package com.example.newsbreak.data.models

import com.google.gson.annotations.SerializedName

data class NewsListApiResponse (
    val page: Int,
    @SerializedName("total_pages")
    val totalPages: Int,
    val status: String,
    val totalResults: Int,
    @SerializedName("articles")
    val newsItemList: List<NewsItem>
)