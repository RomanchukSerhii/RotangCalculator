package com.example.rotangcalculator.presentation

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.FragmentPriceBasketBinding
import kotlin.math.ceil

const val PREF_PROGRESS = "PREF_PROGRESS"

class PriceBasketFragment : Fragment() {

    private lateinit var preferences: SharedPreferences
    private var progres = 0

    private var _binding: FragmentPriceBasketBinding? = null
    private val binding: FragmentPriceBasketBinding
        get() = _binding ?: throw RuntimeException("FragmentPriceBasketBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPriceBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSeekBarProgress()
        updateCoefficient(binding.seekBar)
        setListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setSeekBarProgress() {
        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        binding.seekBar.progress = preferences.getInt(PREF_PROGRESS, 45)
        progres = binding.seekBar.progress
    }

    private fun setListeners() {
        binding.icClear.setOnClickListener {
            clearAllField()
        }
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

    private fun updateCoefficient(seekBar: SeekBar) {
        val coefficient = (seekBar.progress + 100).toDouble()
        binding.coefficientTextView.text = getString(R.string.coefficient, coefficient / 100)
    }

    private fun calculatePrice(): String {
        with(binding) {
            val cover = if (withCover.isChecked) 2 else 1
            val height = etHeight.text.toString().toDouble()
            val width = etWidth.text.toString().toDouble()
            val length = etLength.text.toString().toDouble()
            val coefficient = (progres + 100).toDouble()
            val result =
                (ceil((height * width * 2 + height * length * 2 + width * length * cover) * (coefficient / 1000) + (width + height + length) * 2)).toInt()
            return "$result грн"
        }
    }

    private fun setEditTextError() {
        with(binding) {
            if (etHeight.text.isNullOrEmpty()) etHeight.error = getString(R.string.error)
            if (etLength.text.isNullOrEmpty()) etLength.error = getString(R.string.error)
            if (etWidth.text.isNullOrEmpty()) etWidth.error = getString(R.string.error)
        }
    }

    private fun clearAllField() = with(binding) {
        etHeight.text.clear()
        etWidth.text.clear()
        etLength.text.clear()
    }

    companion object {
        fun newInstance(): PriceBasketFragment {
            return PriceBasketFragment()
        }
    }
}