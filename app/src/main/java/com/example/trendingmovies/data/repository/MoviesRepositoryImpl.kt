package com.example.trendingmovies.data.repository

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.R
import com.example.trendingmovies.data.local.MoviesDatabase
import com.example.trendingmovies.data.paging.MoviesRemoteMediator
import com.example.trendingmovies.data.remote.MoviesApi
import com.example.trendingmovies.domain.model.Movie
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.domain.model.moviedetail.MovieDetail
import com.example.trendingmovies.domain.repositories.MoviesRepository
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.io.BufferedReader
import java.io.InputStreamReader

class MoviesRepositoryImpl(
    private val api: MoviesApi,
    private val context: Context,
    private val moviesDatabase: MoviesDatabase
) : MoviesRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun getMovies(): Flow<PagingData<Movie>> {
        val pagingSourceFactory = { moviesDatabase.movieDao().getMovies() }
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            remoteMediator = MoviesRemoteMediator(
                moviesApi = api,
                moviesDatabase = moviesDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun getMovieDetail(movieId: Int): Flow<WebResponse<MovieDetail>> = flow {
        emit(WebResponse.Loading)
        if (BuildConfig.useMock) {
            val gson = Gson()
            val inputStream = context.resources.openRawResource(R.raw.moviedetailmock)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val jsonResponse = reader.readText()
            val response = gson.fromJson(jsonResponse, MovieDetail::class.java)
            emit(WebResponse.Success(response))
            //Log.e("res", "Movie Detail Response from mock: ${response.toString()}")
        } else {
            val result = api.getMovieDetail(movieId)
            emit(WebResponse.Success(result))
            //Log.e("detail", result.toString())
        }
    }.catch {
        emit(WebResponse.Failure(R.string.can_t_fetch_movie_detail))
    }


}