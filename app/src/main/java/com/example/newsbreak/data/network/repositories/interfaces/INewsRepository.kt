package com.example.newsbreak.data.network.repositories.interfaces

import androidx.paging.PagingData
import com.example.newsbreak.data.models.NewsItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

interface INewsRepository {
    fun getNewsList(search: String): Flow<PagingData<NewsItem>>
}