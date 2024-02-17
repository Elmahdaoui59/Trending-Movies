package com.example.trendingmovies.presentation.moviedetail

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.presentation.common.MoviePoster
import com.example.trendingmovies.presentation.common.UiEvent
import com.example.trendingmovies.presentation.eventFlow
import kotlinx.coroutines.flow.collectLatest


@Composable
fun MovieDetailsScreen(
    snackbarHostState: SnackbarHostState,
    uiState: MovieDetailUiState,
    innerPadding: PaddingValues,
    onRequestMovieDetail: () -> Unit
) {
    val ctx = LocalContext.current
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(message = event.getMsgAsString(ctx))
                }
            }
        }
    }
    LaunchedEffect(key1 = true) {
        onRequestMovieDetail()
    }
    Card(
        modifier = Modifier
            .padding(10.dp)
            .padding(paddingValues = innerPadding)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start
        ) {
            val moviePosterUrl by remember(uiState.posterPath) {
                mutableStateOf(BuildConfig.POSTER_BASE_URL + uiState.posterPath)
            }
            MoviePoster(url = moviePosterUrl, modifier = Modifier.size(200.dp))
            uiState.title?.let { Text(
                text = it,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(10.dp),
                style = MaterialTheme.typography.titleLarge
            ) }
        }
        Spacer(modifier = Modifier.height(10.dp))
        uiState.overview?.let { Text(text = it, modifier = Modifier.padding(5.dp), style = MaterialTheme.typography.bodyLarge) }
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