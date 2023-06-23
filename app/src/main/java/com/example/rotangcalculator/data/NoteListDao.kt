package com.example.rotangcalculator.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.rotangcalculator.domain.models.NoteItem

@Dao
interface NoteListDao {

    @Query("SELECT * FROM notes")
    fun getNoteList(): LiveData<List<NoteItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addNoteItem(noteItemDbModel: NoteItemDbModel)

    @Query("DELETE FROM notes WHERE id=:noteItemId")
    fun deleteNoteItem(noteItemId: Int)

    @Query("SELECT * FROM notes WHERE id=:noteItemId LIMIT 1")
    fun getNoteItem(noteItemId: Int): NoteItemDbModel
}