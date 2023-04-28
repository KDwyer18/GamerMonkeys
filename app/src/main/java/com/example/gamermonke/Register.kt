package com.example.gamermonke

import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.graphics.drawable.toBitmap
import androidx.room.*
import androidx.room.RoomDatabase
import com.example.gamermonke.databinding.ActivityRegisterBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer


class Register : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityRegisterBinding


    private var fullName: EditText? = null
    private var userLocation: EditText? = null
    private var profileButton: Button? = null
    private var loginButton: Button? = null

    private var pfpView: ImageView? = null
    var db: RoomDatabase? = null

//    private val mUserViewModel: MainViewModel by viewModels {
//        UserViewModelFactory((application as UserApplication).repository)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_register)

        fullName = findViewById(R.id.fullName)
        userLocation = findViewById(R.id.location)
        profileButton = findViewById(R.id.pfpButton)
        loginButton = findViewById(R.id.loginButton)
        pfpView = findViewById(R.id.pfpImage)
        loginButton!!.setOnClickListener(this)
        profileButton!!.setOnClickListener(this)
    }

    override fun onClick(p0: View) {
        when (p0.id) {
            R.id.loginButton -> {
                if (!loginButton!!.text.toString().isNullOrEmpty()) {

//                    var names = mUserViewModel.allNames


//                        var user: UserData? = UserData()
//                        user!!.name = fullName!!.text.toString()
//                        user!!.location = userLocation!!.text.toString()
//                        //user!!.pfp = pfpView!!.drawable.toBitmap()
//                        mUserViewModel.setUserData(user)
                        val intent = Intent(this, Register2::class.java)
                        intent.putExtra("EXTRA_FULLNAME", fullName!!.text.toString())
                        intent.putExtra("EXTRA_LOCATION", userLocation!!.text.toString())
                        intent.putExtra("PFP_IMAGE", pfpView!!.drawable.toBitmap())
                        startActivity(intent)


                } else {
                    Toast.makeText(
                        applicationContext,
                        "Name is required",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            R.id.pfpButton -> {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                try {
                    cameraActivity.launch(cameraIntent)
                } catch (ex: ActivityNotFoundException) {
                    //Do error handling here
                }
            }
        }
    }

    private val cameraActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                pfpView = findViewById<View>(R.id.pfpImage) as ImageView

                if (Build.VERSION.SDK_INT >= 33) {
                    val thumbnailImage =
                        result.data!!.getParcelableExtra("data", Bitmap::class.java)
                    pfpView!!.setImageBitmap(thumbnailImage)
                } else {
                    val thumbnailImage = result.data!!.getParcelableExtra<Bitmap>("data")
                    pfpView!!.setImageBitmap(thumbnailImage)
                }


            }
        }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("EXTRA_FIRST", fullName!!.text.toString())
        outState.putString("EXTRA_LOCATION", userLocation!!.text.toString())
        outState.putParcelable("PFP_IMAGE", pfpView!!.drawable.toBitmap())
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        fullName!!.setText(savedInstanceState.getString("EXTRA_FIRST"))
        userLocation!!.setText(savedInstanceState.getString("EXTRA_LOCATION"))
        pfpView!!.setImageBitmap(savedInstanceState.getParcelable("PFP_IMAGE"))
    }
}