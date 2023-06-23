package com.example.rotangcalculator.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "notes")
data class NoteItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "result")
    val result: String,

    @ColumnInfo(name = "high_diameter")
    val highDiameter: Double,

    @ColumnInfo(name = "lower_diameter")
    val lowerDiameter: Double,

    @ColumnInfo(name = "length")
    val length: Double,

    @ColumnInfo(name = "width")
    val width: Double,

    @ColumnInfo(name = "number")
    val number: Double
)