package com.example.rotangcalculator.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotangcalculator.domain.models.NoteItem
import com.example.rotangcalculator.domain.repositories.AddNoteItemUseCase
import com.example.rotangcalculator.presentation.viewmodels.Result
import kotlinx.coroutines.launch
import javax.inject.Inject

class LengthSizeViewModel @Inject constructor(
    private val addNoteItemUseCase: AddNoteItemUseCase
) : ViewModel() {

    private val _state = MutableLiveData<State>()
    val state: LiveData<State> = _state

    fun calculate(
        highDiameterField: String,
        lowerDiameterField: String,
        lengthField: String,
        widthField: String,
        numberField: String
    ) {
        val validateFields = validateFields(
            highDiameterField,
            lowerDiameterField,
            lengthField,
            widthField,
            numberField
        )
        if (validateFields) {
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

    fun validateFields(
        highDiameterField: String,
        lowerDiameterField: String,
        lengthField: String,
        widthField: String,
        numberField: String
    ): Boolean {
        return if (highDiameterField.isEmpty() || lowerDiameterField.isEmpty() || lengthField.isEmpty() || widthField.isEmpty() || numberField.isEmpty()) {
            _state.value = Error
            false
        } else true
    }

    fun saveNoteItem(
        title: String,
        result: String,
        highDiameterField: String,
        lowerDiameterField: String,
        lengthField: String,
        widthField: String,
        numberField: String
    ) {
        val validateFields = validateFields(
            highDiameterField,
            lowerDiameterField,
            lengthField,
            widthField,
            numberField
        )
        if (validateFields) {
            val noteItem = NoteItem(
                title = title,
                result = result,
                highDiameter = highDiameterField.toDouble(),
                lowerDiameter = lowerDiameterField.toDouble(),
                length = lengthField.toDouble(),
                width = widthField.toDouble(),
                number = numberField.toDouble()
            )
            viewModelScope.launch {
                addNoteItemUseCase(noteItem)
            }
        }
    }
}