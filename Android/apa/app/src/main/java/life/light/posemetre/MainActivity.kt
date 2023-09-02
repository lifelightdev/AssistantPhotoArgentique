package life.light.posemetre

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.ImageRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import life.light.posemetre.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.sqrt


typealias LumaListener = (luma: Double) -> Unit

class MainActivity : AppCompatActivity() {

    private val urlServeur = "http://10.0.2.2:8081"
    private lateinit var viewBinding: ActivityMainBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var lux: Double = 0.0
    private var iso: Double = 0.0
    private var listeVitesse: ArrayList<String> = ArrayList()
    private var listeOuverture: ArrayList<String> = ArrayList()
    private var idVue: Long = 0
    private var uriPhoto: Uri? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        getData()
        // Demander les autorisations
        if (allPermissionsGranted()) {
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            val provider = locationManager.allProviders
            val location = locationManager.getLastKnownLocation(provider.first())
            if (location == null) {
                Toast.makeText(this, getString(R.string.check_provider), Toast.LENGTH_SHORT).show()
            } else {
                latitude = location.latitude
                longitude = location.longitude
            }
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Configurer les auditeurs pour les boutons de prise de photo et de calcul
        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }
        viewBinding.calculationButton.setOnClickListener { calcul() }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    fun calcul() {
        /*
         Pour une utilisation en lumière réfléchie
         Les paramètres d'exposition sont liés :
         - à la sensibilité ISO du film,
         - à la luminance du sujet,
         - à l'ouverture et au temps de pose.

         N² / t = (L*S)/K

         N est l'ouverture du diaphragme en indice (f-number)
         t est le temps d'exposition en secondes
         L est la luminance de la scène en cd/m²
         S est la sensibilité ISO du capteur
         K est la constante d'étalonnage du posemètre en lumière réfléchie
         */

        when (viewBinding.radioGroup.checkedRadioButtonId) {
            viewBinding.OuvertureRB.id -> {
                // N² * K = L * S * t
                // N² = (L*S*t) / K
                val constante = 8.0
                val indexOuvertureSelected = viewBinding.ouvertures.selectedItemId.toInt()
                val vitesseSelected = viewBinding.vitesses.getSelectedItem().toString()
                val vitesse: Double
                if (vitesseSelected.contains("1/")) {
                    vitesse =
                        1.0 / vitesseSelected
                            .replace("1/", "").toDouble()
                } else {
                    vitesse = vitesseSelected.toDouble()
                }
                val ouvertureCalcule = sqrt((lux * iso * vitesse) / constante)
                val (indexOuvertureCalculer, ouvertureEstTrouve) =
                    rechercheIndexOuverture(
                        ouvertureCalcule,
                        indexOuvertureSelected,
                        listeOuverture
                    )
                if (!ouvertureEstTrouve) {
                    Toast.makeText(
                        this,
                        getString(R.string.aperture_not_found) + ouvertureCalcule.toInt(),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    viewBinding.ouvertures.setSelection(indexOuvertureCalculer)
                }
            }

            viewBinding.VitesseRB.id -> {
                // L * S * t = N² * K
                // t = (N² * K) / (L * S)
                val constante = 4.0
                val ouvertureDuCalcul =
                    viewBinding.ouvertures.getSelectedItem().toString().toDouble()
                val vitesseCalcule =
                    (ouvertureDuCalcul * ouvertureDuCalcul * constante) / (lux * iso)
                val index = rechercheIndexVitesse(vitesseCalcule, listeVitesse)
                viewBinding.vitesses.setSelection(index)
            }

            else -> {
                Toast.makeText(this, getString(R.string.error_calculation), Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    fun rechercheIndexOuverture(
        ouvertureCalcule: Double, indexOuvertureSelected: Int, listeOuverture: ArrayList<String>
    ): Pair<Int, Boolean> {
        var indexOuvertureCalcule: Int = indexOuvertureSelected
        // Recherche de l'ouveture la plus grande
        var min = listeOuverture.get(0).toDouble()
        var indexMin = 0
        var ouvertureEstTrouve = false
        for (index in listeOuverture.indices) {
            val ouverture = listeOuverture.get(index).toDouble()
            if (ouverture == ouvertureCalcule) {
                indexOuvertureCalcule = index
                ouvertureEstTrouve = true
                break
            } else if (ouverture.toInt() > ouvertureCalcule && ouvertureCalcule > min) {
                val avant = ouverture.toInt() - ouvertureCalcule
                val apres = ouvertureCalcule - min
                if (avant < apres) {
                    indexOuvertureCalcule = index
                    ouvertureEstTrouve = true
                    break
                } else {
                    indexOuvertureCalcule = indexMin
                    ouvertureEstTrouve = true
                    break
                }
            }
            min = ouverture
            indexMin = index
        }
        return Pair(indexOuvertureCalcule, ouvertureEstTrouve)
    }

    fun rechercheIndexVitesse(
        vitesseCalcule: Double,
        listeVitesse: ArrayList<String>
    ): Int {
        var vitesseCalculeInt = 0
        // Convertion en division
        var estInferireurALaSecondeCalcule = false
        if (vitesseCalcule < 1) {
            vitesseCalculeInt = (1.0 / vitesseCalcule).toInt()
            estInferireurALaSecondeCalcule = true
        }
        // Récupération de la vitesse la plus lente de la liste des vitesses disponible trié
        var min: Int
        var indexMin: Int
        var dernierIndex = listeVitesse.size - 1
        var estInferireurALaSeconde = false
        var bulb = false
        // Si la liste se finit par la valeur B on prend la valeur précédente
        if (listeVitesse.get(dernierIndex).equals("B")) {
            dernierIndex = dernierIndex - 1
            bulb = true
        }
        if (listeVitesse.get(dernierIndex).contains("1/")) {
            min = listeVitesse.get(dernierIndex).replace("1/", "").toInt()
            indexMin = dernierIndex
            estInferireurALaSeconde = true
        } else {
            min = listeVitesse.get(dernierIndex).toInt()
            indexMin = dernierIndex
        }
        if (!estInferireurALaSecondeCalcule && !estInferireurALaSeconde) {
            if (bulb) {
                return listeVitesse.size - 1
            } else {
                Toast.makeText(
                    this,
                    getString(R.string.speed_too_high) + vitesseCalcule + getString(R.string.seconds),
                    Toast.LENGTH_LONG
                ).show()
                return listeVitesse.size - 1
            }
        }
        // Recherche de la vitesse plus rapide donc on inverse la liste
        for (index in listeVitesse.indices.reversed()) {
            if (listeVitesse.get(index) != "B") {
                val vitesseString = listeVitesse.get(index)
                var vitesseInt: Int
                if (vitesseString.contains("1/")) {
                    vitesseInt = vitesseString.replace("1/", "").toInt()
                    estInferireurALaSeconde = true
                } else {
                    estInferireurALaSeconde = false
                    vitesseInt = vitesseString.toInt()
                }
                if (estInferireurALaSecondeCalcule && estInferireurALaSeconde) {
                    if (vitesseInt == vitesseCalculeInt) {
                        return index
                    } else if (vitesseInt > vitesseCalculeInt && vitesseCalculeInt < min) {
                        val avant = vitesseInt - vitesseCalculeInt
                        val apres = vitesseCalculeInt - min
                        if (avant < apres) {
                            return index
                        } else {
                            return indexMin
                        }
                    } else {
                        min = vitesseInt
                        indexMin = index
                    }
                }
            }
        }
        val message: String
        if (estInferireurALaSecondeCalcule) {
            message =
                getString(R.string.calculated_speed) + vitesseCalculeInt + getString(R.string.second)
        } else {
            message =
                getString(R.string.calculated_speed_seconds) + vitesseCalculeInt + getString(R.string.seconds)
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        return 0
    }

    private fun takePhoto() {
        // Obtenez une référence stable du cas d'utilisation de capture d'image modifiable
        val imageCapture = imageCapture ?: return

        // Créez un nom horodaté et une entrée MediaStore.
        val name = SimpleDateFormat(FILENAME_FORMAT, Locale.FRENCH)
            .format(System.currentTimeMillis())
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, name)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/CameraX-Image")
            }
        }

        // Créer un objet d'options de sortie contenant un fichier + des métadonnées
        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()

        // Configurer l'écouteur de capture d'image, qui se déclenche après la prise de la photo
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(TAG, getString(R.string.photo_failed) + exc.message, exc)
                }

                override fun
                        onImageSaved(output: ImageCapture.OutputFileResults) {
                    val msg = getString(R.string.photo_success) + output.savedUri
                    Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
                    Log.d(TAG, msg)
                    uriPhoto = output.savedUri
                }
            }
        )
        setData()
    }

