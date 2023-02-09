package com.example.gamermonke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.gamermonke.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

private lateinit var binding : ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)

        val name = intent.getStringExtra("EXTRA_FULLNAME")
        val location = intent.getStringExtra("EXTRA_LOCATION")
        val age = intent.getStringExtra("EXTRA_AGE")
        val height = intent.getStringExtra("EXTRA_HEIGHT")
        val weight = intent.getStringExtra("EXTRA_WEIGHT")
        val gender = intent.getStringExtra("EXTRA_GENDER")
        val activityLvl = intent.getStringExtra("EXTRA_ACTIVITY")
        replaceFragment(Home())

        println(name)
        println(location)
        println(age)
        println(height)
        println(weight)
        println(gender)
        println(activityLvl)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.home -> replaceFragment(Home())
                R.id.bmr -> replaceFragment(BMR())
                R.id.tbd -> replaceFragment(TBD())

                else ->{}
            }

            true
        }

    }



    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}