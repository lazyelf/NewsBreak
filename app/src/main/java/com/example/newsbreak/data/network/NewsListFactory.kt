package com.example.newsbreak.data.network

import androidx.paging.PagingData
import com.example.newsbreak.data.local.repositories.interfaces.ISavedNewsRepository
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.data.network.repositories.interfaces.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsListFactory @Inject constructor(
    private val newsRepository: INewsRepository,
    private val savedNewsRepository: ISavedNewsRepository
) {
    fun invoke(search: String): Flow<PagingData<NewsItem>> {
        return newsRepository.getNewsList(search)
    }

    suspend fun saveNewsItem(newsItem: NewsItem) {
        return savedNewsRepository.add(newsItem)
    }

    fun deleteNewsItem(newsItem: NewsItem) {
        return savedNewsRepository.delete(newsItem)
    }

    fun getSavedNewsItem(): Flow<List<NewsItem>> {
        return savedNewsRepository.getAll()
    }

}