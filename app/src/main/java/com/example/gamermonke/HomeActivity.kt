package com.example.gamermonke

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.gamermonke.databinding.ActivityHomeBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomnavigation.BottomNavigationView


private lateinit var binding : ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    var pfpView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_home)

        val name = intent.getStringExtra("EXTRA_FULLNAME")
        val location = intent.getStringExtra("EXTRA_LOCATION")
        val age = intent.getStringExtra("EXTRA_AGE")
        val ft = intent.getStringExtra("EXTRA_FOOT")
        val inch = intent.getStringExtra("EXTRA_INCHES")
        val weight = intent.getStringExtra("EXTRA_WEIGHT")
        val sex = intent.getStringExtra("EXTRA_SEX")
        val activityLvl = intent.getStringExtra("EXTRA_ACTIVITY")
        var pfp: Bitmap? = intent.getParcelableExtra("PFP_IMAGE")

        val height:String? = "$ft'$inch"
        replaceFragment(Home.newInstance(pfp, name, location, age, sex, weight, height, activityLvl))
        var reset = false

        pfpView = findViewById(R.id.pfpImage)
        pfpView!!.setImageBitmap(pfp)

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

        pfpView!!.setOnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try{
                cameraActivity.launch(cameraIntent)
            } catch (ex: ActivityNotFoundException){}
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener {

            when (it.itemId) {

                R.id.home -> replaceFragment(
                    Home.newInstance(
                        pfp,
                        name,
                        location,
                        age,
                        sex,
                        weight,
                        height,
                        activityLvl
                    )
                )

                R.id.bmr -> replaceFragment(BMR.newInstance(age, height, weight, sex))
                R.id.hikes -> replaceFragment(Hikes.newInstance(location))
                R.id.weather -> replaceFragment(Weather.newInstance(location))

                else -> {}
            }

            true
        }

    }

    private val cameraActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == RESULT_OK) {
            pfpView = findViewById<View>(R.id.pfpImage) as ImageView

            if (Build.VERSION.SDK_INT >= 33) {
                val thumbnailImage = result.data!!.getParcelableExtra("data", Bitmap::class.java)
                pfpView!!.setImageBitmap(thumbnailImage)
            }
            else{
                val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                pfpView!!.setImageBitmap(thumbnailImage)
            }


        }
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}