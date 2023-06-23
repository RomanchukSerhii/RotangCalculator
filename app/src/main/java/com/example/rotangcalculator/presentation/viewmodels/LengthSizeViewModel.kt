package com.example.rotangcalculator.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.rotangcalculator.presentation.viewmodels.Result
import javax.inject.Inject

class LengthSizeViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun calculate(
        highDiameterField: String,
        lowerDiameterField: String,
        lengthField: String,
        widthField: String,
        numberField: String
    ) {
        if (highDiameterField.isEmpty() || lowerDiameterField.isEmpty() || lengthField.isEmpty() || widthField.isEmpty() || numberField.isEmpty()) {
            _state.value = Error
        } else {
            val highDiameter = highDiameterField.toDouble()
            val lowerDiameter = lowerDiameterField.toDouble()
            val length = lengthField.toDouble()
            val width = widthField.toDouble()
            val number = numberField.toDouble()
            val size = (highDiameter + lowerDiameter) / 2
            val turnover = length / (number * width)
            val result = (kotlin.math.ceil(size * turnover)).toInt()
            _state.value = Result("$result см")
        }

    }
}