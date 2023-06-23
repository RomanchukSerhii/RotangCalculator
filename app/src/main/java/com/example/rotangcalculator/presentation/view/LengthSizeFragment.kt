package com.example.rotangcalculator.presentation.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.FragmentLengthSizeBinding
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

    override fun onAttach(context: Context) {
        component.inject(this)
        super.onAttach(context)
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
        observeViewModel()
        setClickListeners()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
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
            icBack.setOnClickListener {
                requireActivity().supportFragmentManager.popBackStack()
            }
            icClear.setOnClickListener {
                clearAllField()
            }
        }
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

    companion object {
        fun newInstance(): LengthSizeFragment {
            return LengthSizeFragment()
        }
    }
}