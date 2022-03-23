package com.skyqgo.recruitmenttest.presentation

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.skyqgo.recruitmenttest.data.ViewState
import com.skyqgo.recruitmenttest.data.getViewStateFlowForNetworkCall
import com.skyqgo.recruitmenttest.data.model.MovieListResponse
import com.skyqgo.recruitmenttest.domain.GetMoviesUseCase
import com.skyqgo.recruitmenttest.domain.model.Movie
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieViewModel @ViewModelInject constructor(
    private val getMoviesUseCase: GetMoviesUseCase
) : ViewModel() {

    val moviesLiveData: MutableLiveData<ViewState<List<Movie>>> by lazy {
        MutableLiveData<ViewState<List<Movie>>>()
    }
    var filterMovies = listOf<Movie>()
    private val movies = ArrayList<Movie>()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            getViewStateFlowForNetworkCall {
                getMoviesUseCase.execute()
            }.collect {
                when (it) {
                    is ViewState.Loading -> moviesLiveData.value = it
                    is ViewState.RenderFailure -> moviesLiveData.value = it
                    is ViewState.RenderSuccess<MovieListResponse> -> {
                        movies.addAll(it.output.movies.map { it.toMovie() })
                        moviesLiveData.value = ViewState.RenderSuccess(movies)
                    }
                }
            }
        }
    }
}