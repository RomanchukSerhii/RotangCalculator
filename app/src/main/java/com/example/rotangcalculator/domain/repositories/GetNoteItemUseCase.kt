package com.example.rotangcalculator.domain.repositories

import com.example.rotangcalculator.domain.models.NoteItem

class GetNoteItemUseCase(
    private val repository: NoteListRepository
) {
    operator fun invoke(noteItemId: Int) : NoteItem {
        return repository.getNoteItem(noteItemId)
    }
}