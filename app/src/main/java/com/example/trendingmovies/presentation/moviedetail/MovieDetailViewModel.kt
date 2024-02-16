package com.example.trendingmovies.presentation.moviedetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.repositories.MoviesRepository
import com.example.trendingmovies.presentation.movies.MoviesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {


    private val _movieDetailUiState: MutableStateFlow<MovieDetailUiState> = MutableStateFlow(
        MovieDetailUiState()
    )
    val movieDetailUiState: StateFlow<MovieDetailUiState> = _movieDetailUiState.asStateFlow()

    fun handleEvent(event: MovieDetailEvent) {
        when(event) {
            is MovieDetailEvent.RequestMovieDetail -> {
                getMovieDetail(event.movieId)
            }
        }
    }

    private fun getMovieDetail(movieId: Int) {
        viewModelScope.launch {
            moviesRepository.getMovieDetail(movieId).collect { result ->
                when (result) {
                    is WebResponse.Loading -> {
                        _movieDetailUiState.update {
                            it.copy(
                                isLoading = true,
                                errorId = null
                            )
                        }
                    }

                    is WebResponse.Success -> {
                        _movieDetailUiState.update {
                            it.copy(
                                isLoading = false,
                                errorId = null,
                                title = result.data.title,
                                overview = result.data.overview,
                                posterPath = result.data.poster_path,
                                releaseDate = result.data.release_date
                            )
                        }
                    }

                    is WebResponse.Failure -> {
                        _movieDetailUiState.update {
                            it.copy(
                                isLoading = false,
                                errorId = result.messageId
                            )
                        }
                    }
                }

            }
        }
    }
}