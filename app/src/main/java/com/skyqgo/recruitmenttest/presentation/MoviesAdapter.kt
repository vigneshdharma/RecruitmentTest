package com.skyqgo.recruitmenttest.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.skyqgo.recruitmenttest.databinding.RowMovieBinding
import com.skyqgo.recruitmenttest.domain.model.Movie
import kotlin.properties.Delegates

class MoviesAdapter :RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>(), Filterable {

    var moviesFiltered: ArrayList<Movie> = ArrayList()

    var itemList: List<Movie> by Delegates.observable(emptyList()) { _, _, _ ->
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(RowMovieBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.bind(itemList[position])
    }

    override fun getItemCount() = itemList.size

    class MovieViewHolder(private val binding: RowMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            val movieModel = MovieModel(movie)
            binding.model = movieModel
        }
    }

    fun filterList(filteredMovies: List<Movie>) {
        this.itemList = filteredMovies
        this.itemList.distinct()
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                if (charString.isEmpty()) moviesFiltered = itemList as ArrayList<Movie> else {
                    val filteredList = ArrayList<Movie>()
                    itemList
                        .filter {
                            it.title?.let { title ->
                                it.genre?.let { genre ->
                                    (title.contains(constraint!!)) or
                                        (genre.contains(constraint))
                                }
                            } ?: false
                        }
                        .forEach { filteredList.add(it) }
                    moviesFiltered = filteredList
                }
                return FilterResults().apply { values = moviesFiltered }
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {

                moviesFiltered = if (results?.values == null) {
                    arrayListOf()
                } else {
                    results.values as ArrayList<Movie>
                }
                notifyDataSetChanged()
            }
        }
    }
}