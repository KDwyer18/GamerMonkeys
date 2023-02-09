package com.example.gamermonke

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.gamermonke.databinding.ActivityMainBinding
import com.example.gamermonke.databinding.ActivityMainBinding.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
        openRegister()
    }

    private fun openRegister() {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
    }



}