package com.example.newsbreak.data.network.api

import com.example.newsbreak.data.models.NewsListApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    suspend fun getNewsList(
        @Query("page") page: Int? = null,
        @Query("country") country: String = "ua"
    ): NewsListApiResponse

    @GET("everything")
    suspend fun getSearchResults(
        @Query("page") page: Int? = null,
        @Query("q") query: String = "default"
    ): NewsListApiResponse
}
