package com.example.rotangcalculator.presentation.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.FragmentLengthSizeBinding
import com.example.rotangcalculator.domain.models.NoteItem
import com.example.rotangcalculator.presentation.App
import com.example.rotangcalculator.presentation.viewmodels.Error
import com.example.rotangcalculator.presentation.viewmodels.LengthSizeViewModel
import com.example.rotangcalculator.presentation.viewmodels.Result
import com.example.rotangcalculator.presentation.viewmodels.ViewModelFactory
import java.security.Provider.Service
import javax.inject.Inject


class LengthSizeFragment : Fragment() {
    private var _binding: FragmentLengthSizeBinding? = null
    private val binding: FragmentLengthSizeBinding
        get() = _binding ?: throw RuntimeException("FragmentLengthSizeBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[LengthSizeViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as App).component
    }

    private var screenMode: String = MODE_UNKNOWN
    private var noteItemId: Int = NoteItem.UNDEFINED_ID

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLengthSizeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchRightMode()
        observeViewModel()
        setClickListeners()
        setupSaveNoteDialogListener()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun launchRightMode() {
        if (screenMode == MODE_EDIT) {
            viewModel.getNoteItem(noteItemId)
        }
    }

    private fun observeViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Error -> setEditTextError()
                is Result -> {
                    binding.textViewResult.text = state.result
                }
            }
        }

        viewModel.noteItem.observe(viewLifecycleOwner) { noteItem ->
            with(binding) {
                etHighDiameter.setText(noteItem.highDiameter.toString())
                etLowerDiameter.setText(noteItem.lowerDiameter.toString())
                etLength.setText(noteItem.length.toString())
                etWidth.setText(noteItem.width.toString())
                etNumber.setText(noteItem.number.toString())
            }
        }
    }

    private fun setClickListeners() {
        with(binding) {
            btResult.setOnClickListener {
                viewModel.calculate(
                    highDiameterField = etHighDiameter.text.toString(),
                    lowerDiameterField = etLowerDiameter.text.toString(),
                    lengthField = etLength.text.toString(),
                    widthField = etWidth.text.toString(),
                    numberField = etNumber.text.toString()
                )
                hideKeyboard()
            }

            saveButton.setOnClickListener {
                val validateFields = viewModel.validateFields(
                    highDiameterField = etHighDiameter.text.toString(),
                    lowerDiameterField = etLowerDiameter.text.toString(),
                    lengthField = etLength.text.toString(),
                    widthField = etWidth.text.toString(),
                    numberField = etNumber.text.toString()
                )

                if (validateFields) {
                    val resultField = binding.textViewResult.text.toString()
                    if (resultField.isEmpty()) {
                        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                        inputMethodManager.hideSoftInputFromWindow(binding.saveButton.windowToken, 0)
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.save_warning),
                            Toast.LENGTH_SHORT
                        ).show()
                        return@setOnClickListener
                    }
                    showSaveNotDialogFragment()
                }
            }

            icBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }

            icClear.setOnClickListener {
                clearAllField()
            }

            openNoteListButton.setOnClickListener {
                val fragment = NoteListFragment.newInstance()
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }

    private fun showSaveNotDialogFragment() {
        SaveNoteDialogFragment.show(
            parentFragmentManager,
            LENGTH_SIZE_REQUEST_KEY
        )
    }

    private fun setupSaveNoteDialogListener() {
        val listener: SaveNoteDialogListener = { requestKey, noteTitle, _ ->
            if (requestKey == LENGTH_SIZE_REQUEST_KEY) {
                with(binding) {
                    viewModel.saveNoteItem(
                        title = noteTitle,
                        result = textViewResult.text.toString(),
                        highDiameterField = etHighDiameter.text.toString(),
                        lowerDiameterField = etLowerDiameter.text.toString(),
                        lengthField = etLength.text.toString(),
                        widthField = etWidth.text.toString(),
                        numberField = etNumber.text.toString()
                    )
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.note_is_saved),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
        SaveNoteDialogFragment.setupListener(
            parentFragmentManager,
            viewLifecycleOwner,
            LENGTH_SIZE_REQUEST_KEY,
            listener
        )
    }

    private fun setEditTextError() {
        with(binding) {
            if (etHighDiameter.text.isNullOrEmpty()) etHighDiameter.error =
                getString(R.string.error)
            if (etLowerDiameter.text.isNullOrEmpty()) etLowerDiameter.error =
                getString(R.string.error)
            if (etLength.text.isNullOrEmpty()) etLength.error = getString(R.string.error)
            if (etNumber.text.isNullOrEmpty()) etNumber.error = getString(R.string.error)
            if (etWidth.text.isNullOrEmpty()) etWidth.error = getString(R.string.error)
        }
    }

    private fun clearAllField() = with(binding) {
        etHighDiameter.text.clear()
        etLowerDiameter.text.clear()
        etWidth.text.clear()
        etNumber.text.clear()
        etLength.text.clear()
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.btResult.windowToken, 0)
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(ARG_SCREEN_MODE)) {
            throw RuntimeException("Param screen mode is absent")
        }

        val mode = args.getString(ARG_SCREEN_MODE)
        if (mode != MODE_ADD && mode != MODE_EDIT) {
            throw RuntimeException("Unknown screen mode: $mode")
        }
        screenMode = mode

        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(ARG_NOTE_ID)) {
                throw RuntimeException("Note item id is absent")
            }
            noteItemId = args.getInt(ARG_NOTE_ID)
        }

    }

    companion object {
        private const val LENGTH_SIZE_REQUEST_KEY = "LENGTH_SIZE_REQUEST_KEY"
        private const val ARG_SCREEN_MODE = "ARG_SCREEN_MODE"
        private const val ARG_NOTE_ID = "ARG_NOTE_ID"
        private const val MODE_EDIT = "MODE_EDIT"
        private const val MODE_ADD = "MODE_ADD"
        private const val MODE_UNKNOWN = "MODE_UNKNOWN"

        fun newInstanceAddMode(): LengthSizeFragment {
            return LengthSizeFragment().apply {
                arguments = bundleOf(
                    ARG_SCREEN_MODE to MODE_ADD
                )
            }
        }

        fun newInstanceEditMode(noteItemId: Int): LengthSizeFragment {
            return LengthSizeFragment().apply {
                arguments = bundleOf(
                    ARG_SCREEN_MODE to MODE_EDIT,
                    ARG_NOTE_ID to noteItemId
                )
            }
        }
    }
}