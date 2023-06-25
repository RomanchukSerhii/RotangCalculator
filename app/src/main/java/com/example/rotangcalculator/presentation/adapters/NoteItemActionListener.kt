package com.example.rotangcalculator.presentation.adapters

import com.example.rotangcalculator.domain.models.NoteItem

interface NoteItemActionListener {

    fun onNoteItemDelete(noteItem: NoteItem)

    fun onNoteItemEdit(noteItem: NoteItem)

    fun onNoteItemEditTitle(noteItem: NoteItem)
}