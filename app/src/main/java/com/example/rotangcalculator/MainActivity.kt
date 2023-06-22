package com.example.rotangcalculator

import android.app.StatusBarManager
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.rotangcalculator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClickLengthSize(view: View) {
        startActivity(Intent(this, LengthSizeActivity::class.java))
    }

    fun onClickPrice(view: View) {
        startActivity(Intent(this, PriceActivity::class.java))
    }
}