package com.example.rotangcalculator.di.components

import android.app.Application
import com.example.rotangcalculator.di.annotations.ApplicationScope
import com.example.rotangcalculator.di.modules.DataModule
import com.example.rotangcalculator.di.modules.ViewModelModule
import com.example.rotangcalculator.presentation.view.LengthSizeFragment
import com.example.rotangcalculator.presentation.view.NoteListFragment
import com.example.rotangcalculator.presentation.view.PriceBasketFragment
import dagger.BindsInstance
import dagger.Component

@ApplicationScope
@Component(modules = [ViewModelModule::class, DataModule::class ])
interface ApplicationComponent {

    fun inject(fragment: PriceBasketFragment)

    fun inject(fragment: LengthSizeFragment)

    fun inject(fragment: NoteListFragment)

    @Component.Factory
    interface ApplicationComponentFactory {

        fun create(
            @BindsInstance application: Application
        ) : ApplicationComponent
    }
}