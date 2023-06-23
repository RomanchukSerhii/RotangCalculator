package com.example.rotangcalculator.data

import com.example.rotangcalculator.data.database.NoteItemDbModel
import com.example.rotangcalculator.domain.models.NoteItem
import javax.inject.Inject

class NoteItemMapper @Inject constructor(){

    fun mapEntityToDbModel(noteItem: NoteItem): NoteItemDbModel {
        return NoteItemDbModel(
            id = noteItem.id,
            title = noteItem.title,
            result = noteItem.result,
            highDiameter = noteItem.highDiameter,
            lowerDiameter = noteItem.lowerDiameter,
            length = noteItem.length,
            width = noteItem.width,
            number = noteItem.number
        )
    }

    fun mapDbModelToEntity(noteItemDbModel: NoteItemDbModel): NoteItem {
        return NoteItem(
            id = noteItemDbModel.id,
            title = noteItemDbModel.title,
            result = noteItemDbModel.result,
            highDiameter = noteItemDbModel.highDiameter,
            lowerDiameter = noteItemDbModel.lowerDiameter,
            length = noteItemDbModel.length,
            width = noteItemDbModel.width,
            number = noteItemDbModel.number
        )
    }

    fun mapListDbModelToListEntity(list: List<NoteItemDbModel>) = list.map {
        mapDbModelToEntity(it)
    }
}