package com.example.rotangcalculator.di.modules

import androidx.lifecycle.ViewModel
import com.example.rotangcalculator.di.annotations.ViewModelKey
import com.example.rotangcalculator.presentation.viewmodels.PriceBasketViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PriceBasketViewModel::class)
    fun bindPriceBasketViewModel(viewModel: PriceBasketViewModel): ViewModel
}