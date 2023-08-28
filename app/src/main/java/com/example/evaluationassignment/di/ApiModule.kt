package com.example.evaluationassignment.di

import com.apollographql.apollo3.ApolloClient
import com.example.evaluationassignment.network.LeadsApi
import com.example.evaluationassignment.network.LeadsApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @[Provides Singleton]
    fun provideLeadsApi(
        apollo: ApolloClient
    ): LeadsApi = LeadsApiImpl(apollo)

}