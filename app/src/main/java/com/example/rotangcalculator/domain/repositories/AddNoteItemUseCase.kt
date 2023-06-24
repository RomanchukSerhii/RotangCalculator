package com.example.rotangcalculator.domain.repositories

import com.example.rotangcalculator.domain.models.NoteItem
import javax.inject.Inject

class AddNoteItemUseCase @Inject constructor(
    private val repository: NoteListRepository
) {
    suspend operator fun invoke(noteItem: NoteItem) {
        repository.addNoteItem(noteItem)
    }
}