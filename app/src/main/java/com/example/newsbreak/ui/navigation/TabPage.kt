package com.example.newsbreak.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.newsbreak.ui.viewmodels.NewsListPageViewModel
import com.example.newsbreak.ui.views.SearchBarView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TabPage(navController: NavHostController, viewModel: NewsListPageViewModel = hiltViewModel()) {
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
            TabNavGraph(navController = navController)
        }
    }
}
