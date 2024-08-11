package com.example.newsbreak.ui.viewmodels

import android.os.Build
import androidx.lifecycle.ViewModel
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.data.network.NewsListFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class NewsItemViewModel @Inject constructor(private val newsListFactory: NewsListFactory) : ViewModel() {

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
    suspend fun deleteContextAction(newsItem: NewsItem) = newsListFactory.deleteNewsItem(newsItem)

    suspend fun saveContextAction(newsItem: NewsItem) = newsListFactory.saveNewsItem(newsItem)

}