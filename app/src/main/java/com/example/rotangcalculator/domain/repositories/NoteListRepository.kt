package com.example.rotangcalculator.domain.repositories

import androidx.lifecycle.LiveData
import com.example.rotangcalculator.domain.models.NoteItem

interface NoteListRepository {

    suspend fun addNoteItem(noteItem: NoteItem)

    suspend fun deleteNoteItem(noteItem: NoteItem)

    suspend fun editNoteItem(noteItem: NoteItem)

    suspend fun getNoteItem(noteItemId: Int): NoteItem

    fun getNoteItemList(): LiveData<List<NoteItem>>
}