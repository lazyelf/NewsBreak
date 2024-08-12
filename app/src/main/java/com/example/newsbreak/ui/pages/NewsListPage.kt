package com.example.newsbreak.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsbreak.data.models.NewsItem
import com.example.newsbreak.ui.viewmodels.NewsListPageViewModel
import com.example.newsbreak.ui.views.ErrorView
import com.example.newsbreak.ui.views.NewsItemView

@Composable
fun NewsListPage(lazyNewsItems: LazyPagingItems<NewsItem>, lazySavedNewsItems: List<NewsItem>, searchQuery: String) {
    if (lazyNewsItems.loadState.refresh is LoadState.Error) {
        ErrorView { lazyNewsItems.retry() }
    }

    if (lazyNewsItems.itemCount > 0) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(count = lazyNewsItems.itemCount) { index ->
                lazyNewsItems[index]?.let { newsItem ->
                    newsItem.isSaved = lazySavedNewsItems.contains(newsItem)
                    NewsItemView(newsItem)
                }
            }
        }
    } else {
        if (searchQuery.isNotEmpty()) {
            Text("No items found")
        }
    }
}