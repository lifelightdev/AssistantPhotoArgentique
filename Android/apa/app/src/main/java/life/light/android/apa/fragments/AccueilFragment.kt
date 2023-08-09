package life.light.android.apa.fragments

import AndroidVue
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import life.light.android.apa.AccueilViewModel
import life.light.android.apa.R
import life.light.android.apa.databinding.FragmentAccueilBinding
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.launch
import org.json.JSONObject

class AccueilFragment internal constructor() : Fragment() {

    /** Android ViewBinding */
    private var _fragmentAccueilBinding: FragmentAccueilBinding? = null

    private val fragmentAccueilBinding get() = _fragmentAccueilBinding!!

    private var mRequestQueue: RequestQueue? = null
    private var mStringRequest: StringRequest? = null
    private val url = "http://10.0.2.2:8081/android/vue"
    val androidVue = AndroidVue()

    private lateinit var viewModel: AccueilViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentAccueilBinding = FragmentAccueilBinding.inflate(inflater, container, false)
        return fragmentAccueilBinding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AccueilViewModel::class.java)

        getData()

        fragmentAccueilBinding.buttonPosemetre.setOnClickListener {
            lifecycleScope.launch {
                Navigation.findNavController(requireActivity(), R.id.fragment_container)
                    .navigate(
                        AccueilFragmentDirections.actionAccueilToCamera(
                            androidVue.vitesses.toTypedArray(),
                            androidVue.ouvertures.toTypedArray(),
                            androidVue.nomAppareilPhoto.toString(),
                            androidVue.sensibilite.toString())
                    )
            }
        }
    }

    private fun getData() {
        // RequestQueue initialized
        mRequestQueue = Volley.newRequestQueue(requireContext())

        // String Request initialized
        mStringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                Log.i(ContentValues.TAG, "-----Response :$response")
                val vueJsonObject = JSONObject(response)
                val vueObject = vueJsonObject.getJSONArray("vues")
                for (i in 0 until (vueObject.length())) {
                    val androidVueJSONObject = vueObject.getJSONObject(i)
                    androidVue.id = androidVueJSONObject.getLong("id")
                    androidVue.nomAppareilPhoto = androidVueJSONObject.getString("nomAppareilPhoto")
                    androidVue.latitude = androidVueJSONObject.getDouble("latitude")
                    androidVue.longitude = androidVueJSONObject.getDouble("longitude")
                    androidVue.sensibilite = androidVueJSONObject.getString("sensibilite")
                    androidVue.vitesses =
                        listOf(androidVueJSONObject.getJSONArray("vitesses").toString())
                    androidVue.ouvertures =
                        listOf(androidVueJSONObject.getJSONArray("ouvertures").toString())

                    fragmentAccueilBinding.appareilPhoto.setText(androidVue.nomAppareilPhoto)
                    fragmentAccueilBinding.sensibilite.setText(androidVue.sensibilite)

                }
            }
        ) { error ->
            Log.i(ContentValues.TAG, "------Error :$error")
        }
        mRequestQueue!!.add(mStringRequest)
    }
}