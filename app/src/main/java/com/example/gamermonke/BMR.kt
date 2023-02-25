package com.example.gamermonke

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
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
class BMR(in_age: String?, in_height: String?, in_weight: String?, in_gender: String?) : Fragment() {

    private var age: String? = in_age
    private var height: String? = in_height
    private var weight: String? = in_weight
    private var gender: String? = in_gender
    private var reset: Boolean = false

    // To be filled text fields:
    private var tvBMRSummary: TextView? = null
    private var tvBMR: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

//            age = it.getString(USER_AGE)
//            weight = it.getString(USER_WEIGHT)
//            height = it.getString(USER_HEIGHT)
//            gender = it.getString(USER_GENDER)
//
//            if(age.isNullOrBlank() || height.isNullOrBlank() || weight.isNullOrBlank() || gender.isNullOrBlank()){
//                // Do not allow for BMR calculation if a field is blank
//            }
//            else {
//                reset = true
//            }

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

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

            tvBMRSummary!!.text = ""
            tvBMR!!.text = finalBMR.toString()
        }


        return fragmentView
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
        fun newInstance(in_age: String, in_height: String, in_weight: String, in_gender: String) =
            BMR(in_age, in_height, in_weight, in_gender).apply {
                arguments = Bundle().apply {
//                    putString(USER_AGE, age)
//                    putString(USER_HEIGHT, height)
//                    putString(USER_WEIGHT, weight)
//                    putString(USER_GENDER, gender)
//
//                    age = in_age
//                    height = in_height
//                    weight = in_weight
//                    gender = in_gender
                }
            }
    }
}