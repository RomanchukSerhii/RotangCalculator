package com.example.rotangcalculator.domain.repositories

import com.example.rotangcalculator.domain.models.NoteItem

class EditNoteItemUseCase(
    private val repository: NoteListRepository
) {
    suspend operator fun invoke(noteItem: NoteItem) {
        repository.editNoteItem(noteItem)
    }
}