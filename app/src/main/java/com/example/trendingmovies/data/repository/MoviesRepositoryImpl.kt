package com.example.trendingmovies.data.repository

import android.util.Log
import com.example.trendingmovies.R
import com.example.trendingmovies.data.remote.MoviesApi
import com.example.trendingmovies.domain.model.MoviesResponse
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.repositories.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.lang.Exception
import javax.inject.Inject

class MoviesRepositoryImpl(
    private val api: MoviesApi
) : MoviesRepository {

    override fun getMovies(): Flow<WebResponse<MoviesResponse>> = flow {
        emit(WebResponse.Loading)
        try {
            val response = api.getMovies()
            Log.e("res", response.toString())
            emit(WebResponse.Success(response))
        } catch (e: Exception) {
            e.printStackTrace()
            // todo Emissions from 'catch' blocks are prohibited in order to avoid unspecified behaviour, 'Flow.catch' operator can be used instead.
            //     For a more detailed explanation, please refer to Flow documentation.
            emit(WebResponse.Failure(R.string.can_t_fetch_movies))
        }
    }


}