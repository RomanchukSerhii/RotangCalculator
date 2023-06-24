package com.example.rotangcalculator.presentation

import android.app.Application
import com.example.rotangcalculator.di.components.DaggerApplicationComponent

class App : Application() {
    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}