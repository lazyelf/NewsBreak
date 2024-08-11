package com.example.newsbreak.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsbreak.ui.viewmodels.NewsListPageViewModel
import com.example.newsbreak.ui.views.ErrorView
import com.example.newsbreak.ui.views.NewsItemView

@Composable
fun NewsListPage(viewModel: NewsListPageViewModel = hiltViewModel()) {
    val lazyNewsItems = viewModel.newsList.collectAsLazyPagingItems()

    if (lazyNewsItems.loadState.refresh is LoadState.Error) {
        ErrorView { lazyNewsItems.retry() }
    }

    LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {

        items(
            count = lazyNewsItems.itemCount,
            key = { index -> lazyNewsItems[index].hashCode() },
            itemContent = { index ->
                lazyNewsItems[index]?.let { newsItem ->
                    NewsItemView(newsItem)
                }
            }
        )
    }
}