package com.example.rotangcalculator.presentation.view

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.FragmentNoteListBinding
import com.example.rotangcalculator.domain.models.NoteItem
import com.example.rotangcalculator.presentation.App
import com.example.rotangcalculator.presentation.adapters.NoteItemActionListener
import com.example.rotangcalculator.presentation.adapters.NoteListAdapter
import com.example.rotangcalculator.presentation.viewmodels.NoteListViewModel
import com.example.rotangcalculator.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class NoteListFragment : Fragment() {
    private var _binding: FragmentNoteListBinding? = null
    private val binding: FragmentNoteListBinding
        get() = _binding ?: throw RuntimeException("FragmentNoteListBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val noteListAdapter: NoteListAdapter by lazy {
        val actionListener = getNoteItemActionListener()
        NoteListAdapter(actionListener)
    }

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[NoteListViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as App).component
    }

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupSaveNoteDialogListener()
        binding.recyclerView.adapter = noteListAdapter
    }

    private fun getNoteItemActionListener(): NoteItemActionListener {
        return object : NoteItemActionListener {
            override fun onNoteItemDelete(noteItem: NoteItem) {
                showDeleteDialog(noteItem)
            }

            override fun onNoteItemEdit(noteItem: NoteItem) {
                val fragment = LengthSizeFragment.newInstanceEditMode(noteItem.id)
                parentFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.fragment_container, fragment)
                    .commit()
            }

            override fun onNoteItemEditTitle(noteItem: NoteItem) {
                showSaveNoteDialogFragment(noteItem)
            }
        }
    }

    private fun showSaveNoteDialogFragment(noteItem: NoteItem) {
        SaveNoteDialogFragment.show(
            parentFragmentManager,
            NOTE_LIST_REQUEST_KEY,
            noteItem.title,
            noteItem.id
        )
    }

    private fun setupSaveNoteDialogListener() {
        val listener: SaveNoteDialogListener = { requestKey, noteTitle, noteId ->
            if (requestKey == NOTE_LIST_REQUEST_KEY) {
                viewModel.editNoteTitle(noteId, noteTitle)
            }
        }
        SaveNoteDialogFragment.setupListener(
            parentFragmentManager,
            viewLifecycleOwner,
            NOTE_LIST_REQUEST_KEY,
            listener
        )
    }

    private fun showDeleteDialog(noteItem: NoteItem) {
        val listener = DialogInterface.OnClickListener { _, which ->
            when (which) {
                DialogInterface.BUTTON_POSITIVE ->
                    viewModel.deleteNoteItem(noteItem)
            }
        }

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(R.string.delete_dialog_title)
            .setMessage(R.string.delete_dialog_message)
            .setPositiveButton("Так", listener)
            .setNegativeButton("Ні", null)
            .setCancelable(true)
            .create()

        dialog.setOnShowListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(requireContext(), R.color.brown_accent))
        }

        dialog.show()
    }

    private fun observeViewModel() {
        viewModel.noteItemList.observe(viewLifecycleOwner) {
            noteListAdapter.submitList(it)
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        private const val NOTE_LIST_REQUEST_KEY = "NOTE_LIST_REQUEST_KEY"
        fun newInstance(): NoteListFragment {
            return NoteListFragment()
        }
    }
}