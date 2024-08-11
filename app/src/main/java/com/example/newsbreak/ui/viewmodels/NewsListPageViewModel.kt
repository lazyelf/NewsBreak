package com.example.newsbreak.ui.viewmodels

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.data.network.NewsListFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsListPageViewModel @Inject constructor(private val newsListFactory: NewsListFactory) :
    ViewModel() {

    private val _newsList = MutableStateFlow<PagingData<NewsItem>>(PagingData.empty())
    val newsList: StateFlow<PagingData<NewsItem>> = _newsList.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = PagingData.empty(),
        )

    init {
        viewModelScope.launch {
            newsListFactory.invoke("").cachedIn(viewModelScope)
                .collectLatest { pagingData ->
                    _newsList.value = pagingData
                    println(pagingData)
                }
        }
    }

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            try {
                newsListFactory.invoke(query).cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _newsList.update { pagingData }
                    }
            } catch (e: Exception) {
                Log.d("SearchError", e.toString())

            }
        }
    }

    val savedNewsList: StateFlow<List<NewsItem>> = newsListFactory.getSavedNewsItem()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}