package com.example.rotangcalculator.domain.repositories

import androidx.lifecycle.LiveData
import com.example.rotangcalculator.domain.models.NoteItem
import javax.inject.Inject

class GetNoteItemListUseCase @Inject constructor(
    private val repository: NoteListRepository
) {
    operator fun invoke(): LiveData<List<NoteItem>> {
        return repository.getNoteItemList()
    }
}