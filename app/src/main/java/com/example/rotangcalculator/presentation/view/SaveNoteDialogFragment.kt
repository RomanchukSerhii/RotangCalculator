package com.example.rotangcalculator.presentation.view

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentResultListener
import androidx.lifecycle.LifecycleOwner
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.PartNoteTitleInputBinding

typealias SaveNoteDialogListener = (requestKey: String, noteTitle: String) -> Unit

class SaveNoteDialogFragment : DialogFragment() {

    private val requestKey: String
        get() = requireArguments().getString(ARG_REQUEST_KEY) ?: ""

    private val noteTitle: String
        get() = requireArguments().getString(ARG_NOTE_TITLE) ?: ""

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBinding = PartNoteTitleInputBinding.inflate(layoutInflater)
        dialogBinding.etNoteTitle.setText(noteTitle)


        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.dialog_message))
            .setView(dialogBinding.root)
            .create()

        dialog.setOnShowListener {
            dialogBinding.etNoteTitle.requestFocus()
            showKeyboard(dialogBinding.etNoteTitle)

            dialogBinding.saveButton.setOnClickListener {
                val enteredText = dialogBinding.etNoteTitle.text.toString()
                if (enteredText.isBlank()) {
                    dialogBinding.etNoteTitle.error = getString(R.string.dialog_error)
                    return@setOnClickListener
                }
                parentFragmentManager.setFragmentResult(
                    requestKey, bundleOf(
                        KEY_INPUT_TITLE_RESPONSE to enteredText
                    )
                )
                dismiss()
            }

            dialogBinding.cancelButton.setOnClickListener {
                dismiss()
            }
        }

        dialog.setOnDismissListener { hideKeyboard(dialogBinding.etNoteTitle) }
        return dialog
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
        private const val KEY_INPUT_TITLE_RESPONSE = "KEY_INPUT_TITLE_RESPONSE"

        fun show(manager: FragmentManager, requestKey: String, noteTitle: String = "") {
            val dialogFragment = SaveNoteDialogFragment()
            dialogFragment.arguments = bundleOf(
                ARG_REQUEST_KEY to requestKey,
                ARG_NOTE_TITLE to noteTitle
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
                    listener.invoke(key, result.getString(KEY_INPUT_TITLE_RESPONSE) ?: "")
                }
            )
        }
    }
}