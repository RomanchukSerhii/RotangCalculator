package com.example.rotangcalculator.di.modules

import android.app.Application
import com.example.rotangcalculator.data.NoteListRepositoryImpl
import com.example.rotangcalculator.data.database.AppDatabase
import com.example.rotangcalculator.data.database.NoteListDao
import com.example.rotangcalculator.di.annotations.ApplicationScope
import com.example.rotangcalculator.domain.repositories.NoteListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @ApplicationScope
    @Binds
    fun bindNoteListRepository(impl: NoteListRepositoryImpl): NoteListRepository

    companion object {
        @ApplicationScope
        @Provides
        fun provideNoteListDao(application: Application): NoteListDao {
            return AppDatabase.getInstance(application).noteListDao()
        }
    }
}