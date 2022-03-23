package com.skyqgo.recruitmenttest.data

import com.skyqgo.recruitmenttest.data.model.MovieListResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow


interface RemoteDataSource {

    val webService: WebService

    @ExperimentalCoroutinesApi
    suspend fun getMovies(): Flow<DataState<MovieListResponse>>
}