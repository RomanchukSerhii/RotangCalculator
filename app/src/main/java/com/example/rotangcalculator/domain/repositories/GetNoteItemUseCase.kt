package com.example.rotangcalculator.domain.repositories

import com.example.rotangcalculator.domain.models.NoteItem
import javax.inject.Inject

class GetNoteItemUseCase @Inject constructor(
    private val repository: NoteListRepository
) {
    suspend operator fun invoke(noteItemId: Int) : NoteItem {
        return repository.getNoteItem(noteItemId)
    }
}