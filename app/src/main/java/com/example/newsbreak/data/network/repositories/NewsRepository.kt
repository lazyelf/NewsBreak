package com.example.newsbreak.data.network.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.data.network.api.NewsApi
import com.example.newsbreak.data.network.datasources.NewsDataSource
import com.example.newsbreak.data.network.repositories.interfaces.INewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: NewsApi) : INewsRepository,
    BaseRepository() {

    private val pageSize = 12

    override fun getNewsList(search: String): Flow<PagingData<NewsItem>> =
        Pager(
            PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 10,
                enablePlaceholders = false,
            ))
    { NewsDataSource(api, search) }.flow

}