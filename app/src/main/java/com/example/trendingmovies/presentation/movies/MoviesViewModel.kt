package com.example.trendingmovies.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.repositories.MoviesRepository
import com.example.trendingmovies.presentation.common.UiEvent
import com.example.trendingmovies.presentation.eventFlow
import com.example.trendingmovies.presentation.movies.MoviesUiState
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

    init {
        getMovies()
    }

    private fun getMovies() {
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
                        sendUiError(result.messageId)
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

    private fun sendUiError(messageId: Int) {
        viewModelScope.launch {
            eventFlow.emit(UiEvent.ShowSnackBar(messageId))
        }
    }

}