package life.light.posemetre

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
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
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import life.light.posemetre.databinding.ActivityMainBinding
import org.json.JSONArray
import org.json.JSONObject
import java.nio.ByteBuffer
import java.text.SimpleDateFormat
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


typealias LumaListener = (luma: Double) -> Unit

class MainActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityMainBinding
    private var imageCapture: ImageCapture? = null
    private lateinit var cameraExecutor: ExecutorService

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
                Log.i(TAG, "Latitude: " + location.latitude)
                Log.i(TAG, "Longitude: " + location.longitude)
            }
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS
            )
        }

        // Configurer les auditeurs pour le bouton de prise de photo
        viewBinding.imageCaptureButton.setOnClickListener { takePhoto() }

        cameraExecutor = Executors.newSingleThreadExecutor()
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
                }
            }
        )
    }

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
                        Log.d(TAG, getString(R.string.average_luminosity) + luma)
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
        private const val TAG = "Posemétre"
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
        val url = "http://10.0.2.2:8081/android/vue"
        val androidVue = AndroidVue()
        val mRequestQueue = Volley.newRequestQueue(baseContext)

        val mStringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.i(TAG, "-----Response :$response")
                val vueJsonObject = JSONObject(response)
                val vueObject = vueJsonObject.getJSONArray("vues")
                for (i in 0 until (vueObject.length())) {
                    val androidVueJSONObject = vueObject.getJSONObject(i)
                    androidVue.id = androidVueJSONObject.getLong("id")
                    androidVue.nomAppareilPhoto = androidVueJSONObject.getString("nomAppareilPhoto")
                    androidVue.sensibilite = androidVueJSONObject.getString("sensibilite")
                    androidVue.vitesses =
                        jsonArrayToArrayList(androidVueJSONObject.getJSONArray("vitesses"))
                    androidVue.ouvertures =
                        jsonArrayToArrayList(androidVueJSONObject.getJSONArray("ouvertures"))
                    viewBinding.Sensitivity.text = androidVue.sensibilite
                    viewBinding.cameraName.text = androidVue.nomAppareilPhoto
                    val adapterVitesse = ArrayAdapter(
                        baseContext,
                        android.R.layout.simple_spinner_item,
                        androidVue.vitesses
                    )
                    viewBinding.vitesse.adapter = adapterVitesse
                    val adapterOuverture = ArrayAdapter(
                        baseContext,
                        android.R.layout.simple_spinner_item,
                        androidVue.ouvertures
                    )
                    viewBinding.ouverture.adapter = adapterOuverture
                }
            }
        ) { error ->
            Log.e(TAG, "------Error :$error")
        }
        mRequestQueue.add(mStringRequest)
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