package com.example.rotangcalculator.presentation.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.FragmentLengthSizeBinding


class LengthSizeFragment : Fragment() {
    private var _binding: FragmentLengthSizeBinding? = null
    private val binding: FragmentLengthSizeBinding
        get() = _binding ?: throw RuntimeException("FragmentLengthSizeBinding is null")

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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): LengthSizeFragment {
            return LengthSizeFragment()
        }
    }
}