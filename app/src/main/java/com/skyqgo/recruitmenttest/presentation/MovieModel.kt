package com.skyqgo.recruitmenttest.presentation

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.skyqgo.recruitmenttest.R
import com.skyqgo.recruitmenttest.domain.model.Movie

class MovieModel(val movie: Movie) {
    private val movieData = MutableLiveData<Movie>()

    init {
        movieData.value = movie
    }
}

object LoadImageBindingAdapter {
    @BindingAdapter("imageBind")
    @JvmStatic
    fun loadImage(view: ImageView, imageUrl: String) {
        Glide.with(view.context).load(imageUrl).placeholder(R.drawable.ic_no_image_available).into(view)
    }
}
