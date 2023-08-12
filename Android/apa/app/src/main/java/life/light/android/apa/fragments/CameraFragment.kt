/*
 * Copyright 2020 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package life.light.android.apa.fragments

import android.R.attr.value
import android.content.*
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.concurrent.futures.await
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import androidx.window.layout.WindowInfoTracker
import androidx.window.layout.WindowMetricsCalculator
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.launch
import life.light.android.apa.KEY_EVENT_ACTION
import life.light.android.apa.KEY_EVENT_EXTRA
import life.light.android.apa.R
import life.light.android.apa.databinding.CameraUiContainerBinding
import life.light.android.apa.databinding.FragmentCameraBinding
import life.light.android.apa.utils.ANIMATION_FAST_MILLIS
import life.light.android.apa.utils.ANIMATION_SLOW_MILLIS
import life.light.android.apa.utils.MediaStoreUtils
import life.light.android.apa.utils.simulateClick
import java.nio.ByteBuffer
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

/** Helper type alias used for analysis use case callbacks */
typealias LumaListener = (luma: Double) -> Unit

/**
 * Main fragment for this app. Implements all camera operations including:
 * - Viewfinder
 * - Photo taking
 * - Image analysis
 */
class CameraFragment : Fragment() {

    private var _fragmentCameraBinding: FragmentCameraBinding? = null

    private val fragmentCameraBinding get() = _fragmentCameraBinding!!

    private var cameraUiContainerBinding: CameraUiContainerBinding? = null

    private lateinit var broadcastManager: LocalBroadcastManager

    private lateinit var mediaStoreUtils: MediaStoreUtils

    private var displayId: Int = -1
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var windowManager: WindowInfoTracker
    private val args: CameraFragmentArgs by navArgs()
    private var vitesses = ArrayList<String>()
    private var vitesse: String = ""
    private var idVitesse: Int = 0
    private var ouvertures = ArrayList<String>()
    private var ouverture: String = ""
    private var idOuverture: Int = 0
    private var iso = ""
    private var idVue: Long = 0
    private val luxToEv: MutableMap<Double, Double> = HashMap()
    private val idEvs: MutableMap<Double, Int> = HashMap()
    private val idOuvertures: MutableMap<Double, Int> = HashMap()
    private val evOuvertureToVitesse = Array(23) { arrayOfNulls<String>(23) }
    private var luma = 0.0

    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    /** Blocking camera operations are performed using this executor */
    private lateinit var cameraExecutor: ExecutorService

