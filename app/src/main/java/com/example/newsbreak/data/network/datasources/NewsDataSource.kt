package com.example.newsbreak.data.network.datasources

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.data.network.FIRST_PAGE
import com.example.newsbreak.data.network.api.NewsApi
import java.io.IOException

class NewsDataSource(private val api: NewsApi, private val search: String) :
    PagingSource<Int, NewsItem>() {

    override fun getRefreshKey(state: PagingState<Int, NewsItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NewsItem> {
        val page = params.key ?: FIRST_PAGE
        return try {
            val data =
                if (search.isEmpty()) api.getNewsList(page)
                else api.getSearchResults(page, search)
            LoadResult.Page(
                data = data.newsItemList,
                prevKey = if (page == FIRST_PAGE) null else page - 1,
                nextKey = page + 1
            )
        } catch (t: Throwable) {
            var exception = t
            if (t is IOException) {
                exception = IOException("Check internet connection and try again")
            }
            LoadResult.Error(exception)
        }
    }
}