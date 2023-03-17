package com.example.gamermonke

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Hikes.newInstance] factory method to
 * create an instance of this fragment.
 */
class Hikes : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var EtLocation: EditText? = null
    private var location: String? = null
    var API_KEY ="AIzaSyACLzFEX9E_clLJ8RVBCC_MZAjF-aA5Em0"
    var latitude = 40.767778
    var longitude = -111.845205
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        location = "Draper, Utah"

        val searchUri = Uri.parse("geo:40.767778, -111.845205?q=hikes")
        val mapIntent = Intent(Intent.ACTION_VIEW, searchUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        try{
            startActivity(mapIntent)
        }
        catch(ex: ActivityNotFoundException){
            // handle errors
        }
        return inflater.inflate(R.layout.fragment_map, container, false)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TBD.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Hikes().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}