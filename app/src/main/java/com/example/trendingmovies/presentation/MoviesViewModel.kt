package com.example.trendingmovies.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.repositories.MoviesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _moviesUiState: MutableStateFlow<MoviesUiState> = MutableStateFlow(MoviesUiState())
    val moviesUiState: StateFlow<MoviesUiState> = _moviesUiState.asStateFlow()

    fun getMovies() {
        viewModelScope.launch {
            moviesRepository.getMovies().collect { result ->
                when (result) {
                    is WebResponse.Loading -> {
                        _moviesUiState.update {
                            it.copy(
                                isLoading = true,
                                errorId = null
                            )
                        }
                    }

                    is WebResponse.Success -> {
                        _moviesUiState.update {
                            it.copy(
                                isLoading = false,
                                errorId = null,
                                movies = result.data.movies
                            )
                        }
                    }

                    is WebResponse.Failure -> {
                        _moviesUiState.update {
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