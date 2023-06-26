package com.example.rotangcalculator.data

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.map
import com.example.rotangcalculator.data.database.NoteListDao
import com.example.rotangcalculator.domain.models.NoteItem
import com.example.rotangcalculator.domain.repositories.NoteListRepository
import javax.inject.Inject

class NoteListRepositoryImpl @Inject constructor(
    private val noteListDao: NoteListDao,
    private val mapper: NoteItemMapper
) : NoteListRepository {


    override suspend fun addNoteItem(noteItem: NoteItem) {
        Log.d("NoteTAG", noteItem.id.toString())
        noteListDao.addNoteItem(mapper.mapEntityToDbModel(noteItem))
    }

    override suspend fun deleteNoteItem(noteItem: NoteItem) {
        noteListDao.deleteNoteItem(noteItem.id)
    }

    override suspend fun editNoteItem(noteItem: NoteItem) {
        noteListDao.addNoteItem(mapper.mapEntityToDbModel(noteItem))
    }

    override suspend fun getNoteItem(noteItemId: Int): NoteItem {

        Log.d("TAG", noteItemId.toString())
        val dbModel = noteListDao.getNoteItem(noteItemId)
        return mapper.mapDbModelToEntity(dbModel)
    }

    override fun getNoteItemList(): LiveData<List<NoteItem>> = noteListDao.getNoteList().map {
        mapper.mapListDbModelToListEntity(it)
    }

}