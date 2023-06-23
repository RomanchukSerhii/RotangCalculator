package com.example.rotangcalculator.domain.repositories

import androidx.lifecycle.LiveData
import com.example.rotangcalculator.domain.models.NoteItem

interface NoteItemRepository {

    fun addNoteItem(noteItem: NoteItem)

    fun deleteNoteItem(noteItem: NoteItem)

    fun editNoteItem(noteItem: NoteItem)

    fun getNoteItem(noteItemId: Int): LiveData<NoteItem>

    fun getNoteItemList(): LiveData<List<NoteItem>>
}