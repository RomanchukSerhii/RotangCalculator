package com.example.rotangcalculator.domain.models

data class NoteItem(
    val title: String,
    val result: String,
    val highDiameter: Double,
    val lowerDiameter: Double,
    val length: Double,
    val width: Double,
    val number: Double,
    var id: Int = UNDEFINED_ID
) {
    companion object {
        const val UNDEFINED_ID = 0
    }
}