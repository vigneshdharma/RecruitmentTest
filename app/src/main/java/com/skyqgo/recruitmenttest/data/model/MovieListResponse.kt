package com.skyqgo.recruitmenttest.data.model

import com.google.gson.annotations.SerializedName
import com.skyqgo.recruitmenttest.domain.model.Movie

data class MovieListResponse(
    @SerializedName("data") var movies: ArrayList<MovieResponse> = arrayListOf(),
)

data class MovieResponse(
    @SerializedName( "id") var id: Int? = null,
    @SerializedName( "title") var title: String? = null,
    @SerializedName( "year") var year: String? = null,
    @SerializedName( "genre") var genre: String? = null,
    @SerializedName( "poster") var poster: String? = null
) {

    fun toMovie(): Movie = Movie(
        this.title,
        this.year,
        this.genre,
        this.poster
    )
}

