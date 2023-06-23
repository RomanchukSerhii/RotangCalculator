package com.example.rotangcalculator.presentation.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.SeekBar
import androidx.lifecycle.ViewModelProvider
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.FragmentPriceBasketBinding
import com.example.rotangcalculator.presentation.App
import com.example.rotangcalculator.presentation.viewmodels.PriceBasketViewModel
import com.example.rotangcalculator.presentation.viewmodels.ViewModelFactory
import com.example.rotangcalculator.presentation.viewmodels.Error
import com.example.rotangcalculator.presentation.viewmodels.Result
import javax.inject.Inject

const val PREF_PROGRESS = "PREF_PROGRESS"

class PriceBasketFragment : Fragment() {

    private lateinit var preferences: SharedPreferences
    private var progres = 0

    private var _binding: FragmentPriceBasketBinding? = null
    private val binding: FragmentPriceBasketBinding
        get() = _binding ?: throw RuntimeException("FragmentPriceBasketBinding is null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[PriceBasketViewModel::class.java]
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
        _binding = FragmentPriceBasketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSeekBarProgress()
        updateCoefficient(binding.seekBar)
        setListeners()
        observerViewModel()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun observerViewModel() {
        viewModel.state.observe(viewLifecycleOwner) { state ->
            when (state) {
                is Error -> setEditTextError()
                is Result -> {
                    binding.textViewResult.text = state.result
                }
            }
        }
    }

    private fun setSeekBarProgress() {
        preferences = requireActivity().getPreferences(Context.MODE_PRIVATE)
        binding.seekBar.progress = preferences.getInt(PREF_PROGRESS, 45)
        progres = binding.seekBar.progress
    }

    private fun setListeners() {
        with(binding) {
            icClear.setOnClickListener {
                clearAllField()
            }

            btResult.setOnClickListener {
                viewModel.calculate(
                    cover = if (withCover.isChecked) 2 else 1,
                    heightField = etHeight.text.toString(),
                    widthField = etWidth.text.toString(),
                    lengthField = etLength.text.toString(),
                    coefficient = (progres + 100).toDouble()
                )
                hideKeyboard()
            }

            withCover.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) {
                    viewModel.calculate(
                        cover = if (withCover.isChecked) 2 else 1,
                        heightField = etHeight.text.toString(),
                        widthField = etWidth.text.toString(),
                        lengthField = etLength.text.toString(),
                        coefficient = (progres + 100).toDouble()
                    )
                    hideKeyboard()
                } else {
                    viewModel.calculate(
                        cover = if (withCover.isChecked) 2 else 1,
                        heightField = etHeight.text.toString(),
                        widthField = etWidth.text.toString(),
                        lengthField = etLength.text.toString(),
                        coefficient = (progres + 100).toDouble()
                    )
                    hideKeyboard()
                }
            }

            seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar?,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    if (seekBar != null) {
                        updateCoefficient(seekBar)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {
                    if (seekBar != null) {
                        progres = seekBar.progress
                        preferences.edit()
                            .putInt(PREF_PROGRESS, progres)
                            .apply()
                        viewModel.calculate(
                            cover = if (withCover.isChecked) 2 else 1,
                            heightField = etHeight.text.toString(),
                            widthField = etWidth.text.toString(),
                            lengthField = etLength.text.toString(),
                            coefficient = (progres + 100).toDouble()
                        )
                        hideKeyboard()
                    }
                }
            })

            icBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
        }
    }

    private fun updateCoefficient(seekBar: SeekBar) {
        val coefficient = (seekBar.progress + 100).toDouble()
        binding.coefficientTextView.text = getString(R.string.coefficient, coefficient / 100)
    }

    private fun setEditTextError() {
        with(binding) {
            if (etHeight.text.isNullOrEmpty()) etHeight.error = getString(R.string.error)
            if (etLength.text.isNullOrEmpty()) etLength.error = getString(R.string.error)
            if (etWidth.text.isNullOrEmpty()) etWidth.error = getString(R.string.error)
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.btResult.windowToken, 0)
    }

    private fun clearAllField() = with(binding) {
        etHeight.text.clear()
        etWidth.text.clear()
        etLength.text.clear()
    }

    companion object {
        fun newInstance(): PriceBasketFragment {
            return PriceBasketFragment()
        }
    }
}