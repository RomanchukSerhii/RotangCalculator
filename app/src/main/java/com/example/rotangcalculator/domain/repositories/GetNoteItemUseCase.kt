package com.example.rotangcalculator.domain.repositories

import androidx.lifecycle.LiveData
import com.example.rotangcalculator.domain.models.NoteItem

class GetNoteItemUseCase(
    private val repository: NoteItemRepository
) {
    operator fun invoke(): LiveData<List<NoteItem>> {
        return repository.getNoteItemList()
    }
}