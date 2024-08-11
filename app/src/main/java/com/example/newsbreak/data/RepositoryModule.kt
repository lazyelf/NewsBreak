package com.example.newsbreak.data

import com.example.newsbreak.data.local.repositories.SavedNewsRepository
import com.example.newsbreak.data.local.repositories.interfaces.ISavedNewsRepository
import com.example.newsbreak.data.network.repositories.NewsRepository
import com.example.newsbreak.data.network.repositories.interfaces.INewsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun providesNewsRepository(repository: NewsRepository): INewsRepository

    @Binds
    @Singleton
    abstract fun providesSavedNewsRepository(repository: SavedNewsRepository): ISavedNewsRepository

}