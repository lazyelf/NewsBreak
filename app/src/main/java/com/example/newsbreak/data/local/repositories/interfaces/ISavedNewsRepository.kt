package com.example.newsbreak.data.local.repositories.interfaces

import com.example.newsbreak.data.models.NewsItem

interface ISavedNewsRepository {
    suspend fun add(newsItem: NewsItem)

    suspend fun addAll(newsItems: NewsItem)

    fun delete(newsItem: NewsItem)

    fun getAll(): List<NewsItem>

    fun searchByQuery(query: String): List<NewsItem>
}