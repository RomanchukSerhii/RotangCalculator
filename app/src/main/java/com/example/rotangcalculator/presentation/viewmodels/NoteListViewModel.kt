package com.example.rotangcalculator.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rotangcalculator.domain.models.NoteItem
import com.example.rotangcalculator.domain.repositories.DeleteNoteItemUseCase
import com.example.rotangcalculator.domain.repositories.EditNoteItemUseCase
import com.example.rotangcalculator.domain.repositories.GetNoteItemListUseCase
import com.example.rotangcalculator.domain.repositories.GetNoteItemUseCase
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoteListViewModel @Inject constructor(
    private val editNoteItemUseCase: EditNoteItemUseCase,
    private val deleteNoteItemUseCase: DeleteNoteItemUseCase,
    private val getNoteItemUseCase: GetNoteItemUseCase,
    private val getNoteItemListUseCase: GetNoteItemListUseCase
) :ViewModel() {

    val noteItemList = getNoteItemListUseCase()

    fun editNoteTitle(noteItemId: Int, noteTitle: String) {
        val deferredNoteItem = viewModelScope.async {
            getNoteItemUseCase(noteItemId)
        }
        viewModelScope.launch {
            val oldNoteItem = deferredNoteItem.await()
            val editNoteItem = oldNoteItem.copy(title = noteTitle)
            editNoteItemUseCase(editNoteItem)
        }
    }

    fun deleteNoteItem(noteItem: NoteItem) {
        viewModelScope.launch {
            deleteNoteItemUseCase(noteItem)
        }
    }
}