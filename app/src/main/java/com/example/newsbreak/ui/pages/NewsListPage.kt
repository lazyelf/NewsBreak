package com.example.newsbreak.ui.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsbreak.ui.viewmodels.NewsListPageViewModel
import com.example.newsbreak.ui.views.ErrorView
import com.example.newsbreak.ui.views.NewsItemView
import kotlinx.coroutines.flow.collectLatest

@Composable
fun NewsListPage(viewModel: NewsListPageViewModel) {
    val lazyNewsItems = viewModel.newsList.collectAsLazyPagingItems()
    val searchQuery = viewModel.searchQuery.collectAsState(initial = "").value

    if (lazyNewsItems.loadState.refresh is LoadState.Error) {
        ErrorView { lazyNewsItems.retry() }
    }

    if (lazyNewsItems.itemCount > 0) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(count = lazyNewsItems.itemCount) { index ->
                lazyNewsItems[index]?.let { newsItem ->
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