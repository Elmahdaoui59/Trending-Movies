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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.trendingmovies.domain.model.Movie
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun MoviesListScreen(
    snackbarHostState: SnackbarHostState,
    moviesList: LazyPagingItems<Movie>,
    innerPadding: PaddingValues,
    onIemClicked: (Int) -> Unit,
    onRefresh: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()
    val swipeRefreshState = rememberPullRefreshState(
        refreshing = (moviesList.loadState.refresh is LoadState.Loading)
                or (moviesList.loadState.append is LoadState.Loading),
        onRefresh = { onRefresh() })

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
            .padding(10.dp)
            .pullRefresh(swipeRefreshState),
        contentAlignment = Alignment.Center
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(
                moviesList.itemCount
            ) { index ->
                moviesList[index]?.let { movie ->
                    MovieItemLayout(
                        movie = moviesList[index]!!,
                        modifier = Modifier.clickable {
                            movie.id?.let { onIemClicked(it) }
                        }
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
            }
        }

        if (moviesList.itemCount == 0) {
            // I'm using this lazy-column here for the pullRefresh modifier,
            // so it can receive scroll events and trigger refresh when the list of movies is empty
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
            refreshing = (moviesList.loadState.refresh is LoadState.Loading)
                    or (moviesList.loadState.append is LoadState.Loading),
            state = swipeRefreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            backgroundColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary
        )
        // this part to show an error (if any) in a SnackBar
        LaunchedEffect(key1 = moviesList.loadState) {
            // should be in Launched effect block so that loadState is not check on every recomposition
            moviesList.apply {
                if (
                    (loadState.refresh is LoadState.Error)
                    or (loadState.append is LoadState.Error)
                ) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar(
                            message = "An error has occurred, verity internet connection"
                        )
                    }
                }
            }
        }
    }
}