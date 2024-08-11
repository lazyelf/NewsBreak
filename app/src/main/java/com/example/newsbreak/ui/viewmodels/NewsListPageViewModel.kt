package com.example.newsbreak.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.data.network.NewsListFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class NewsListPageViewModel @Inject constructor(private val newsListFactory: NewsListFactory) :
    ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    private val searchQuery = _searchQuery.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = "",
        )

    val newsList: Flow<PagingData<NewsItem>> = searchQuery
        .flatMapLatest { query ->
            newsListFactory.invoke(query).cachedIn(viewModelScope)
        }//.stateIn(viewModelScope, SharingStarted.Lazily, snapshotFlow { newsListFactory.invoke("") })

    /*    private val _newsList: MutableStateFlow<PagingData<NewsItem>> = MutableStateFlow()
        private val newsList = _newsList.asStateFlow()
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(),
                initialValue = newsListFactory.invoke(""),
            )*/


    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        /*        newsListFactory.invoke(query).collect { new ->
                    _newsList.update { new }*/
    }

    val savedNewsList: StateFlow<List<NewsItem>> = newsListFactory.getSavedNewsItem()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

}