package example.aleks.com.herokuapp.presentation.adapter

import android.support.v7.widget.RecyclerView
import example.aleks.com.herokuapp.databinding.LayoutMovieItemBinding
import example.aleks.com.herokuapp.presentation.model.MovieItemModel

/**
 * Created by Aleksandar on 2.5.2018 г..
 */

class MovieViewHolder(private val binding: LayoutMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movieItemModel: MovieItemModel) {

        binding.movieItem = movieItemModel
        binding.executePendingBindings()
    }
}