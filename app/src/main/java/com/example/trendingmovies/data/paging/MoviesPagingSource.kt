package com.example.trendingmovies.data.paging

import android.content.Context
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.trendingmovies.BuildConfig
import com.example.trendingmovies.R
import com.example.trendingmovies.data.remote.MoviesApi
import com.example.trendingmovies.domain.model.Movie
import com.example.trendingmovies.domain.model.MoviesResponse
import com.example.trendingmovies.domain.model.WebResponse
import com.example.trendingmovies.util.Constants.STARTING_PAGE_NUMBER
import com.google.gson.Gson
import java.io.BufferedReader
import java.io.InputStreamReader

class MoviesPagingSource(
    private val moviesApi: MoviesApi,
    private val context: Context
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: STARTING_PAGE_NUMBER
            val response = if (BuildConfig.useMock) {
                val gson = Gson()
                val inputStream = context.resources.openRawResource(R.raw.moviesrsmock)
                val reader = BufferedReader(InputStreamReader(inputStream))
                val jsonResponse = reader.readLine()
                gson.fromJson(jsonResponse, MoviesResponse::class.java)
            } else {
                moviesApi.getMovies(page = pageNumber)
            }
            val movies = response.movies
            val prevKey = if (pageNumber > STARTING_PAGE_NUMBER) pageNumber - 1 else null
            val nextKey = if (pageNumber < response.total_pages) pageNumber + 1 else null
            LoadResult.Page(
                data = movies,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

}