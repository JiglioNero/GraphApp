package com.example.graphapp.di

import com.example.graphapp.data.api.ApiService
import com.example.graphapp.data.repository.PointsRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun providePointsRepository(apiService: ApiService): PointsRepository {
        return PointsRepository(apiService)
    }
}