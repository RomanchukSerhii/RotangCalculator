package com.example.rotangcalculator.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.ActivityLengthSizeBinding

class LengthSizeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLengthSizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLengthSizeBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onClickBack(view: View) {
        finish()
    }

    fun onClickResult(view: View) {
        if (!isFieldEmpty())
            calculateLength()
    }

    fun onClickClear(view: View) {
        clearAllField()
    }

    private fun isFieldEmpty(): Boolean  {
        with(binding) {
            if (etHighDiameter.text.isNullOrEmpty()) etHighDiameter.error = getString(R.string.error)
            if (etLowerDiameter.text.isNullOrEmpty()) etLowerDiameter.error = getString(R.string.error)
            if (etLength.text.isNullOrEmpty()) etLength.error = getString(R.string.error)
            if (etNumber.text.isNullOrEmpty()) etNumber.error = getString(R.string.error)
            if (etWidth.text.isNullOrEmpty()) etWidth.error = getString(R.string.error)

            return etWidth.text.isNullOrEmpty() || etNumber.text.isNullOrEmpty() || etLength.text.isNullOrEmpty() || etLowerDiameter.text.isNullOrEmpty() ||etHighDiameter.text.isNullOrEmpty()
        }
    }

    private fun calculateLength() = with(binding) {
        val highDiameter = etHighDiameter.text.toString().toDouble()
        val lowerDiameter = etLowerDiameter.text.toString().toDouble()
        val length = etLength.text.toString().toDouble()
        val number = etNumber.text.toString().toDouble()
        val width = etWidth.text.toString().toDouble()
        val size = (highDiameter + lowerDiameter) / 2
        val turnover = length / (number * width)
        val result = (kotlin.math.ceil(size * turnover)).toInt()
        val tempText = "$result см"

        val intent = Intent(applicationContext, ResultActivity::class.java)
        intent.putExtra("result", tempText)
        startActivity(intent)
    }

    private fun clearAllField() = with(binding) {
        etHighDiameter.text.clear()
        etLowerDiameter.text.clear()
        etWidth.text.clear()
        etNumber.text.clear()
        etLength.text.clear()
    }
}