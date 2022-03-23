package com.skyqgo.recruitmenttest.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.skyqgo.recruitmenttest.data.DataState
import com.skyqgo.recruitmenttest.data.ViewState
import com.skyqgo.recruitmenttest.data.model.MovieListResponse
import com.skyqgo.recruitmenttest.domain.GetMoviesUseCase
import com.skyqgo.recruitmenttest.domain.model.Movie
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verifyOrder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentMatchers

@ExperimentalCoroutinesApi
class MovieViewModelTest{

    @get:Rule
    var mainCoroutineRule = TestCoroutineRule()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private val fakeSuccessFlow = flow {
        emit(DataState.OnSuccess(mockMovieListResponse))
    }

    private val fakeFailureFlow = flow {
        emit(DataState.OnFailed(mockException))
    }

    @RelaxedMockK
    private lateinit var viewStateObserver: Observer<ViewState<List<Movie>>>

    @RelaxedMockK
    private lateinit var mockMovieListResponse: MovieListResponse

    @RelaxedMockK
    private lateinit var mockException: Exception

    @RelaxedMockK
    private lateinit var mockUseCase: GetMoviesUseCase

    private val viewModel: MovieViewModel by lazy {
        MovieViewModel(mockUseCase)
    }

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        every { mockException.message } returns "Test Exception"
    }

    @Test
    fun `load movies`() {

        runTest {

            coEvery { mockUseCase.execute() } returns fakeSuccessFlow

            viewModel.moviesLiveData.observeForever(viewStateObserver)
            viewModel.loadMovies()

            verifyOrder {
                viewStateObserver.onChanged(ViewState.Loading(true))
                viewStateObserver.onChanged(ViewState.RenderSuccess(ArgumentMatchers.anyList()))
                viewStateObserver.onChanged(ViewState.Loading(false))
            }
        }
    }

}