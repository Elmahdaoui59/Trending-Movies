package com.example.trendingmovies.presentation.movies

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.trendingmovies.presentation.common.UiEvent
import com.example.trendingmovies.presentation.eventFlow
import kotlinx.coroutines.flow.collectLatest


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesListScreen(
    snackbarHostState: SnackbarHostState,
    state: MoviesUiState,
    innerPadding: PaddingValues,
    onIemClicked: (Int) -> Unit,
    onRefresh: () -> Unit
) {

    val ctx = LocalContext.current
    val swipeRefreshState = rememberPullRefreshState(
        refreshing = state.isLoading,
        onRefresh = { onRefresh() })
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is UiEvent.ShowSnackBar -> {
                    snackbarHostState.showSnackbar(message = event.getMsgAsString(ctx))
                }
            }
        }
    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(10.dp)
            .pullRefresh(swipeRefreshState),
        contentAlignment = Alignment.Center
    ) {
        if (state.movies?.isNotEmpty() == true) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                items(
                    state.movies,
                    key = {
                        it.id ?: 0
                    }
                ) { movie ->
                    MovieItemLayout(
                        movie = movie,
                        modifier = Modifier.clickable {
                            movie.id?.let { onIemClicked(it) }
                        }
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }

        } else {
            // I'm using this lazy-column here for the pullRefresh modifier,
            // so it can receive scroll events and trigger refresh
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Text(text = "Nod data")
                }
            }
        }
        PullRefreshIndicator(
            refreshing = state.isLoading,
            state = swipeRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        )
    }
}