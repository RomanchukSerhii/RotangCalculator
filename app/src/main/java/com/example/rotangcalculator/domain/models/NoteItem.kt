package com.example.rotangcalculator.domain.models

data class NoteItem(
    val id: Int,
    val title: String,
    val result: String,
    val highDiameter: Double,
    val lowerDiameter: Double,
    val length: Double,
    val width: Double,
    val number: Double
)