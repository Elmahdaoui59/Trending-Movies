package com.example.trendingmovies.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.trendingmovies.presentation.movies.MoviesListScreen
import com.example.trendingmovies.presentation.movies.MoviesViewModel
import com.example.trendingmovies.presentation.getScreenTitleByRoute
import com.example.trendingmovies.presentation.moviedetail.MovieDetailEvent
import com.example.trendingmovies.presentation.moviedetail.MovieDetailViewModel
import com.example.trendingmovies.presentation.moviedetail.MovieDetailsScreen
import com.example.trendingmovies.presentation.movies.MoviesEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController
) {

    val moviesViewModel = hiltViewModel<MoviesViewModel>()
    val moviesUiState by moviesViewModel.moviesUiState.collectAsStateWithLifecycle()

    val movieDetailViewModel = hiltViewModel<MovieDetailViewModel>()
    val movieDetailUiState by movieDetailViewModel.movieDetailUiState.collectAsStateWithLifecycle()

    val navBackStackEntry: NavBackStackEntry? by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val snackbarHostState = remember {
        SnackbarHostState()
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) {
                Snackbar(
                    snackbarData = it,
                    contentColor = MaterialTheme.colorScheme.error,
                    containerColor = MaterialTheme.colorScheme.errorContainer
                )
            }
        },
        topBar = {
            TopAppBar(
                title = {
                    Text(text = currentRoute?.let { getScreenTitleByRoute(it) } ?: "")
                },
                navigationIcon = {
                    if (currentRoute?.contains(Screen.MovieDetailScreen.route) == true) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "back arrow"
                            )
                        }
                    }
                }
            )

        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.MoviesScreen.route
        ) {
            composable(Screen.MoviesScreen.route) {
                MoviesListScreen(
                    snackbarHostState = snackbarHostState,
                    state = moviesUiState,
                    innerPadding = innerPadding,
                    onIemClicked = {
                        navController.navigate("${Screen.MovieDetailScreen.route}/$it")
                    },
                    onRefresh = { moviesViewModel.handleEvent(MoviesEvent.RefreshMovies) }
                )
            }
            composable(
                "${Screen.MovieDetailScreen.route}/{movieId}",
                arguments = listOf(
                    navArgument("movieId") {
                        type = NavType.IntType
                    }
                )
            ) { backStackEntry ->
                val movieId = backStackEntry.arguments?.getInt("movieId")
                movieId?.let {
                    MovieDetailsScreen(
                        snackbarHostState = snackbarHostState,
                        uiState = movieDetailUiState,
                        innerPadding = innerPadding,
                        onRequestMovieDetail = {
                            movieDetailViewModel.handleEvent(
                                MovieDetailEvent.RequestMovieDetail(
                                    movieId
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}