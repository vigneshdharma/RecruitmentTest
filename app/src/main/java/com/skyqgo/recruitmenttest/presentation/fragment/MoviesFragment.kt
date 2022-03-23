package com.skyqgo.recruitmenttest.presentation.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.skyqgo.recruitmenttest.data.ViewState
import com.skyqgo.recruitmenttest.data.showToast
import com.skyqgo.recruitmenttest.databinding.FragmentMoviesBinding
import com.skyqgo.recruitmenttest.domain.model.Movie
import com.skyqgo.recruitmenttest.presentation.MovieViewModel
import com.skyqgo.recruitmenttest.presentation.MoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import java.util.Locale

@OptIn(ExperimentalCoroutinesApi::class)
@AndroidEntryPoint
class MoviesFragment : Fragment(), SearchView.OnQueryTextListener {

    private val viewModel: MovieViewModel by activityViewModels()
    private lateinit var binding: FragmentMoviesBinding
    private val moviesAdapter: MoviesAdapter by lazy { MoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMoviesBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.movieList.apply {
            setHasFixedSize(true)
            adapter = moviesAdapter
        }

        viewModel.moviesLiveData.observe(viewLifecycleOwner) { viewState ->
            when (viewState) {
                is ViewState.Loading ->
                    binding.loading.visibility =
                        if (viewState.isLoading) View.VISIBLE else View.GONE

                is ViewState.RenderFailure ->
                    viewState.throwable.message?.let { toastMessage ->
                        context?.showToast(toastMessage)
                    }

                is ViewState.RenderSuccess -> {
                    (binding.movieList.adapter as MoviesAdapter).itemList =
                        viewState.output

                    viewModel.filterMovies = (binding.movieList.adapter as MoviesAdapter).itemList
                }
            }

            requireActivity().onBackPressedDispatcher.addCallback {
                activity?.finish()
            }
        }

        binding.searchMovie.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {
                filter(editable.toString())
            }
        })
    }

    private fun filter(text: String) {

        if (text.isEmpty()) {
            moviesAdapter.filterList(viewModel.filterMovies)
            return
        }
        val filteredMovies: ArrayList<Movie> = ArrayList()

        for (s in viewModel.filterMovies) {
            s.title?.let { title ->
                s.genre?.let { genre ->
                    if (title.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault())) ||
                        genre.lowercase(Locale.getDefault()).contains(text.lowercase(Locale.getDefault()))
                    ) {
                        filteredMovies.add(s)
                    }
                }
            }
        }
        moviesAdapter.filterList(filteredMovies)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        moviesAdapter.filter.filter(query)
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        moviesAdapter.filter.filter(newText)
        return false
    }


}