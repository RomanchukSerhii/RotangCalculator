package com.example.rotangcalculator.domain.repositories

import androidx.lifecycle.LiveData
import com.example.rotangcalculator.domain.models.NoteItem

class GetNoteItemListUseCase(
    private val repository: NoteItemRepository
) {
    operator fun invoke(noteItemId: Int) : LiveData<NoteItem> {
        return repository.getNoteItem(noteItemId)
    }
}