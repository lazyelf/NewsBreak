package com.example.newsbreak.ui.viewmodels

import android.util.Log
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.data.network.NewsListFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.time.Duration.Companion.milliseconds

@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsListPageViewModel @Inject constructor(private val newsListFactory: NewsListFactory) :
    ViewModel() {

    /*        private val _searchQuery = MutableStateFlow("")
    private val searchQuery = _searchQuery.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = "",
        )

val newsList: Flow<PagingData<NewsItem>> = searchQuery
    .flatMapLatest { query ->
        newsListFactory.invoke(query).cachedIn(viewModelScope)
    }.stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())


fun setSearchQuery(query: String) {
    _searchQuery.value = query
}*/

    private val _newsList = MutableStateFlow<PagingData<NewsItem>>(PagingData.empty())
    val newsList: StateFlow<PagingData<NewsItem>> = _newsList.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = "",
        )

    init {
        viewModelScope.launch {
            try {
                newsListFactory.invoke("").cachedIn(viewModelScope)
                    .collectLatest { pagingData ->
                        _newsList.value =  pagingData
                    }
            } catch (e: Exception) {
                Log.d("SearchError", e.toString())
            }
        }
    }

    fun setSearchQuery(query: String) {
        viewModelScope.launch {
            try {
                newsListFactory.invoke(query).cachedIn(viewModelScope)
                    .debounce(300.milliseconds)
                    .collectLatest { pagingData ->
                        _newsList.value = pagingData
                    }
                _searchQuery.value = query

            } catch (e: Exception) {
                Log.d("SearchError", e.toString())
            }
        }
    }

    val savedNewsList: StateFlow<List<NewsItem>> =  newsListFactory.getSavedNewsItem()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}