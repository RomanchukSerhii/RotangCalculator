package com.example.rotangcalculator.presentation

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SeekBar
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.ActivityPriceBinding
import kotlin.math.ceil


const val PREF_PROGRESS = "PREF_PROGRESS"

class PriceActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPriceBinding
    private lateinit var preferences: SharedPreferences
    private var progres = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPriceBinding.inflate(layoutInflater)
        setContentView(binding.root)
        preferences = getPreferences(Context.MODE_PRIVATE)

        binding.seekBar.progress = preferences.getInt(PREF_PROGRESS, 45)
        progres = binding.seekBar.progress
        updateCoefficient(binding.seekBar)
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (seekBar != null) {
                    updateCoefficient(seekBar)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    progres = seekBar.progress
                    preferences.edit()
                        .putInt(PREF_PROGRESS, progres)
                        .apply()
                }
            }
        })
    }

    fun onClickBack(view: View) {
        finish()
    }

    fun onClickResult(view: View) {
        if (!isFieldEmpty())
            calculatePrice()
    }

    fun onClickClear(view: View) {
        clearAllField()
    }

    private fun updateCoefficient(seekBar: SeekBar) {
        val coefficient = (seekBar.progress + 100).toDouble()
        binding.coefficientTextView.text = getString(R.string.coefficient, coefficient / 100)
    }

    private fun calculatePrice() = with(binding) {
        val cover = if (withCover.isChecked) { 2 } else { 1 }
        val height = etHeight.text.toString().toDouble()
        val width = etWidth.text.toString().toDouble()
        val length = etLength.text.toString().toDouble()
        val coefficient = (progres + 100).toDouble()
        Log.d("MyLog", "$coefficient")
        val result = (ceil((height * width * 2 + height * length * 2 + width * length * cover) * (coefficient / 1000) + (width + height + length) * 2)).toInt()
        val tempText = "$result грн"

        val intent = Intent(applicationContext, ResultActivity::class.java)
        intent.putExtra("result", tempText)
        startActivity(intent)
    }

    private fun isFieldEmpty(): Boolean  {
        with(binding) {
            if (etHeight.text.isNullOrEmpty()) etHeight.error = getString(R.string.error)
            if (etLength.text.isNullOrEmpty()) etLength.error = getString(R.string.error)
            if (etWidth.text.isNullOrEmpty()) etWidth.error = getString(R.string.error)

            return etWidth.text.isNullOrEmpty() || etHeight.text.isNullOrEmpty() || etLength.text.isNullOrEmpty()
        }
    }

    private fun clearAllField() = with(binding) {
        etHeight.text.clear()
        etWidth.text.clear()
        etLength.text.clear()
    }
}