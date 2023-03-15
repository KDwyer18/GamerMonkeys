package com.example.gamermonke

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.gamermonke.databinding.ActivityRegister2Binding

class Register2 : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityRegister2Binding

    private val feetArr = arrayOf("", "1","2","3","4","5","6")
    private val inchesArr = arrayOf("","0", "1","2","3","4","5","6","7","8","9","10","11")
    private val sexArr = arrayOf("","Male", "Female")
    private val activityLevelArr = arrayOf("", "Beginner", "Intermediate", "Advanced")
    private val intAges = IntArray(100) { (it + 1) }
    private var ageArr = arrayOf("")
    private val weight = IntArray(400) { (it + 1) }
    private var weightArr =  arrayOf("")

    private var pfp:Bitmap? = null
    private var name:String? = ""
    private var location:String? = ""
    private var activityLevelSpinner: Spinner? = null
    private var sexSpinner: Spinner? = null
    private var ageSpinner: Spinner? = null
    private var footSpinner: Spinner? = null
    private var inchSpinner: Spinner? = null
    private var weightSpinner: Spinner? = null
    private var loginButton: Button? = null

    private var activityVar: String? = ""
    private var sexVar: String? = ""
    private var ageVar: String? = ""
    private var footVar: String? = ""
    private var inchVar: String? = ""
    private var weightVar: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegister2Binding.inflate(layoutInflater)
        setContentView(R.layout.activity_register2)

        pfp = intent.getParcelableExtra("PFP_IMAGE")
        name = intent.getStringExtra("EXTRA_FULLNAME")
        location = intent.getStringExtra("EXTRA_LOCATION")

        activityLevelSpinner= findViewById(R.id.activityLevelSpinner)
        sexSpinner = findViewById(R.id.sexSpinner)
        ageSpinner = findViewById(R.id.ageSpinner)
        footSpinner = findViewById(R.id.heightFootSpinner)
        inchSpinner = findViewById(R.id.heightInchSpinner)
        weightSpinner = findViewById(R.id.weightSpinner)
        loginButton = findViewById(R.id.loginButton)

        ageArr = ageArr.plus(intAges.map { it.toString() }.toTypedArray())
        weightArr = weightArr.plus(weight.map { it.toString() }.toTypedArray())

        initializingSpinner(activityLevelSpinner!!.id, activityLevelArr, R.id.activityLevelText, activityVar)
        initializingSpinner(sexSpinner!!.id, sexArr, R.id.sexText, sexVar)
        initializingSpinner(ageSpinner!!.id, ageArr, R.id.ageText, ageVar)
        initializingSpinner(footSpinner!!.id, feetArr, R.id.footText, footVar)
        initializingSpinner(inchSpinner!!.id, inchesArr, R.id.inchText, inchVar)
        initializingSpinner(weightSpinner!!.id, weightArr, R.id.weightText, weightVar)

        loginButton!!.setOnClickListener(this)

    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.loginButton -> {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("EXTRA_FULLNAME", name)
                intent.putExtra("EXTRA_LOCATION", location)
                intent.putExtra("PFP_IMAGE", pfp)
                intent.putExtra("EXTRA_ACTIVITY", activityVar)
                intent.putExtra("EXTRA_SEX", sexVar)
                intent.putExtra("EXTRA_AGE", ageVar)
                intent.putExtra("EXTRA_FEET", footVar)
                intent.putExtra("EXTRA_INCHES", inchVar)
                intent.putExtra("EXTRA_WEIGHT", weightVar)
                startActivity(intent)
            }
        }
    }

    private fun initializingSpinner(spinnerID: Int, array: Array<String>, textViewID: Int, tempVar: String?){
        var tempVar = ""
        val spinnerVal = findViewById<Spinner>(spinnerID)
        val spinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, array)
        spinnerVal.adapter = spinnerAdapter
        spinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                tempVar = array[p2]
                val inchText = findViewById<TextView>(textViewID)
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

}
