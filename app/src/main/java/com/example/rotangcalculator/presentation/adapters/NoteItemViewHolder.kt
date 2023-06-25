package com.example.rotangcalculator.presentation.adapters

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.NoteItemBinding
import com.example.rotangcalculator.domain.models.NoteItem

class NoteItemViewHolder(
    private val binding: NoteItemBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(noteItem: NoteItem) {
        with(binding) {
            val widthLine = formattedNumber(noteItem.width)
            tvNoteTitle.text = noteItem.title
            tvHighDiameter.text = context.getString(R.string.tv_high_diameter, noteItem.highDiameter.toInt())
            tvLowerDiameter.text = context.getString(R.string.tv_lower_diameter, noteItem.lowerDiameter.toInt())
            tvLength.text = context.getString(R.string.tv_length, noteItem.length.toInt())
            tvWidth.text = context.getString(R.string.tv_width, widthLine)
            tvNumber.text = context.getString(R.string.tv_number, noteItem.number.toInt())
            tvResultValue.text = noteItem.result

            itemView.tag = noteItem
            editButton.tag = noteItem
            deleteButton.tag = noteItem
        }
    }

    private fun formattedNumber(number: Double): String {
        val formatNumber = (number * 100).toInt()
        return if (formatNumber % 10 == 0 ) {
            String.format("%.1f", number)
        } else {
            String.format("%.2f", number)
        }
    }
}