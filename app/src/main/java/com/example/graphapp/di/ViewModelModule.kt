package com.example.graphapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.graphapp.data.repository.PointsRepository
import com.example.graphapp.fragment.viewModel.MainViewModel
import com.example.graphapp.fragment.viewModel.factory.MainViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideMainViewModelFactory(repository: PointsRepository): MainViewModelFactory {
        return MainViewModelFactory(repository)
    }
}