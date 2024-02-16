package com.example.trendingmovies.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.trendingmovies.presentation.movies.MoviesListScreen
import com.example.trendingmovies.presentation.movies.MoviesViewModel
import com.example.trendingmovies.presentation.getScreenTitleByRoute

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    moviesViewModel: MoviesViewModel
) {

    val moviesUiState by moviesViewModel.moviesUiState.collectAsStateWithLifecycle()
    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(text = currentRoute?.let { getScreenTitleByRoute(it) } ?: "")
            })
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MoviesScreen.route
        ) {
            composable(Screen.MoviesScreen.route) {
                MoviesListScreen(moviesUiState, innerPadding = innerPadding)
            }

        }

    }
}