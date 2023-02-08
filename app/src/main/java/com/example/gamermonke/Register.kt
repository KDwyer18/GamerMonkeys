package com.example.gamermonke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.gamermonke.databinding.ActivityRegisterBinding


class Register : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding

    private val intAges = IntArray(100) { (it + 1) }
    val ages =  intAges.map { it.toString() }.toTypedArray()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener{
            openHomeActivity()
        }

        val spinner = findViewById<Spinner>(R.id.ageSpinner)
        val arrayAdapter = ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, ages)
        spinner.adapter = arrayAdapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                //("Not yet implemented")
                Toast.makeText(applicationContext, "selected age is =" + ages[p2], Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                //("Not yet implemented")
            }
        }


    }

    private fun openHomeActivity() {
        val intent = Intent(this, HomeActivity::class.java)
        startActivity(intent)
    }
}