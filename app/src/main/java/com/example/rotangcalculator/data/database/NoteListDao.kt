package com.example.rotangcalculator.data.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rotangcalculator.data.database.NoteItemDbModel
import com.example.rotangcalculator.domain.models.NoteItem

@Dao
interface NoteListDao {

    @Query("SELECT * FROM notes")
    fun getNoteList(): LiveData<List<NoteItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNoteItem(noteItemDbModel: NoteItemDbModel)

    @Query("DELETE FROM notes WHERE id=:noteItemId")
    suspend fun deleteNoteItem(noteItemId: Int)

    @Query("SELECT * FROM notes WHERE id=:noteItemId LIMIT 1")
    suspend fun getNoteItem(noteItemId: Int): NoteItemDbModel
}