    @SuppressLint("RestrictedApi")
    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            // Utilisé pour lier le cycle de vie des caméras au propriétaire du cycle de vie
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            // Aperçu
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(viewBinding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            val imageAnalyzer = ImageAnalysis.Builder()
                .build()
                .also {
                    it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                        lux = luma
                    })
                }

            // Sélectionnez la caméra arrière par défaut
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                // Dissocier les cas d'utilisation avant de les relier
                cameraProvider.unbindAll()

                // Lier les cas d'utilisation à la caméra
                cameraProvider.bindToLifecycle(
                    this, cameraSelector, preview, imageCapture, imageAnalyzer
                )
            } catch (exc: Exception) {
                Log.e(TAG, getString(R.string.binding), exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            baseContext, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "Posemètre"
        private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS =
            mutableListOf(
                Manifest.permission.CAMERA,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults:
        IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                Toast.makeText(
                    this, getString(R.string.permissions),
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun getData() {
        val url = "$urlServeur/android/vue"
        val androidVue = AndroidVue()
        val mRequestQueue = Volley.newRequestQueue(baseContext)

        val mStringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val vueJsonObject = JSONObject(response)
                val vueObject = vueJsonObject.getJSONArray("vues")
                for (i in 0 until (vueObject.length())) {
                    val androidVueJSONObject = vueObject.getJSONObject(i)
                    androidVue.id = androidVueJSONObject.getLong("id")
                    idVue = androidVue.id
                    androidVue.nomAppareilPhoto = androidVueJSONObject.getString("nomAppareilPhoto")
                    androidVue.sensibilite = androidVueJSONObject.getString("sensibilite")
                    androidVue.vitesses =
                        jsonArrayToArrayList(androidVueJSONObject.getJSONArray("vitesses"))
                    listeVitesse = androidVue.vitesses
                    androidVue.ouvertures =
                        jsonArrayToArrayList(androidVueJSONObject.getJSONArray("ouvertures"))
                    listeOuverture = androidVue.ouvertures
                    iso = androidVue.sensibilite?.toDouble()!!
                    viewBinding.Sensitivity.text = " ${androidVue.sensibilite} ISO "
                    viewBinding.cameraName.text = " ${androidVue.nomAppareilPhoto} "
                    val adapterVitesse = ArrayAdapter(
                        baseContext,
                        android.R.layout.simple_spinner_item,
                        androidVue.vitesses
                    )
                    viewBinding.vitesses.adapter = adapterVitesse
                    val adapterOuverture = ArrayAdapter(
                        baseContext,
                        android.R.layout.simple_spinner_item,
                        androidVue.ouvertures
                    )
                    viewBinding.ouvertures.adapter = adapterOuverture
                }
            }
        ) { error ->
            Log.e(TAG, "------Error :$error")
        }
        mRequestQueue.add(mStringRequest)
    }

    private fun setData() {
        val queue = Volley.newRequestQueue(baseContext)
        val url = "$urlServeur/vue/$idVue/photo"
        val valeurOuverture = viewBinding.ouvertures.getSelectedItem().toString()
        val valeurVitesse = viewBinding.vitesses.getSelectedItem().toString()
        val requestBody =
            "valeurOuverture=$valeurOuverture&valeurVitesse=$valeurVitesse&idStatut=2&longitude=$longitude&latitude=$latitude"
        val stringReq: StringRequest =
            object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    val strResp = response.toString()
                },
                Response.ErrorListener { error ->
                    Log.e(TAG, "------Error :$error")
                }
            ) {
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        queue.add(stringReq)
    }

    private fun setPhoto() {
        val queue = Volley.newRequestQueue(baseContext)
        val url = "$urlServeur/vue/$idVue/photoJPG"
        // request a image response from the provided url
        val imageRequest = ImageRequest(
            url,
            { bitmap -> // response listener
                Log.i(TAG, "------Image downloaded successfully!")
            },
            0, // max width
            0, // max height
            ImageView.ScaleType.CENTER_CROP, // image scale type
            Bitmap.Config.ARGB_8888, // decode config
            { error -> // error listener
                Log.e(TAG, "------Error :$error")
            }
        )
        queue.add(imageRequest)
    }

    private fun jsonArrayToArrayList(jArray: JSONArray): ArrayList<String> {
        val listdata = ArrayList<String>()
        for (i in 0 until jArray.length()) {
            listdata.add(jArray.getString(i))
        }
        return listdata
    }

    private class LuminosityAnalyzer(private val listener: LumaListener) : ImageAnalysis.Analyzer {

        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rembobiner le tampon à zéro
            val data = ByteArray(remaining())
            get(data)   // Copiez le tampon dans un tableau d'octets
            return data // Renvoie le tableau d'octets
        }

        override fun analyze(image: ImageProxy) {

            val buffer = image.planes[0].buffer
            val data = buffer.toByteArray()
            val pixels = data.map { it.toInt() and 0xFF }
            val luma = pixels.average()

            listener(luma)

            image.close()
        }
    }
}