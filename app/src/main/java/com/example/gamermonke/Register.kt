package com.example.gamermonke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.gamermonke.databinding.ActivityRegisterBinding


class Register : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding


    val genderArr = arrayOf("","male", "female")
    val activityLevelArr = arrayOf("", "beginner", "intermediate", "advanced")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)


        //Full Name
        var userFullName = findViewById<EditText>(R.id.fullName)

        //Location
        val userLocation = findViewById<EditText>(R.id.location)

        //Age
        val userAge = findViewById<EditText>(R.id.ageText)
        val ageString: String = userAge.text.toString()
        if(isNumeric(ageString)){
            //he valid
        } else {
            //invalid age
        }
        //Height
        val userHeight = findViewById<EditText>(R.id.heightText)

        //Weight
        val userWeight = findViewById<EditText>(R.id.weight)
        val weightString: String = userAge.text.toString()
        if(isNumeric(weightString)){
            //he valid
        } else {
            //invalid weight
        }

        //Gender Spinner
        var genderSpinnerVar = ""
        val genderSpinnerVal = findViewById<Spinner>(R.id.genderSpinner)
        val genderSpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, genderArr)
        genderSpinnerVal.adapter = genderSpinnerAdapter
        genderSpinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //("Not yet implemented")
                genderSpinnerVar = genderArr[p2]
                val genderText = findViewById<TextView>(R.id.genderText)
                if(genderSpinnerVar != ""){
                    genderText.visibility = View.INVISIBLE
                } else {
                    genderText.visibility = View.VISIBLE
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                //("Not yet implemented")
            }

        }


        //Activity Level Spinner
        var activitySpinnerVar = ""
        val activitySpinnerVal = findViewById<Spinner>(R.id.activityLevelSpinner)
        val activitySpinnerAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, activityLevelArr)
        activitySpinnerVal.adapter = activitySpinnerAdapter
        activitySpinnerVal.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //("Not yet implemented")
                activitySpinnerVar = activityLevelArr[p2]
                val activityText = findViewById<TextView>(R.id.activityLevelText)
                if(genderSpinnerVar != ""){
                    activityText.visibility = View.INVISIBLE
                } else {
                    activityText.visibility = View.VISIBLE
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //("Not yet implemented")
            }
        }


        //Profile Picture

        //Login Button
        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener{
            val msg: String = userFullName.text.toString()

            if(msg.trim().isNotEmpty()) {
                val intent = Intent(this, HomeActivity::class.java)
                intent.putExtra("EXTRA_FULLNAME", userFullName.text.toString())
                intent.putExtra("EXTRA_LOCATION", userLocation.text.toString())
                intent.putExtra("EXTRA_AGE", ageString)
                intent.putExtra("EXTRA_HEIGHT", userHeight.text.toString())
                intent.putExtra("EXTRA_WEIGHT", weightString)
                intent.putExtra("EXTRA_GENDER", genderSpinnerVar)
                intent.putExtra("EXTRA_ACTIVITY", activitySpinnerVar)
//                intent.putExtra("EXTRA_PFP")
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext, "Full Name is required", Toast.LENGTH_SHORT).show()
            }
        }

    }


    private fun isNumeric(toCheck : String): Boolean{
        val parsedInt = toCheck.toIntOrNull()
        return parsedInt != null
    }
}