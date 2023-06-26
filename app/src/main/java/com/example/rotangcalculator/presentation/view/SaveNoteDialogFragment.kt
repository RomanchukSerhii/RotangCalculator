package com.example.rotangcalculator.presentation.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.PartNoteTitleInputBinding

typealias SaveNoteDialogListener = (requestKey: String, noteTitle: String, noteId: Int) -> Unit

class SaveNoteDialogFragment : DialogFragment() {

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_KEY) ?: ""

    private val noteTitle: String
        get() = requireArguments().getString(ARG_NOTE_TITLE) ?: ""

    private val noteId: Int
        get() = requireArguments().getInt(ARG_NOTE_ID)

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = PartNoteTitleInputBinding.inflate(layoutInflater)


        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setView(dialogBinding.root)
            .create()

        dialog.setOnShowListener {
            with(dialogBinding) {
                if (savedInstanceState == null) {
                    etNoteTitle.setText(noteTitle)
                } else {
                    val inputTitle = savedInstanceState.getString(KEY_INPUT_TITLE)
                    Log.d("TAG", inputTitle.toString())
                    etNoteTitle.setText(inputTitle)
                }
                etNoteTitle.requestFocus()
                etNoteTitle.setSelection(etNoteTitle.text.length)
                showKeyboard(dialogBinding.etNoteTitle)

                dialogBinding.saveButton.setOnClickListener {
                    val enteredText = etNoteTitle.text.toString()
                    if (enteredText.isBlank()) {
                        etNoteTitle.error = getString(R.string.dialog_error)
                        return@setOnClickListener
                    }
                    parentFragmentManager.setFragmentResult(
                        requestKey, bundleOf(
                            KEY_INPUT_TITLE_RESPONSE to enteredText,
                            KEY_INPUT_ID_RESPONSE to noteId
                        )
                    )
                    dismiss()
                }

                cancelButton.setOnClickListener {
                    dismiss()
                }
            }

        }

        dialog.setOnDismissListener { hideKeyboard(dialogBinding.etNoteTitle) }
        return dialog
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val inputTitle = dialog?.findViewById<EditText>(R.id.etNoteTitle)
        outState.putString(KEY_INPUT_TITLE, inputTitle?.text.toString())
    }

    private fun showKeyboard(view: View) {
        view.post {
            getInputMethodManager(view).showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
        }
    }

    private fun hideKeyboard(view: View) {
        getInputMethodManager(view).hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun getInputMethodManager(view: View): InputMethodManager {
        val context = view.context
        return context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    }

    companion object {
        private val TAG = SaveNoteDialogFragment::class.java.simpleName
        private const val ARG_REQUEST_KEY = "ARG_REQUEST_KEY"
        private const val ARG_NOTE_TITLE = "ARG_NOTE_TITLE"
        private const val ARG_NOTE_ID = "ARG_NOTE_ID"
        private const val KEY_INPUT_TITLE = "KEY_INPUT_TITLE"
        private const val KEY_INPUT_TITLE_RESPONSE = "KEY_INPUT_TITLE_RESPONSE"
        private const val KEY_INPUT_ID_RESPONSE = "KEY_INPUT_ID_RESPONSE"

        fun show(
            manager: FragmentManager,
            requestKey: String,
            noteTitle: String = "",
            noteId: Int = 0
        ) {
            val dialogFragment = SaveNoteDialogFragment()
            dialogFragment.arguments = bundleOf(
                ARG_REQUEST_KEY to requestKey,
                ARG_NOTE_TITLE to noteTitle,
                ARG_NOTE_ID to noteId
            )
            dialogFragment.show(manager, TAG)
        }

        fun setupListener(
            manager: FragmentManager,
            lifecycleOwner: LifecycleOwner,
            requestKey: String,
            listener: SaveNoteDialogListener
        ) {
            manager.setFragmentResultListener(
                requestKey,
                lifecycleOwner,
                FragmentResultListener { key, result ->
                    listener.invoke(
                        key,
                        result.getString(KEY_INPUT_TITLE_RESPONSE) ?: "",
                        result.getInt(KEY_INPUT_ID_RESPONSE)
                    )
                }
            )
        }
    }
}