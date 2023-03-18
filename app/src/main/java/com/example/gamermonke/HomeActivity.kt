package com.example.gamermonke

import android.graphics.Bitmap
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.gamermonke.databinding.ActivityHomeBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


private lateinit var binding : ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private var weatherFragment: Fragment? = null
    private var BMRFragment: Fragment? = null
    private var homeFragment: Fragment? = null
    private var name: String? = null
    private var pfp:Bitmap? = null
    private var location:String? = ""
    private var activityLvl: String? = null
    private var sex: String? = null
    private var age: String? = null
    private var ft: String? = null
    private var inch: String? = null
    private var weight: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)

        name = intent.getStringExtra("EXTRA_FULLNAME")
        location = intent.getStringExtra("EXTRA_LOCATION")
        age = intent.getStringExtra("EXTRA_AGE")
        ft = intent.getStringExtra("EXTRA_FOOT")
        inch = intent.getStringExtra("EXTRA_INCHES")
        weight = intent.getStringExtra("EXTRA_WEIGHT")
        sex = intent.getStringExtra("EXTRA_SEX")
        activityLvl = intent.getStringExtra("EXTRA_ACTIVITY")
        pfp = intent.getParcelableExtra("PFP_IMAGE")
        if(savedInstanceState != null) {
            weatherFragment = supportFragmentManager.findFragmentByTag("weather_fragment")
        }
        else {
            weatherFragment = Weather(location)
            val fTrans = supportFragmentManager.beginTransaction()
            fTrans.replace(R.id.weather, weatherFragment as Weather, "weather_fragment")
            fTrans.commit()
        }
        val height:String? = "$ft'$inch"
        replaceFragment(Home(pfp, name, location, age, sex, weight, height, activityLvl))
        var reset = false

        val pfpView: ImageView = findViewById(R.id.pfpImage)
        pfpView.setImageBitmap(pfp)

        if(age.isNullOrBlank() || height.isNullOrBlank() || sex.isNullOrBlank() || sex.isNullOrBlank()){

            // Do not allow for BMR calculation if a field is blank
        }
        else {
            reset = true
        }

        val titleBar = findViewById<MaterialToolbar>(R.id.title_bar)
        if(reset) {
            val numAge = age!!.toInt()
            val numWeight = weight!!.toInt()

            val feetInch = height!!.split("\'")
            var inches = 12 * feetInch[0].toInt() + feetInch[1].toInt()

            var finalBMR = 0.0

            finalBMR = if (sex.equals("male")) {
                //66.47 + (6.24 * lbs) + (12.7 * inch) - (6.75 * age)
                66.47 + (6.24 * numWeight) + (12.7 * inches) - (6.75 * numAge)

            } else {
                // 65.51 + (4.35 * lbs) + (4.70 * inch) - (4.70 * age)
                65.51 + (4.35 * numWeight) + (4.7 * inches) - (4.70 * numAge)
            }

            if(activityLvl.equals("intermediate")) {
                titleBar.title= "Caloric Intake: " + String.format("%.0f", (finalBMR * 1.55))
            }
            else if(activityLvl.equals("advanced")){
                titleBar.title= "Caloric Intake: " + String.format("%.0f", (finalBMR * 1.725))
            }
            else{
                titleBar.title= "Caloric Intake: " + String.format("%.0f", (finalBMR * 1.2))
            }


        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){

                R.id.home -> replaceFragment(Home(pfp, name, location, age, sex, weight, height, activityLvl))
                R.id.bmr -> replaceFragment(BMR(age, height, weight, sex))
                R.id.hikes -> replaceFragment(Hikes(location))
                R.id.weather ->replaceFragment((Weather(location)))

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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("EXTRA_FULLNAME", name)
        outState.putString("EXTRA_LOCATION", location)
        outState.putParcelable("PFP_IMAGE", pfp)
        outState.putString("EXTRA_ACTIVITY", activityLvl)
        outState.putString("EXTRA_SEX", sex)
        outState.putString("EXTRA_AGE", age)
        outState.putString("EXTRA_FOOT", ft)
        outState.putString("EXTRA_INCHES", inch)
        outState.putString("EXTRA_WEIGHT", weight)
    }
}