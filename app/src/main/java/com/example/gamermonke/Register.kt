package com.example.gamermonke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.gamermonke.databinding.ActivityMainBinding


class Register : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    val intAges = IntArray(100) { (it + 1) }
//    val ages =  intAges.map { it.toString() }.toTypedArray()

    val ages = arrayOf("1","2","3")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)

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
}