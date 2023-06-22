package com.example.rotangcalculator.presentation

import android.app.Application
import com.example.rotangcalculator.di.components.ApplicationComponents
import com.example.rotangcalculator.di.components.DaggerApplicationComponents

class App : Application() {
    val component: ApplicationComponents by lazy {
        DaggerApplicationComponents.create()
    }
}