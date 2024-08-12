package com.example.newsbreak.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.newsbreak.ui.pages.NewsListPage
import com.example.newsbreak.ui.pages.SavedNewsListPage
import com.example.newsbreak.ui.viewmodels.NewsListPageViewModel
import com.example.newsbreak.ui.views.SearchBarView

sealed class Page(var route: String, val icon: ImageVector?, var title: String) {
    data object Home : Page("Home", Icons.Default.Home, "Home")
    data object Saved : Page("Saved", Icons.Default.Favorite, "Saved")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabPage(navController: NavHostController, viewModel: NewsListPageViewModel = hiltViewModel()) {

    val lazyNewsItems = viewModel.newsList.collectAsLazyPagingItems()
    val searchQuery = viewModel.searchQuery.collectAsState(initial = "").value
    val lazySavedNewsItems = viewModel.savedNewsList.collectAsState(initial = emptyList()).value

    Scaffold(
        topBar = {
            SearchBarView(onSearch = viewModel::setSearchQuery)
        },
        bottomBar = {
            BottomAppBar(modifier = Modifier) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) {
        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface)
                .padding(
                    PaddingValues(
                        10.dp,
                        it.calculateTopPadding(),
                        10.dp,
                        it.calculateBottomPadding()
                    )
                )
        ) {
            NavHost(navController, startDestination = Page.Home.route) {
                composable(Page.Home.route) {
                    NewsListPage(lazyNewsItems, lazySavedNewsItems, searchQuery)
                }
                composable(Page.Saved.route) {
                    SavedNewsListPage(lazySavedNewsItems)
                }
            }
        }
    }
}
