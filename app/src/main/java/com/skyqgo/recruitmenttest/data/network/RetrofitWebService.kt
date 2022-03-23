package com.skyqgo.recruitmenttest.data.network

import com.skyqgo.recruitmenttest.data.DataState
import com.skyqgo.recruitmenttest.data.WebService
import com.skyqgo.recruitmenttest.data.model.MovieListResponse
import com.skyqgo.recruitmenttest.data.performSafeNetworkApiCall
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitWebService @Inject constructor(private val retrofitClient: MovieApi) :
    WebService {

    @ExperimentalCoroutinesApi
    override suspend fun getMovies(): Flow<DataState<MovieListResponse>> =
        performSafeNetworkApiCall("Error Obtaining movies") {
            retrofitClient.getMovies()
        }
}