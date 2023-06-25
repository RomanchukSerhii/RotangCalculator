package com.example.rotangcalculator.presentation.adapters

import androidx.recyclerview.widget.RecyclerView
import com.example.rotangcalculator.databinding.NoteItemBinding
import com.example.rotangcalculator.domain.models.NoteItem

class NoteItemViewHolder(
    private val binding: NoteItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(noteItem: NoteItem) {
        with(binding) {
            tvNoteTitle.text = noteItem.title
            tvHighDiameter.text = noteItem.highDiameter.toString()
            tvLowerDiameter.text = noteItem.lowerDiameter.toString()
            tvLength.text = noteItem.length.toString()
            tvWidth.text = noteItem.width.toString()
            tvNumber.text = noteItem.number.toString()
            tvResultValue.text = noteItem.result

            itemView.tag = noteItem
            editButton.tag = noteItem
            deleteButton.tag = noteItem
        }
    }
}