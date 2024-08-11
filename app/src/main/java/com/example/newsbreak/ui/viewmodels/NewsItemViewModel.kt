package com.example.newsbreak.ui.viewmodels

import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.data.network.NewsListFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class NewsItemViewModel @Inject constructor(private val newsListFactory: NewsListFactory) :
    ViewModel() {

    private val _isSaved = MutableStateFlow(false)
    val isSaved = _isSaved.asStateFlow()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(),
            initialValue = false,
        )

    fun initSavedStatus(newsItem: NewsItem) {
        _isSaved.value = newsItem.isSaved
    }

    fun parseTime(date: String): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDateTime
                .parse(
                    date,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
                )
                .format(DateTimeFormatter.ofPattern("HH:mm"))
                .toString()
        } else {
            "00:00"
        }
    }

    fun deleteContextAction(newsItem: NewsItem) {
        changeSavedStatus(newsItem)
        newsListFactory.deleteNewsItem(newsItem)
    }

    suspend fun saveContextAction(newsItem: NewsItem) {
        changeSavedStatus(newsItem)
        newsListFactory.saveNewsItem(newsItem)
    }

    private fun changeSavedStatus(newsItem: NewsItem) {
        newsItem.isSaved = !newsItem.isSaved
        _isSaved.value = newsItem.isSaved
    }

}