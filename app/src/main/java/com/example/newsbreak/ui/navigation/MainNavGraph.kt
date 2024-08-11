package com.example.newsbreak.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.newsbreak.ui.pages.NewsListPage
import com.example.newsbreak.ui.pages.SavedNewsListPage

sealed class Page(var route: String, val icon: ImageVector?, var title: String) {
    object Home : Page("Home", Icons.Default.Home, "Home")
    object Saved : Page("Saved", Icons.Default.Favorite, "Saved")
}

@Composable
fun TabNavGraph(navController: NavHostController) {
    NavHost(navController, startDestination = Page.Home.route) {
        composable(Page.Home.route) {
            NewsListPage()
        }
        composable(Page.Saved.route) {
            SavedNewsListPage()
        }
    }
}