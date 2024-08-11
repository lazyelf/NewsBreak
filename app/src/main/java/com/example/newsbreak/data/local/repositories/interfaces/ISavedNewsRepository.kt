package com.example.newsbreak.data.local.repositories.interfaces

import com.example.newsbreak.data.models.NewsItem
import kotlinx.coroutines.flow.Flow

interface ISavedNewsRepository {
    suspend fun add(newsItem: NewsItem)

    suspend fun addAll(newsItems: NewsItem)

    fun delete(newsItem: NewsItem)

    fun getAll(): Flow<List<NewsItem>>

    fun searchByQuery(query: String): Flow<List<NewsItem>>
}