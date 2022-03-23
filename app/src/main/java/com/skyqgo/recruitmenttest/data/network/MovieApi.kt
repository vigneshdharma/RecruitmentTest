package com.skyqgo.recruitmenttest.data.network

import com.skyqgo.recruitmenttest.data.model.MovieListResponse
import retrofit2.Response
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface MovieApi {
    @GET("/api/movies")
    suspend fun getMovies(): Response<MovieListResponse>
}