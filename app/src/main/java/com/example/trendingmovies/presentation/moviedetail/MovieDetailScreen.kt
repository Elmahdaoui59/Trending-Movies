package com.example.trendingmovies.presentation.moviedetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.presentation.common.MoviePoster


@Composable
fun MovieDetailsScreen(
    uiState: MovieDetailUiState,
    innerPadding: PaddingValues,
    onRequestMovieDetail: () -> Unit
) {

    LaunchedEffect(key1 = true) {
        onRequestMovieDetail()
    }
    Column(
        modifier = Modifier
            .padding(10.dp)
            .padding(paddingValues = innerPadding)
    ) {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.Start
        ) {
            val moviePosterUrl  by remember(uiState.posterPath) {
                mutableStateOf(BuildConfig.POSTER_BASE_URL + uiState.posterPath)
            }
            MoviePoster(url = moviePosterUrl, modifier = Modifier.size(200.dp))
            uiState.title?.let { Text(text = it) }
        }
        uiState.overview?.let { Text(text = it) }
    }
    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    }
}