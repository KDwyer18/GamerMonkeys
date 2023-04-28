package com.example.gamermonke

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.room.*
import org.w3c.dom.Text


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Home.newInstance] factory method to
 * create an instance of this fragment.
 */
class Home (in_pfp: Bitmap?, in_name: String?, in_location: String?, in_age: String?, in_sex: String?, in_weight: String?, in_height: String?, in_activityLevel: String?) : Fragment() {
    private var pfp: Bitmap? = in_pfp
    private var name: String? = in_name
    private var location: String? = in_location
    private var age: String? = in_age
    private var sex: String? = in_sex
    private var weight: String? = in_weight
    private var height: String? = in_height
    private var foot: String? = null
    private var inch: String? = null
    private var activityLevel: String? = in_activityLevel

    private val feetArr = arrayOf("", "1","2","3","4","5","6")
    private val inchesArr = arrayOf("","0", "1","2","3","4","5","6","7","8","9","10","11")
    private val sexArr = arrayOf("","Male", "Female")
    private val activityLevelArr = arrayOf("", "Beginner", "Intermediate", "Advanced")

    private val intAges = IntArray(100) { (it + 1) }
    private var ageArr = arrayOf("")
    private val intWeight = IntArray(400) { (it + 1) }
    private var weightArr =  arrayOf("")

    private var updateButton: Button? = null
    private var nameText: EditText? = null
    private var locationText: EditText? = null
    private var activityLevelSpinner: Spinner? = null
    private var sexSpinner: Spinner? = null
    private var ageSpinner: Spinner? = null
    private var footSpinner: Spinner? = null
    private var inchSpinner: Spinner? = null
    private var weightSpinner: Spinner? = null

    var fragmentView: View? = null
    var thisContext: Context? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ageArr = ageArr.plus(intAges.map { it.toString() }.toTypedArray())
        weightArr = weightArr.plus(intWeight.map { it.toString() }.toTypedArray())
        foot = height!!.split("\'")[0]
        inch = height!!.split("\'")[1]



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        fragmentView = inflater.inflate(R.layout.fragment_home, container, false)
        thisContext = container!!.context

        nameText = fragmentView!!.findViewById(R.id.fullName)
        locationText = fragmentView!!.findViewById(R.id.location)
        activityLevelSpinner= fragmentView!!.findViewById(R.id.activityLevelSpinner)
        sexSpinner = fragmentView!!.findViewById(R.id.sexSpinner)
        ageSpinner = fragmentView!!.findViewById(R.id.ageSpinner)
        footSpinner = fragmentView!!.findViewById(R.id.heightFootSpinner)
        inchSpinner = fragmentView!!.findViewById(R.id.heightInchSpinner)
        weightSpinner = fragmentView!!.findViewById(R.id.weightSpinner)
        updateButton = fragmentView!!.findViewById(R.id.updateButton)

        nameText!!.setText(name)
        locationText!!.setText(location)

        initializingSpinner(R.id.ageSpinner, ageArr, R.id.ageText, age)
        initializingSpinner(R.id.sexSpinner, sexArr, R.id.sexText, sex)
        initializingSpinner(R.id.weightSpinner, weightArr, R.id.weightText, weight)
        initializingSpinner(R.id.heightFootSpinner, feetArr, R.id.footText, foot)
        initializingSpinner(R.id.heightInchSpinner, inchesArr, R.id.inchText, inch)
        initializingSpinner(R.id.activityLevelSpinner, activityLevelArr, R.id.activityLevelText, activityLevel)

        if(!activityLevel.isNullOrEmpty()){
            val activityLevelText = fragmentView!!.findViewById<TextView>(R.id.activityLevelText)
            activityLevelText.hint = ""
            activityLevelText.text = activityLevel
        }
        if(!age.isNullOrEmpty()){
            val ageText = fragmentView!!.findViewById<TextView>(R.id.ageText)
            ageText.hint = ""
            ageText.text = age
        }
        if(!sex.isNullOrEmpty()){
            val sexText = fragmentView!!.findViewById<TextView>(R.id.sexText)
            sexText.hint = ""
            sexText.text = sex
        }
        if(!foot.isNullOrEmpty()){
            val footText = fragmentView!!.findViewById<TextView>(R.id.footText)
            footText.hint = ""
            footText.text = foot
        }
        if(!inch.isNullOrEmpty()){
            val inchText = fragmentView!!.findViewById<TextView>(R.id.inchText)
            inchText.hint = ""
            inchText.text = inch
        }
        if(!weight.isNullOrEmpty()){
            val weightText = fragmentView!!.findViewById<TextView>(R.id.weightText)
            weightText.hint = ""
            weightText.text = weight
        }

        updateButton!!.setOnClickListener{
            val intent = Intent(thisContext, HomeActivity::class.java)
            if(!nameText!!.text.isNullOrEmpty()){
                name = nameText!!.text.toString()
            }
            if(!locationText!!.text.isNullOrEmpty()){
                location = locationText!!.text.toString()
            }
            activityLevel = selectedVal(activityLevelSpinner, activityLevel)
            sex = selectedVal(sexSpinner, sex)
            age = selectedVal(ageSpinner, age)
            foot = selectedVal(footSpinner, foot)
            inch = selectedVal(inchSpinner, inch)
            weight = selectedVal(weightSpinner, weight)

            intent.putExtra("EXTRA_FULLNAME", name)
            intent.putExtra("EXTRA_LOCATION", location)
            intent.putExtra("PFP_IMAGE", pfp)
            intent.putExtra("EXTRA_ACTIVITY", activityLevel)
            intent.putExtra("EXTRA_SEX", sex)
            intent.putExtra("EXTRA_AGE", age)
            intent.putExtra("EXTRA_FOOT", foot)
            intent.putExtra("EXTRA_INCHES", inch)
            intent.putExtra("EXTRA_WEIGHT", weight)
            startActivity(intent)
        }


        return fragmentView
    }

    private fun initializingSpinner(spinnerID: Int, array: Array<String>, textViewID: Int, tempVar: String?){
        var tempVar = ""
        val spinnerVal = fragmentView!!.findViewById<Spinner>(spinnerID)
        val spinnerAdapter = ArrayAdapter<String>(thisContext!!, android.R.layout.simple_spinner_dropdown_item, array)
        spinnerVal.adapter = spinnerAdapter
        spinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                tempVar = array[p2]
                val inchText = fragmentView!!.findViewById<TextView>(textViewID)
                if(tempVar != ""){
                    inchText.visibility = View.INVISIBLE
                } else {
                    inchText.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun selectedVal(spinner: Spinner?, str: String?): String?{
        if(spinner!!.selectedItem.toString().isNullOrEmpty()){
            if(str.isNullOrEmpty())
                return ""
            return str
        }
        return spinner!!.selectedItem.toString()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Home.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(in_pfp: Bitmap, in_name: String?, in_location: String?, in_age: String?, in_sex: String?, in_weight: String?, in_height: String?, in_activityLevel: String?) =
            Home(in_pfp, in_name, in_location, in_age, in_sex, in_weight, in_height, in_activityLevel).apply {
                arguments = Bundle().apply {

                }
            }
    }
}