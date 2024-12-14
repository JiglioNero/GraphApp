package com.example.graphapp.di

import com.example.graphapp.MyApplication
import com.example.graphapp.fragment.MainFragment
import com.example.graphapp.fragment.viewModel.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, RepositoryModule::class, ViewModelModule::class])
interface AppComponent {
    fun inject(mainFragment: MainFragment)
}