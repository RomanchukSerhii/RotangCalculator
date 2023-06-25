package com.example.rotangcalculator.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.NoteItemBinding
import com.example.rotangcalculator.domain.models.NoteItem

class NoteListAdapter(
    private val actionListener: NoteItemActionListener
) : ListAdapter<NoteItem, NoteItemViewHolder>(DiffCallback), View.OnClickListener {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemViewHolder {
        val binding = NoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        binding.root.setOnClickListener(this)
        binding.deleteButton.setOnClickListener(this)
        binding.editButton.setOnClickListener(this)
        return NoteItemViewHolder(binding, parent.context)
    }

    override fun onBindViewHolder(holder: NoteItemViewHolder, position: Int) {
        val noteItem = getItem(position)
        holder.bind(noteItem)
    }

    override fun onClick(v: View) {
        val noteItem = v.tag as NoteItem
        when(v.id) {
            R.id.edit_button -> {
                actionListener.onNoteItemEditTitle(noteItem)
            }
            R.id.delete_button -> {
                actionListener.onNoteItemDelete(noteItem)
            }
            else -> {
                actionListener.onNoteItemEdit(noteItem)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<NoteItem>() {
            override fun areItemsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: NoteItem, newItem: NoteItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}