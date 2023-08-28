package com.example.evaluationassignment.di

import com.example.evaluationassignment.repository.MainRepository
import com.example.evaluationassignment.repository.MainRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun getMainRepository(impl: MainRepositoryImpl): MainRepository
}