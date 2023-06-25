package com.example.rotangcalculator.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotangcalculator.domain.models.NoteItem
import com.example.rotangcalculator.domain.repositories.DeleteNoteItemUseCase
import com.example.rotangcalculator.domain.repositories.EditNoteItemUseCase
import com.example.rotangcalculator.domain.repositories.GetNoteItemListUseCase
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val editNoteItemUseCase: EditNoteItemUseCase,
    private val deleteNoteItemUseCase: DeleteNoteItemUseCase,
    private val getNoteItemListUseCase: GetNoteItemListUseCase
) :ViewModel() {

    val noteItemList = getNoteItemListUseCase()

    fun editNoteTitle(noteItem: NoteItem, noteTitle: String) {
        val editNoteItem = noteItem.copy(title = noteTitle)
        viewModelScope.launch {
            editNoteItemUseCase(editNoteItem)
        }
    }

    fun deleteNoteItem(noteItem: NoteItem) {
        viewModelScope.launch {
            deleteNoteItemUseCase(noteItem)
        }
    }
}