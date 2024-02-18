package com.example.trendingmovies.presentation.movies

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.trendingmovies.domain.model.Movie
import com.example.trendingmovies.domain.repositories.MoviesRepository
import com.example.trendingmovies.presentation.common.UiEvent
import com.example.trendingmovies.presentation.eventFlow
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _moviesList: MutableStateFlow<PagingData<Movie>> =
        MutableStateFlow(value = PagingData.empty())
    val moviesList: StateFlow<PagingData<Movie>> = _moviesList.asStateFlow()

    init {
        getMovies()
    }

    fun handleEvent(event: MoviesEvent) {
        when (event) {
            is MoviesEvent.RefreshMovies -> {
                getMovies()
            }
        }
    }

    private fun getMovies() {
        viewModelScope.launch {
            moviesRepository
                .getMovies()
                .cachedIn(viewModelScope)
                .distinctUntilChanged()
                .collectLatest { result: PagingData<Movie> ->
                    _moviesList.update {
                        result
                    }
                }
        }
    }
}