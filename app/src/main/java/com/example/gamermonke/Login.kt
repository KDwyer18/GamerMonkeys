package com.example.gamermonke

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

private var mTVUserName: TextView? = null
private var mTVAddress: TextView? = null
private var mTVAge: TextView? = null
private var mStringUserName: String? = null
private var mStringAddress: String? = null
private var mAge: String? = null
class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }
    override fun onSaveInstanceState(outState: Bundle)
    {
        // Call superclass so it can save view hierarchy
        super.onSaveInstanceState(outState)

        mStringUserName = mTVUserName!!.text.toString()
        mStringAddress = mTVAddress!!.text.toString()

        // Save user's current state
        outState.putString("UserName_Text", mStringUserName)
        outState.putString("Address_Text", mStringAddress)



    }

}