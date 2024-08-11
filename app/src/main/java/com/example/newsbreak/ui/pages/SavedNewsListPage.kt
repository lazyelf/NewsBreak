package com.example.newsbreak.ui.pages;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newsbreak.ui.viewmodels.NewsListPageViewModel
import com.example.newsbreak.ui.views.NewsItemView

@Composable
fun SavedNewsListPage(viewModel: NewsListPageViewModel) {

    val lazyNewsItems = viewModel.savedNewsList.collectAsState(initial = emptyList()).value

    if (lazyNewsItems.isNotEmpty()) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            items(
                count = lazyNewsItems.size,
                key = { index -> lazyNewsItems[index].hashCode() },
                itemContent = { index ->
                    NewsItemView(lazyNewsItems[index])
                }
            )
        }
    } else {
            Text("No items found")
    }
}