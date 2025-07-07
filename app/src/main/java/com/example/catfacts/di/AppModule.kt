package com.example.catfacts.di

import com.example.catfacts.data.remote.api.CatFactApi
import com.example.catfacts.data.repository.CatFactRepositoryImpl
import com.example.catfacts.domain.repository.CatFactRepository
import com.example.catfacts.util.DefaultDispatcherProvider
import com.example.catfacts.util.DispatcherProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl() = "https://catfact.ninja/"

    @Provides
    @Singleton
    fun provideApi(baseUrl: String): CatFactApi =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CatFactApi::class.java)

    @Provides
    fun provideDispatcherProvider(): DispatcherProvider = DefaultDispatcherProvider()

    @Provides
    @Singleton
    fun provideRepository(api: CatFactApi): CatFactRepository = CatFactRepositoryImpl(api)
}