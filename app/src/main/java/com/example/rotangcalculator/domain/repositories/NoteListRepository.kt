package com.example.rotangcalculator.domain.repositories

import androidx.lifecycle.LiveData
import com.example.rotangcalculator.domain.models.NoteItem

interface NoteListRepository {

    fun addNoteItem(noteItem: NoteItem)

    fun deleteNoteItem(noteItem: NoteItem)

    fun editNoteItem(noteItem: NoteItem)

    fun getNoteItem(noteItemId: Int): NoteItem

    fun getNoteItemList(): LiveData<List<NoteItem>>
}