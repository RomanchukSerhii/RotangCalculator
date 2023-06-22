package com.example.rotangcalculator.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rotangcalculator.R
import com.example.rotangcalculator.databinding.FragmentPriceBasketBinding

class PriceBasketFragment : Fragment() {
    private var _binding: FragmentPriceBasketBinding? = null
    private val binding: FragmentPriceBasketBinding
        get() = _binding ?: throw RuntimeException("FragmentPriceBasketBinding is null")

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
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    companion object {
        fun newInstance(): PriceBasketFragment {
            return PriceBasketFragment()
        }
    }
}