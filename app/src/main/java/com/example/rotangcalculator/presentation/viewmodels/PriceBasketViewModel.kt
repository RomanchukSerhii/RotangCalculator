package com.example.rotangcalculator.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rotangcalculator.presentation.viewmodels.Result
import javax.inject.Inject
import kotlin.math.ceil

class PriceBasketViewModel @Inject constructor(): ViewModel() {
    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun calculate(
        cover: Int,
        heightField: String,
        widthField: String,
        lengthField: String,
        coefficient: Double
    ) {
        if (heightField.isEmpty() || widthField.isEmpty() || lengthField.isEmpty()) {
            _state.value = Error
        } else {
            val height = heightField.toDouble()
            val width = widthField.toDouble()
            val length = lengthField.toDouble()
            val result =
                (ceil((height * width * 2 + height * length * 2 + width * length * cover) * (coefficient / 1000) + (width + height + length) * 2)).toInt()
            _state.value = Result("$result грн")
        }
    }
}