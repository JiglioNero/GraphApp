package com.example.graphapp

import android.app.Application
import com.example.graphapp.di.AppComponent
import com.example.graphapp.di.AppModule
import com.example.graphapp.di.DaggerAppComponent
import com.example.graphapp.di.RepositoryModule

class MyApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule())
            .repositoryModule(RepositoryModule())
            .build()

    }
}