package example.aleks.com.herokuapp.presentation.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import example.aleks.com.herokuapp.R
import example.aleks.com.herokuapp.databinding.LayoutMovieItemBinding
import example.aleks.com.herokuapp.presentation.model.MovieItemModel
import example.aleks.com.herokuapp.presentation.model.MoviesViewModel

/**
 * Created by Aleksandar on 2.5.2018 г..
 */
class MoviesAdapter constructor(private val moviesViewModel: MoviesViewModel) : RecyclerView.Adapter<MovieViewHolder>(), Filterable {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {

        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<LayoutMovieItemBinding>(layoutInflater, R.layout.layout_movie_item, parent, false)
        return MovieViewHolder(binding)
    }

    override fun getItemCount(): Int = moviesViewModel.moviesViewState.movieItems.size

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {

        holder.bind(moviesViewModel.moviesViewState.movieItems[position])
    }

    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): Filter.FilterResults {

                val oReturn = Filter.FilterResults()
                oReturn.values = moviesViewModel.search(constraint?.toString() ?: "")
                return oReturn
            }

            override fun publishResults(constraint: CharSequence, results: Filter.FilterResults) {

                val resultMovies = results.values as List<MovieItemModel>
                moviesViewModel.moviesViewState.movieItems.clear()
                moviesViewModel.moviesViewState.movieItems.addAll(resultMovies)
                notifyDataSetChanged()
            }
        }
    }
}