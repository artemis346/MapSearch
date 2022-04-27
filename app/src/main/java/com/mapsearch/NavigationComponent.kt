package com.mapsearch

import Destinations.MAP_ROUTE
import Destinations.SEARCH_ROUTE
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mapsearch.nearby.MainMapScreen
import com.mapsearch.search.SearchResultScreen

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = MAP_ROUTE
    ) {
        composable(MAP_ROUTE) {
            MainMapScreen(navController)
        }
        composable(SEARCH_ROUTE) {
            SearchResultScreen(navController)
        }
    }
}