package com.example.newsbreak.data.local.repositories

import com.example.newsbreak.data.local.database.AppDatabase
import com.example.newsbreak.data.local.repositories.interfaces.ISavedNewsRepository
import com.example.newsbreak.data.models.NewsItem
import javax.inject.Inject

class SavedNewsRepository @Inject constructor(private val db: AppDatabase) : SavedBaseRepository(), ISavedNewsRepository {

    override suspend fun add(newsItem: NewsItem) = db.newsItemDao().insert(newsItem)

    override suspend fun addAll(newsItems: NewsItem) = db.newsItemDao().insertAll(newsItems)

    override fun delete(newsItem: NewsItem) = db.newsItemDao().delete(newsItem)

    override fun getAll() = db.newsItemDao().getAll()

    override fun searchByQuery(query: String) = db.newsItemDao().findByQuery(query)
}