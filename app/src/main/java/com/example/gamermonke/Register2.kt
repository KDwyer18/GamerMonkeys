package com.example.gamermonke

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.gamermonke.databinding.ActivityRegister2Binding



private lateinit var binding: ActivityRegister2Binding
class Register2 : AppCompatActivity(){

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

    private var activityVar = ""
    private var sexVar = ""
    private var ageVar = ""
    private var footVar = ""
    private var inchVar = ""
    private var weightVar = ""

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


//        loginButton!!.setOnClickListener(this)

        var activitySpinnerVar = ""
        val activitySpinnerVal = findViewById<Spinner>(R.id.activityLevelSpinner)
        val activitySpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activityLevelArr)
        activitySpinnerVal.adapter = activitySpinnerAdapter
        activitySpinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //("Not yet implemented")
                activitySpinnerVar = activityLevelArr[p2]
                val activityText = findViewById<TextView>(R.id.activityLevelText)
                if(activitySpinnerVar != ""){
                    activityText.visibility = View.INVISIBLE
                } else {
                    activityText.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //("Not yet implemented")
            }
        }

        val sexSpinnerVal = findViewById<Spinner>(R.id.sexSpinner)
        val sexSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, sexArr)
        sexSpinnerVal.adapter = sexSpinnerAdapter
        sexSpinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                sexVar = sexArr[p2]
                val sexText = findViewById<TextView>(R.id.sexText)
                if(sexVar != ""){
                    sexText.visibility = View.INVISIBLE
                } else {
                    sexText.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val ageSpinnerVal = findViewById<Spinner>(R.id.ageSpinner)
        val ageSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ageArr)
        ageSpinnerVal.adapter = ageSpinnerAdapter
        ageSpinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                ageVar = ageArr[p2]
                val ageText = findViewById<TextView>(R.id.ageText)
                if(ageVar != ""){
                    ageText.visibility = View.INVISIBLE
                } else {
                    ageText.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val footSpinnerVal = findViewById<Spinner>(R.id.heightFootSpinner)
        val footSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, feetArr)
        footSpinnerVal.adapter = footSpinnerAdapter
        footSpinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                footVar = feetArr[p2]
                val footText = findViewById<TextView>(R.id.footText)
                if(footVar != ""){
                    footText.visibility = View.INVISIBLE
                } else {
                    footText.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val inchSpinnerVal = findViewById<Spinner>(R.id.heightInchSpinner)
        val inchSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, inchesArr)
        inchSpinnerVal.adapter = inchSpinnerAdapter
        inchSpinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                inchVar = inchesArr[p2]
                val inchText = findViewById<TextView>(R.id.inchText)
                if(inchVar != ""){
                    inchText.visibility = View.INVISIBLE
                } else {
                    inchText.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        val weightSpinnerVal = findViewById<Spinner>(R.id.weightSpinner)
        val weightSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, weightArr)
        weightSpinnerVal.adapter = weightSpinnerAdapter
        weightSpinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                weightVar = weightArr[p2]
                val weightText = findViewById<TextView>(R.id.weightText)
                if(weightVar != ""){
                    weightText.visibility = View.INVISIBLE
                } else {
                    weightText.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        loginButton!!.setOnClickListener{
            println(name)
            println(location)
            println(pfp)
            println(activityVar)
            println(sexVar)
            println(ageVar)
            println(footVar)
            println(inchVar)
            println(weightVar)
            val intent = Intent(this, HomeActivity::class.java)
            intent.putExtra("EXTRA_FULLNAME", name)
            intent.putExtra("EXTRA_LOCATION", location)
            intent.putExtra("PFP_IMAGE", pfp)
            intent.putExtra("EXTRA_ACTIVITY", activityVar)
            intent.putExtra("EXTRA_SEX", sexVar)
            intent.putExtra("EXTRA_AGE", ageVar)
            intent.putExtra("EXTRA_FOOT", footVar)
            intent.putExtra("EXTRA_INCHES", inchVar)
            intent.putExtra("EXTRA_WEIGHT", weightVar)
            startActivity(intent)
        }

    }
}
