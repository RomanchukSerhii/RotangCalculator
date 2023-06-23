package com.example.rotangcalculator.domain.repositories

import com.example.rotangcalculator.domain.models.NoteItem

class DeleteNoteItemUseCase(
    private val repository: NoteItemRepository
) {
    operator fun invoke(noteItem: NoteItem) {
        repository.deleteNoteItem(noteItem)
    }
}