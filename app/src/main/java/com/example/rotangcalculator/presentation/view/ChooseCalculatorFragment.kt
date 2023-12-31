package com.example.rotangcalculator.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.FragmentChooseCalculatorBinding


class ChooseCalculatorFragment : Fragment() {
    private var _binding: FragmentChooseCalculatorBinding? = null
    private val binding: FragmentChooseCalculatorBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseCalculatorBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseCalculatorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.buttonPrice.setOnClickListener {
            val fragment = PriceBasketFragment.newInstance()
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        binding.buttonLengthSize.setOnClickListener {
            val fragment = LengthSizeFragment.newInstanceAddMode()
            parentFragmentManager.beginTransaction()
                .addToBackStack(null)
                .replace(R.id.fragment_container, fragment)
                .commit()
        }

        binding.openNoteListButton.setOnClickListener {
            val fragment = NoteListFragment.newInstance()
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): ChooseCalculatorFragment {
            return ChooseCalculatorFragment()
        }
    }
}