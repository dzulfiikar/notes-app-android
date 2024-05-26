package com.dzul.notestapp.di

import com.dzul.notestapp.data.sources.network.NoteAPIService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

//@Module
//@InstallIn(SingletonComponent::class)
//object NetworkModule {
//    @Provides
//    fun provideBaseUrl(): String = "https://jsonplaceholder.typicode.com"
//
//    @Provides
//    fun provideRetrofit(baseUrl: String): Retrofit = Retrofit.Builder()
//        .baseUrl(baseUrl)
//        .addConverterFactory(GsonConverterFactory.create())
//        .build()
//
//    @Singleton
//    @Provides
//    fun provideNoteAPIService(retrofit: Retrofit): NoteAPIService = retrofit.create(NoteAPIService::class.java)
//}