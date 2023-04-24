package com.example.gamermonke

import android.content.ActivityNotFoundException
import android.content.Intent
import android.location.Address
import android.location.Geocoder
import android.location.LocationRequest
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.net.URL

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_LOCATION = "location"

/**
 * A simple [Fragment] subclass.
 * Use the [Hikes.newInstance] factory method to
 * create an instance of this fragment.
 */
class Hikes() : Fragment() {
    var location: String? = ""

    // For the ViewModel
    private var mHikesViewModel: HikesViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            location = it.getString(ARG_LOCATION)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Construct and call the ViewModel, set observer
        mHikesViewModel = ViewModelProvider(this)[HikesViewModel::class.java]
        mHikesViewModel!!.data.observe(viewLifecycleOwner, hikesObserver)

        // Inflate the layout for this fragment
        if(location.isNullOrBlank())
            location = "Salt Lake City, Utah"

        mHikesViewModel!!.setLocation(location!!)

        val geocoder = Geocoder(this.requireContext())
        var error = ""

        val addresses = geocoder.getFromLocationName(location!!, 5)
        var address = addresses?.get(0)
        val lat = address?.latitude
        val lon = address?.longitude

        val searchUri = Uri.parse("geo:$lat, $lon?q=hikes")
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

    private val hikesObserver: Observer<Hikes> =
        Observer { hikesData ->
            if (hikesData != null) {
                location = hikesData.location
            }
        }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param in_location Location that the user provides. SLC,UT by default.
         * @return A new instance of fragment TBD.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(in_location: String?) =
            Hikes().apply {
                arguments = Bundle().apply {
                    putString(ARG_LOCATION, in_location)
                    location = in_location
                }
            }
    }
}