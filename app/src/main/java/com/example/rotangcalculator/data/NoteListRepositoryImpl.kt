package com.example.rotangcalculator.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.rotangcalculator.data.database.NoteListDao
import com.example.rotangcalculator.domain.models.NoteItem
import com.example.rotangcalculator.domain.repositories.NoteListRepository

class NoteListRepositoryImpl(
    private val noteListDao: NoteListDao
) : NoteListRepository {


    override fun addNoteItem(noteItem: NoteItem) {
        noteListDao
    }

    override fun deleteNoteItem(noteItem: NoteItem) {
        TODO("Not yet implemented")
    }

    override fun editNoteItem(noteItem: NoteItem) {
        TODO("Not yet implemented")
    }

    override fun getNoteItem(noteItemId: Int): NoteItem {
        TODO("Not yet implemented")
    }

    override fun getNoteItemList(): LiveData<List<NoteItem>> {
        TODO("Not yet implemented")
    }
}