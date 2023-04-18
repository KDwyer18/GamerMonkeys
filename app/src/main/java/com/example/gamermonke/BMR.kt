package com.example.gamermonke

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val USER_AGE = "age"
private const val USER_WEIGHT = "weight"
private const val USER_HEIGHT = "height"
private const val USER_GENDER = "gender"

private val mRecyclerView: RecyclerView? = null
private val mAdapter: RecyclerView.Adapter<*>? = null
private val layoutManager: RecyclerView.LayoutManager? = null

/**
 * A simple [Fragment] subclass.
 * Use the [BMR.newInstance] factory method to
 * create an instance of this fragment.
 */

class BMR() : Fragment() {

    private var age: String? = ""
    private var height: String? = ""
    private var weight: String? = ""
    private var gender: String? = ""
    private var reset: Boolean = false
    var fBMR: Double = 0.0

    // To be filled text fields:
    private var tvBMRSummary: TextView? = null
    private var tvBMR: TextView? = null
    private var mBMRViewModel: BMRViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        // Construct and call the ViewModel, set observer
        mBMRViewModel = ViewModelProvider(this)[BMRViewModel::class.java]
        mBMRViewModel!!.data.observe(viewLifecycleOwner, bmrObserver)


        val fragmentView = inflater.inflate(R.layout.fragment_b_m_r, container, false)

        if(height.isNullOrBlank() || gender.isNullOrBlank()){
            // Do not allow for BMR calculation if a field is blank
        }
        else {
            reset = true
        }

        tvBMRSummary = fragmentView.findViewById(R.id.bmr_placeholder) as TextView
        tvBMR = fragmentView.findViewById(R.id.bmr_total) as TextView
        if(reset) {
            val numAge = age!!.toInt()
            val numWeight = weight!!.toInt()

            val feetInch = height!!.split("\'")
            var inches = 12 * feetInch[0].toInt() + feetInch[1].toInt()

            var finalBMR = 0.0

            if (gender.equals("male")) {
                //66.47 + (6.24 * lbs) + (12.7 * inch) - (6.75 * age)
                finalBMR = 66.47 + (6.24 * numWeight) + (12.7 * inches) - (6.75 * numAge)

            }

            else {
                // 65.51 + (4.35 * lbs) + (4.70 * inch) - (4.70 * age)
                finalBMR = 65.51 + (4.35 * numWeight) + (4.7 * inches) - (4.70 * numAge)
            }
            fBMR = finalBMR
            mBMRViewModel!!.setBMR(finalBMR)
            tvBMRSummary!!.text = ""
            tvBMR!!.text = String.format("%.0f", finalBMR)
        }


        return fragmentView
    }

    private val bmrObserver: Observer<BMR> =
        Observer { bmrData ->
            if (bmrData != null) {
                tvBMRSummary!!.text = ""
                tvBMR!!.text = String.format("%.0f", bmrData.fBMR)
            }
        }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param in_age User's Age
         * @param in_height User's height
         * @param in_weight User's weight
         * @param in_gender User's gender
         *
         * @return A new instance of fragment BMR.
         */
        @JvmStatic
        fun newInstance(in_age: String?, in_height: String?, in_weight: String?, in_gender: String?) =
            BMR().apply {
                arguments = Bundle().apply {
                    age = in_age
                    height = in_height
                    weight = in_weight
                    gender = in_gender

                    putString(age, in_age)
                    putString(height, in_height)
                    putString(weight, in_weight)
                    putString(gender, in_gender)
                }
            }

    }
}