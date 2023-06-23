package com.example.rotangcalculator.di.components

import com.example.rotangcalculator.di.annotations.ApplicationScope
import com.example.rotangcalculator.di.modules.ViewModelModule
import com.example.rotangcalculator.presentation.view.LengthSizeFragment
import com.example.rotangcalculator.presentation.view.PriceBasketFragment
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class])
interface ApplicationComponents {

    fun inject(fragment: PriceBasketFragment)

    fun inject(fragment: LengthSizeFragment)
}