    /** Volume down button receiver used to trigger shutter */
    private val volumeDownReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.getIntExtra(KEY_EVENT_EXTRA, KeyEvent.KEYCODE_UNKNOWN)) {
                // When the volume down button is pressed, simulate a shutter button click
                KeyEvent.KEYCODE_VOLUME_DOWN -> {
                    cameraUiContainerBinding?.cameraCaptureButton?.simulateClick()
                }
            }
        }
    }

    /**
     * We need a display listener for orientation changes that do not trigger a configuration
     * change, for example if we choose to override config change in manifest or for 180-degree
     * orientation changes.
     */
    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (displayId == this@CameraFragment.displayId) {
                Log.d(TAG, "Rotation changed: ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
                imageAnalyzer?.targetRotation = view.display.rotation
            }
        } ?: Unit
    }

    override fun onResume() {
        super.onResume()
        // Make sure that all permissions are still present, since the
        // user could have removed them while the app was in paused state.
        if (!PermissionsFragment.hasPermissions(requireContext())) {
            Navigation.findNavController(requireActivity(), R.id.fragment_container).navigate(
                CameraFragmentDirections.actionCameraToAccueil()
            )
        }
    }

    override fun onDestroyView() {
        _fragmentCameraBinding = null
        super.onDestroyView()

        // Shut down our background executor
        cameraExecutor.shutdown()

        // Unregister the broadcast receivers and listeners
        broadcastManager.unregisterReceiver(volumeDownReceiver)
        displayManager.unregisterDisplayListener(displayListener)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentCameraBinding = FragmentCameraBinding.inflate(inflater, container, false)
        return fragmentCameraBinding.root
    }

    private fun setGalleryThumbnail(filename: String) {
        // Run the operations in the view's thread
        cameraUiContainerBinding?.photoViewButton?.let { photoViewButton ->
            photoViewButton.post {
                // Remove thumbnail padding
                photoViewButton.setPadding(resources.getDimension(R.dimen.stroke_small).toInt())

                // Load thumbnail into circular button using Glide
                Glide.with(photoViewButton)
                    .load(filename)
                    .apply(RequestOptions.circleCropTransform())
                    .into(photoViewButton)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize our background executor
        cameraExecutor = Executors.newSingleThreadExecutor()

        broadcastManager = LocalBroadcastManager.getInstance(view.context)

        // Set up the intent filter that will receive events from our main activity
        val filter = IntentFilter().apply { addAction(KEY_EVENT_ACTION) }
        broadcastManager.registerReceiver(volumeDownReceiver, filter)

        // Every time the orientation of device changes, update rotation for use cases
        displayManager.registerDisplayListener(displayListener, null)

        // Initialize WindowManager to retrieve display metrics
        windowManager = WindowInfoTracker.getOrCreate(view.context)

        // Initialize MediaStoreUtils for fetching this app's images
        mediaStoreUtils = MediaStoreUtils(requireContext())

        // Wait for the views to be properly laid out
        fragmentCameraBinding.viewFinder.post {

            // Keep track of the display in which this view is attached
            displayId = fragmentCameraBinding.viewFinder.display.displayId

            // Build UI controls
            updateCameraUi()

            // Set up the camera and its use cases
            lifecycleScope.launch {
                setUpCamera()
            }

            cameraUiContainerBinding?.sensibilite?.setText(args.sensibilite + " ISO")
            iso = args.sensibilite
            idVue = args.idVue

            ouvertures = listeDuTableau(args.listeOuveture[0].replace("[", "").replace("]", ""))
            val adapterOuverture =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, ouvertures)
            adapterOuverture.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cameraUiContainerBinding?.ouverture?.adapter = adapterOuverture

            vitesses = listeDuTableau(args.listeVitesse[0].replace("[", "").replace("]", ""))
            val adapterVitesse =
                ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, vitesses)
            adapterVitesse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            cameraUiContainerBinding?.vitesse?.adapter = adapterVitesse

            cameraUiContainerBinding?.radioGroup?.check(R.id.AutomatiqueRB)

        }
    }

    private fun listeDuTableau(tableau: String): ArrayList<String> {
        val list = ArrayList<String>()
        var element = ""
        var trouve = false
        for (caractere in tableau) {
            if ((caractere == '"') && (!trouve)) {
                trouve = true
            } else if (trouve && (caractere != '"')) {
                element += caractere
            } else if ((caractere == '"') && (trouve)) {
                trouve = false
                list.add(element)
                element = ""
            }
        }
        return list
    }

    /**
     * Inflate camera controls and update the UI manually upon config changes to avoid removing
     * and re-adding the view finder from the view hierarchy; this provides a seamless rotation
     * transition on devices that support it.
     *
     * NOTE: The flag is supported starting in Android 8 but there still is a small flash on the
     * screen for devices that run Android 9 or below.
     */
    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)

        // Rebind the camera with the updated display metrics
        bindCameraUseCases()

        // Enable or disable switching between cameras
        updateCameraSwitchButton()
    }

    /** Initialize CameraX, and prepare to bind the camera use cases  */
    private suspend fun setUpCamera() {
        cameraProvider = ProcessCameraProvider.getInstance(requireContext()).await()

        // Select lensFacing depending on the available cameras
        lensFacing = when {
            hasBackCamera() -> CameraSelector.LENS_FACING_BACK
            hasFrontCamera() -> CameraSelector.LENS_FACING_FRONT
            else -> throw IllegalStateException("Back and front camera are unavailable")
        }

        // Enable or disable switching between cameras
        updateCameraSwitchButton()

        // Build and bind the camera use cases
        bindCameraUseCases()
    }

    /** Declare and bind preview, capture and analysis use cases */
    private fun bindCameraUseCases() {

        // Get screen metrics used to setup camera for full screen resolution
        val metrics = WindowMetricsCalculator.getOrCreate()
            .computeCurrentWindowMetrics(requireActivity()).bounds
        Log.d(TAG, "Screen metrics: ${metrics.width()} x ${metrics.height()}")

        val screenAspectRatio = aspectRatio(metrics.width(), metrics.height())
        Log.d(TAG, "Preview aspect ratio: $screenAspectRatio")

        val rotation = fragmentCameraBinding.viewFinder.display.rotation

        // CameraProvider
        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        // CameraSelector
        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        // Preview
        preview = Preview.Builder()
            // We request aspect ratio but no resolution
            .setTargetAspectRatio(screenAspectRatio)
            // Set initial target rotation
            .setTargetRotation(rotation)
            .build()

        // ImageCapture
        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            // We request aspect ratio but no resolution to match preview config, but letting
            // CameraX optimize for whatever specific resolution best fits our use cases
            .setTargetAspectRatio(screenAspectRatio)
            // Set initial target rotation, we will have to call this again if rotation changes
            // during the lifecycle of this use case
            .setTargetRotation(rotation)
            .build()

        // ImageAnalysis
        imageAnalyzer = ImageAnalysis.Builder()
            // We request aspect ratio but no resolution
            .setTargetAspectRatio(screenAspectRatio)
            // Set initial target rotation, we will have to call this again if rotation changes
            // during the lifecycle of this use case
            .setTargetRotation(rotation)
            .build()
            // The analyzer can then be assigned to the instance
            .also {
                it.setAnalyzer(cameraExecutor, LuminosityAnalyzer { luma ->
                    // Values returned from our analyzer are passed to the attached listener
                    // We log image analysis results here - you should do something useful instead!
                    // Les valeurs renvoyées par notre analyseur sont transmises à l'écouteur attaché
                    // Nous enregistrons ici les résultats de l'analyse d'image - vous devriez faire quelque chose d'utile à la place !
                    // Log.d(TAG, "Average luminosity: $luma")
                    this.luma = luma
                })
            }

        // Must unbind the use-cases before rebinding them
        cameraProvider.unbindAll()

        if (camera != null) {
            // Must remove observers from the previous camera instance
            removeCameraStateObservers(camera!!.cameraInfo)
        }

        try {
            // A variable number of use-cases can be passed here -
            // camera provides access to CameraControl & CameraInfo
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture, imageAnalyzer
            )

            // Attach the viewfinder's surface provider to preview use case
            preview?.setSurfaceProvider(fragmentCameraBinding.viewFinder.surfaceProvider)
            observeCameraState(camera?.cameraInfo!!)
        } catch (exc: Exception) {
            Log.e(TAG, "Use case binding failed", exc)
        }
    }

    private fun calcul() {

        initMapLuxToEv()
        var ev = 0.0
        if (luxToEv.containsKey(luma)) {
            ev = luxToEv[luma]!!
        } else {
            luxToEv.forEach { (lux, valeur) ->
                if (luma < lux && ev == 0.0) {
                    ev = valeur
                }
            }
        }

        if (iso == "100") {
            initListeIdEv()
            initListeIdOuverture()
            initEvOuvertureToVitesse()
            if (R.id.AutomatiqueRB == cameraUiContainerBinding?.radioGroup?.checkedRadioButtonId) {
                idOuverture = ouvertures.size / 2
                ouverture = ouvertures[idOuverture]
                val idListeOuverture = idOuvertures[ouverture.toDouble()]
                val idListeEV = idEvs[ev]
                vitesse = evOuvertureToVitesse[idListeEV!!][idListeOuverture!!].toString()
                for ((i, v) in vitesses.withIndex()) {
                    if (v == vitesse) {
                        idVitesse = i
                        break
                    }
                }
            }
            //Log.d(TAG, " EV = " + ev + " Ouverture = " + ouverture + " Vitesse = " + vitesse)
        }
    }

    private fun removeCameraStateObservers(cameraInfo: CameraInfo) {
        cameraInfo.cameraState.removeObservers(viewLifecycleOwner)
    }

    private fun observeCameraState(cameraInfo: CameraInfo) {
        cameraInfo.cameraState.observe(viewLifecycleOwner) { cameraState ->
            run {
                when (cameraState.type) {
                    CameraState.Type.PENDING_OPEN -> {
                        // Ask the user to close other camera apps
                        Toast.makeText(
                            context,
                            "CameraState: Pending Open",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    CameraState.Type.OPENING -> {
                        // Show the Camera UI
                        Toast.makeText(
                            context,
                            "CameraState: Opening",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    CameraState.Type.OPEN -> {
                        // Setup Camera resources and begin processing
                        Toast.makeText(
                            context,
                            "CameraState: Open",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    CameraState.Type.CLOSING -> {
                        // Close camera UI
                        Toast.makeText(
                            context,
                            "CameraState: Closing",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    CameraState.Type.CLOSED -> {
                        // Free camera resources
                        Toast.makeText(
                            context,
                            "CameraState: Closed",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }

            cameraState.error?.let { error ->
                when (error.code) {
                    // Open errors
                    CameraState.ERROR_STREAM_CONFIG -> {
                        // Make sure to setup the use cases properly
                        Toast.makeText(
                            context,
                            "Stream config error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Opening errors
                    CameraState.ERROR_CAMERA_IN_USE -> {
                        // Close the camera or ask user to close another camera app that's using the
                        // camera
                        Toast.makeText(
                            context,
                            "Camera in use",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    CameraState.ERROR_MAX_CAMERAS_IN_USE -> {
                        // Close another open camera in the app, or ask the user to close another
                        // camera app that's using the camera
                        Toast.makeText(
                            context,
                            "Max cameras in use",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    CameraState.ERROR_OTHER_RECOVERABLE_ERROR -> {
                        Toast.makeText(
                            context,
                            "Other recoverable error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Closing errors
                    CameraState.ERROR_CAMERA_DISABLED -> {
                        // Ask the user to enable the device's cameras
                        Toast.makeText(
                            context,
                            "Camera disabled",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    CameraState.ERROR_CAMERA_FATAL_ERROR -> {
                        // Ask the user to reboot the device to restore camera function
                        Toast.makeText(
                            context,
                            "Fatal error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    // Closed errors
                    CameraState.ERROR_DO_NOT_DISTURB_MODE_ENABLED -> {
                        // Ask the user to disable the "Do Not Disturb" mode, then reopen the camera
                        Toast.makeText(
                            context,
                            "Do not disturb mode enabled",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    /**
     *  [androidx.camera.core.ImageAnalysis.Builder] requires enum value of
     *  [androidx.camera.core.AspectRatio]. Currently it has values of 4:3 & 16:9.
     *
     *  Detecting the most suitable ratio for dimensions provided in @params by counting absolute
     *  of preview ratio to one of the provided values.
     *
     *  @param width - preview width
     *  @param height - preview height
     *  @return suitable aspect ratio
     */
    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    /** Method used to re-draw the camera UI controls, called every time configuration changes. */
    private fun updateCameraUi() {

        // Remove previous UI if any
        cameraUiContainerBinding?.root?.let {
            fragmentCameraBinding.root.removeView(it)
        }

        cameraUiContainerBinding = CameraUiContainerBinding.inflate(
            LayoutInflater.from(requireContext()),
            fragmentCameraBinding.root,
            true
        )

        // In the background, load latest photo taken (if any) for gallery thumbnail
        lifecycleScope.launch {
            val thumbnailUri = mediaStoreUtils.getLatestImageFilename()
            thumbnailUri?.let {
                setGalleryThumbnail(it)
            }
        }

        // Listener for button used to capture photo
        cameraUiContainerBinding?.cameraCaptureButton?.setOnClickListener {

            // Get a stable reference of the modifiable image capture use case
            imageCapture?.let { imageCapture ->

                // Create time stamped name and MediaStore entry.
                val name = SimpleDateFormat(FILENAME, Locale.US)
                    .format(System.currentTimeMillis())
                val contentValues = ContentValues().apply {
                    put(MediaStore.MediaColumns.DISPLAY_NAME, name)
                    put(MediaStore.MediaColumns.MIME_TYPE, PHOTO_TYPE)
                    if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                        val appName = requireContext().resources.getString(R.string.app_name)
                        put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/${appName}")
                    }
                }

                // Create output options object which contains file + metadata
                val outputOptions = ImageCapture.OutputFileOptions
                    .Builder(
                        requireContext().contentResolver,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
                    .build()

                // Setup image capture listener which is triggered after photo has been taken
                imageCapture.takePicture(
                    outputOptions, cameraExecutor, object : ImageCapture.OnImageSavedCallback {
                        override fun onError(exc: ImageCaptureException) {
                            Log.e(TAG, "Photo capture failed: ${exc.message}", exc)
                        }

                        override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                            val savedUri = output.savedUri
                            Log.d(TAG, "Photo capture succeeded: $savedUri")

                            // We can only change the foreground Drawable using API level 23+ API
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                // Update the gallery thumbnail with latest picture taken
                                setGalleryThumbnail(savedUri.toString())
                            }

                            // Implicit broadcasts will be ignored for devices running API level >= 24
                            // so if you only target API level 24+ you can remove this statement
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                                // Suppress deprecated Camera usage needed for API level 23 and below
                                @Suppress("DEPRECATION")
                                requireActivity().sendBroadcast(
                                    Intent(android.hardware.Camera.ACTION_NEW_PICTURE, savedUri)
                                )
                            }
                            // Sauvegarde la photo sur le serveur
                            postVolley()
                        }
                    })

                // We can only change the foreground Drawable using API level 23+ API
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                    // Display flash animation to indicate that photo was captured
                    fragmentCameraBinding.root.postDelayed({
                        fragmentCameraBinding.root.foreground = ColorDrawable(Color.WHITE)
                        fragmentCameraBinding.root.postDelayed(
                            { fragmentCameraBinding.root.foreground = null }, ANIMATION_FAST_MILLIS
                        )
                    }, ANIMATION_SLOW_MILLIS)
                }
            }
        }

        // Setup for button used to switch cameras
        cameraUiContainerBinding?.cameraSwitchButton?.let {

            // Disable the button until the camera is set up
            it.isEnabled = false

            // Listener for button used to switch cameras. Only called if the button is enabled
            it.setOnClickListener {
                lensFacing = if (CameraSelector.LENS_FACING_FRONT == lensFacing) {
                    CameraSelector.LENS_FACING_BACK
                } else {
                    CameraSelector.LENS_FACING_FRONT
                }
                // Re-bind use cases to update selected camera
                bindCameraUseCases()
            }
        }

        // Listener for button used to view the most recent photo
        cameraUiContainerBinding?.photoViewButton?.setOnClickListener {
            // Only navigate when the gallery has photos
            lifecycleScope.launch {
                if (mediaStoreUtils.getImages().isNotEmpty()) {
                    Navigation.findNavController(requireActivity(), R.id.fragment_container)
                        .navigate(
                            CameraFragmentDirections.actionCameraToGallery(
                                mediaStoreUtils.mediaStoreCollection.toString()
                            )
                        )
                }
            }
        }

        cameraUiContainerBinding?.calculButton?.setOnClickListener {
            // Only navigate when the gallery has photos
            lifecycleScope.launch {
                calcul()
                cameraUiContainerBinding?.vitesse?.setSelection(idVitesse)
                cameraUiContainerBinding?.ouverture?.setSelection(idOuverture)
            }
        }
    }


    fun postVolley() {
        val queue = Volley.newRequestQueue(requireContext())
        val url = "http://10.0.2.2:8081/vue/$idVue/photo"
        val requestBody = "idOuverture=$idOuverture&idVitesse=$idVitesse&idStatut=2"
        val stringReq: StringRequest =
            object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    // response
                    var strResp = response.toString()
                    Log.d("API", strResp)
                },
                Response.ErrorListener { error ->
                    Log.d("API", "error => $error")
                }
            ) {
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        queue.add(stringReq)
    }

    /** Enabled or disabled a button to switch cameras depending on the available cameras */
    private fun updateCameraSwitchButton() {
        try {
            cameraUiContainerBinding?.cameraSwitchButton?.isEnabled =
                hasBackCamera() && hasFrontCamera()
        } catch (exception: CameraInfoUnavailableException) {
            cameraUiContainerBinding?.cameraSwitchButton?.isEnabled = false
        }
    }

    /** Returns true if the device has an available back camera. False otherwise */
    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    /** Returns true if the device has an available front camera. False otherwise */
    private fun hasFrontCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_FRONT_CAMERA) ?: false
    }

    /**
     * Our custom image analysis class.
     *
     * <p>All we need to do is override the function `analyze` with our desired operations. Here,
     * we compute the average luminosity of the image by looking at the Y plane of the YUV frame.
     */
    private class LuminosityAnalyzer(listener: LumaListener? = null) : ImageAnalysis.Analyzer {
        private val frameRateWindow = 8
        private val frameTimestamps = ArrayDeque<Long>(5)
        private val listeners = ArrayList<LumaListener>().apply { listener?.let { add(it) } }
        private var lastAnalyzedTimestamp = 0L
        var framesPerSecond: Double = -1.0
            private set

        /**
         * Helper extension function used to extract a byte array from an image plane buffer
         */
        private fun ByteBuffer.toByteArray(): ByteArray {
            rewind()    // Rewind the buffer to zero
            val data = ByteArray(remaining())
            get(data)   // Copy the buffer into a byte array
            return data // Return the byte array
        }

        /**
         * Analyzes an image to produce a result.
         *
         * <p>The caller is responsible for ensuring this analysis method can be executed quickly
         * enough to prevent stalls in the image acquisition pipeline. Otherwise, newly available
         * images will not be acquired and analyzed.
         *
         * <p>The image passed to this method becomes invalid after this method returns. The caller
         * should not store external references to this image, as these references will become
         * invalid.
         *
         * @param image image being analyzed VERY IMPORTANT: Analyzer method implementation must
         * call image.close() on received images when finished using them. Otherwise, new images
         * may not be received or the camera may stall, depending on back pressure setting.
         *
         */
        override fun analyze(image: ImageProxy) {
            // If there are no listeners attached, we don't need to perform analysis
            if (listeners.isEmpty()) {
                image.close()
                return
            }

            // Keep track of frames analyzed
            val currentTime = System.currentTimeMillis()
            frameTimestamps.push(currentTime)

            // Compute the FPS using a moving average
            while (frameTimestamps.size >= frameRateWindow) frameTimestamps.removeLast()
            val timestampFirst = frameTimestamps.peekFirst() ?: currentTime
            val timestampLast = frameTimestamps.peekLast() ?: currentTime
            framesPerSecond = 1.0 / ((timestampFirst - timestampLast) /
                    frameTimestamps.size.coerceAtLeast(1).toDouble()) * 1000.0

            // Analysis could take an arbitrarily long amount of time
            // Since we are running in a different thread, it won't stall other use cases

            lastAnalyzedTimestamp = frameTimestamps.first

            // Since format in ImageAnalysis is YUV, image.planes[0] contains the luminance plane
            val buffer = image.planes[0].buffer

            // Extract image data from callback object
            val data = buffer.toByteArray()

            // Convert the data into an array of pixel values ranging 0-255
            val pixels = data.map { it.toInt() and 0xFF }

            // Compute average luminance for the image
            val luma = pixels.average()

            // Call all listeners with new value
            listeners.forEach { it(luma) }

            image.close()
        }
    }

    companion object {
        private const val TAG = "apa"
        private const val FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS"
        private const val PHOTO_TYPE = "image/jpeg"
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0
    }

    private fun initMapLuxToEv() {
        luxToEv[001.25] = -1.0
        luxToEv[001.75] = -0.5
        luxToEv[002.50] = 00.0
        luxToEv[003.50] = 00.5
        luxToEv[005.00] = 01.0
        luxToEv[007.00] = 01.5
        luxToEv[010.00] = 02.0
        luxToEv[014.00] = 02.5
        luxToEv[020.00] = 03.0
        luxToEv[028.00] = 03.5
        luxToEv[040.00] = 04.0
        luxToEv[056.00] = 04.5
        luxToEv[080.00] = 05.0
        luxToEv[112.00] = 05.5
        luxToEv[160.00] = 06.0
        luxToEv[225.00] = 06.5
    }

    private fun initListeIdEv() {
        idEvs[-6.0] = 0
        idEvs[-5.0] = 1
        idEvs[-4.0] = 2
        idEvs[-3.0] = 3
        idEvs[-2.0] = 4
        idEvs[-1.0] = 5
        idEvs[00.0] = 6
        idEvs[01.0] = 7
        idEvs[02.0] = 8
        idEvs[03.0] = 9
        idEvs[04.0] = 10
        idEvs[05.0] = 11
        idEvs[06.0] = 12
        idEvs[07.0] = 13
        idEvs[08.0] = 14
        idEvs[09.0] = 15
        idEvs[10.0] = 16
        idEvs[11.0] = 17
        idEvs[12.0] = 18
        idEvs[13.0] = 19
        idEvs[14.0] = 20
        idEvs[15.0] = 21
        idEvs[16.0] = 22
    }

    private fun initListeIdOuverture() {
        idOuvertures[01.0] = 0
        idOuvertures[01.4] = 1
        idOuvertures[02.0] = 2
        idOuvertures[02.8] = 3
        idOuvertures[04.0] = 4
        idOuvertures[05.6] = 5
        idOuvertures[08.0] = 6
        idOuvertures[11.0] = 7
        idOuvertures[16.0] = 8
        idOuvertures[22.0] = 9
        idOuvertures[32.0] = 10
        idOuvertures[45.0] = 11
        idOuvertures[64.0] = 12
    }

    private fun initEvOuvertureToVitesse() {
        // Ouverture 1
        evOuvertureToVitesse[0][0] = "60"
        evOuvertureToVitesse[1][0] = "30"
        evOuvertureToVitesse[2][0] = "15"
        evOuvertureToVitesse[3][0] = "8"
        evOuvertureToVitesse[4][0] = "4"
        evOuvertureToVitesse[5][0] = "2"
        evOuvertureToVitesse[6][0] = "1"
        evOuvertureToVitesse[7][0] = "1/2"
        evOuvertureToVitesse[8][0] = "1/4"
        evOuvertureToVitesse[9][0] = "1/8"
        evOuvertureToVitesse[10][0] = "1/15"
        evOuvertureToVitesse[11][0] = "1/30"
        evOuvertureToVitesse[12][0] = "1/60"
        evOuvertureToVitesse[12][0] = "1/125"
        evOuvertureToVitesse[13][0] = "1/250"
        evOuvertureToVitesse[14][0] = "1/500"
        evOuvertureToVitesse[15][0] = "1/1000"
        evOuvertureToVitesse[16][0] = "1/2000"
        evOuvertureToVitesse[17][0] = "1/4000"
        evOuvertureToVitesse[18][0] = "1/8000"

        // Ouverture 2.8
        evOuvertureToVitesse[0][3] = "8m"
        evOuvertureToVitesse[1][3] = "4m"
        evOuvertureToVitesse[2][3] = "2m"
        evOuvertureToVitesse[3][3] = "60"
        evOuvertureToVitesse[4][3] = "30"
        evOuvertureToVitesse[5][3] = "15"
        evOuvertureToVitesse[6][3] = "8"
        evOuvertureToVitesse[7][3] = "4"
        evOuvertureToVitesse[8][3] = "2"
        evOuvertureToVitesse[9][3] = "1"
        evOuvertureToVitesse[10][3] = "1/2"
        evOuvertureToVitesse[11][3] = "1/4"
        evOuvertureToVitesse[12][3] = "1/8"
        evOuvertureToVitesse[13][3] = "1/15"
        evOuvertureToVitesse[14][3] = "1/30"
        evOuvertureToVitesse[15][3] = "1/60"
        evOuvertureToVitesse[16][3] = "1/125"
        evOuvertureToVitesse[17][3] = "1/250"
        evOuvertureToVitesse[18][3] = "1/500"

        // Ouverture 4
        evOuvertureToVitesse[0][4] = "16m"
        evOuvertureToVitesse[1][4] = "8m"
        evOuvertureToVitesse[2][4] = "4m"
        evOuvertureToVitesse[3][4] = "2m"
        evOuvertureToVitesse[1][4] = "60"
        evOuvertureToVitesse[5][4] = "30"
        evOuvertureToVitesse[6][4] = "15"
        evOuvertureToVitesse[7][4] = "8"
        evOuvertureToVitesse[8][4] = "4"
        evOuvertureToVitesse[9][4] = "2"
        evOuvertureToVitesse[10][4] = "1"
        evOuvertureToVitesse[11][4] = "1/2"
        evOuvertureToVitesse[12][4] = "1/4"
        evOuvertureToVitesse[13][4] = "1/8"
        evOuvertureToVitesse[14][4] = "1/15"
        evOuvertureToVitesse[15][4] = "1/30"
        evOuvertureToVitesse[16][4] = "1/60"
        evOuvertureToVitesse[17][4] = "1/125"
        evOuvertureToVitesse[18][4] = "1/250"
        evOuvertureToVitesse[19][4] = "1/500"

        // Ouverture 5.6
        evOuvertureToVitesse[0][5] = "32m"
        evOuvertureToVitesse[1][5] = "16m"
        evOuvertureToVitesse[2][5] = "8m"
        evOuvertureToVitesse[3][5] = "4m"
        evOuvertureToVitesse[4][5] = "2m"
        evOuvertureToVitesse[5][5] = "60"
        evOuvertureToVitesse[6][5] = "30"
        evOuvertureToVitesse[7][5] = "15"
        evOuvertureToVitesse[8][5] = "8"
        evOuvertureToVitesse[9][5] = "4"
        evOuvertureToVitesse[10][5] = "2"
        evOuvertureToVitesse[11][5] = "1"
        evOuvertureToVitesse[12][5] = "1/2"
        evOuvertureToVitesse[13][5] = "1/4"
        evOuvertureToVitesse[14][5] = "1/8"
        evOuvertureToVitesse[15][5] = "1/15"
        evOuvertureToVitesse[16][5] = "1/30"
        evOuvertureToVitesse[17][5] = "1/60"
        evOuvertureToVitesse[18][5] = "1/125"
        evOuvertureToVitesse[19][5] = "1/250"
        evOuvertureToVitesse[20][5] = "1/500"

        // Ouverture 8
        evOuvertureToVitesse[0][6] = "64m"
        evOuvertureToVitesse[1][6] = "32m"
        evOuvertureToVitesse[2][6] = "16m"
        evOuvertureToVitesse[3][6] = "8m"
        evOuvertureToVitesse[4][6] = "4m"
        evOuvertureToVitesse[5][6] = "2m"
        evOuvertureToVitesse[6][6] = "60"
        evOuvertureToVitesse[7][6] = "30"
        evOuvertureToVitesse[8][6] = "15"
        evOuvertureToVitesse[9][6] = "8"
        evOuvertureToVitesse[10][6] = "4"
        evOuvertureToVitesse[11][6] = "2"
        evOuvertureToVitesse[12][6] = "1"
        evOuvertureToVitesse[13][6] = "1/2"
        evOuvertureToVitesse[14][6] = "1/4"
        evOuvertureToVitesse[15][6] = "1/8"
        evOuvertureToVitesse[16][6] = "1/15"
        evOuvertureToVitesse[17][6] = "1/30"
        evOuvertureToVitesse[18][6] = "1/60"
        evOuvertureToVitesse[19][6] = "1/125"
        evOuvertureToVitesse[20][6] = "1/250"
        evOuvertureToVitesse[21][6] = "1/500"

        // Ouverture 11
        evOuvertureToVitesse[0][7] = "128m"
        evOuvertureToVitesse[1][7] = "64m"
        evOuvertureToVitesse[2][7] = "32m"
        evOuvertureToVitesse[3][7] = "16m"
        evOuvertureToVitesse[4][7] = "8m"
        evOuvertureToVitesse[5][7] = "4m"
        evOuvertureToVitesse[6][7] = "2m"
        evOuvertureToVitesse[7][7] = "60"
        evOuvertureToVitesse[8][7] = "30"
        evOuvertureToVitesse[9][7] = "15"
        evOuvertureToVitesse[10][7] = "8"
        evOuvertureToVitesse[11][7] = "4"
        evOuvertureToVitesse[12][7] = "2"
        evOuvertureToVitesse[13][7] = "1"
        evOuvertureToVitesse[14][7] = "1/2"
        evOuvertureToVitesse[15][7] = "1/4"
        evOuvertureToVitesse[16][7] = "1/8"
        evOuvertureToVitesse[17][7] = "1/15"
        evOuvertureToVitesse[18][7] = "1/30"
        evOuvertureToVitesse[19][7] = "1/60"
        evOuvertureToVitesse[20][7] = "1/125"
        evOuvertureToVitesse[21][7] = "1/250"
        evOuvertureToVitesse[22][7] = "1/500"

        // Ouverture 16
        evOuvertureToVitesse[0][8] = "256m"
        evOuvertureToVitesse[1][8] = "128m"
        evOuvertureToVitesse[2][8] = "64m"
        evOuvertureToVitesse[3][8] = "32m"
        evOuvertureToVitesse[4][8] = "16m"
        evOuvertureToVitesse[5][8] = "8m"
        evOuvertureToVitesse[6][8] = "4m"
        evOuvertureToVitesse[7][8] = "2m"
        evOuvertureToVitesse[8][8] = "60"
        evOuvertureToVitesse[9][8] = "30"
        evOuvertureToVitesse[10][8] = "15"
        evOuvertureToVitesse[11][8] = "8"
        evOuvertureToVitesse[12][8] = "4"
        evOuvertureToVitesse[13][8] = "2"
        evOuvertureToVitesse[14][8] = "1"
        evOuvertureToVitesse[15][8] = "1/2"
        evOuvertureToVitesse[16][8] = "1/4"
        evOuvertureToVitesse[17][8] = "1/8"
        evOuvertureToVitesse[18][8] = "1/15"
        evOuvertureToVitesse[19][8] = "1/30"
        evOuvertureToVitesse[20][8] = "1/60"
        evOuvertureToVitesse[21][8] = "1/125"
        evOuvertureToVitesse[22][8] = "1/250"

        // Ouverture 22
        evOuvertureToVitesse[0][9] = "512m"
        evOuvertureToVitesse[1][9] = "256m"
        evOuvertureToVitesse[2][9] = "128m"
        evOuvertureToVitesse[3][9] = "64m"
        evOuvertureToVitesse[4][9] = "32m"
        evOuvertureToVitesse[5][9] = "16m"
        evOuvertureToVitesse[6][9] = "8m"
        evOuvertureToVitesse[7][9] = "4m"
        evOuvertureToVitesse[8][9] = "2m"
        evOuvertureToVitesse[9][9] = "60"
        evOuvertureToVitesse[10][9] = "30"
        evOuvertureToVitesse[11][9] = "15"
        evOuvertureToVitesse[12][9] = "8"
        evOuvertureToVitesse[13][9] = "4"
        evOuvertureToVitesse[14][9] = "2"
        evOuvertureToVitesse[15][9] = "1"
        evOuvertureToVitesse[16][9] = "1/2"
        evOuvertureToVitesse[17][9] = "1/4"
        evOuvertureToVitesse[18][9] = "1/8"
        evOuvertureToVitesse[19][9] = "1/15"
        evOuvertureToVitesse[20][9] = "1/30"
        evOuvertureToVitesse[21][9] = "1/60"
        evOuvertureToVitesse[22][9] = "1/125"
    }
}
