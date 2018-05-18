package example.aleks.com.herokuapp.presentation.model

import android.arch.lifecycle.ViewModel
import example.aleks.com.herokuapp.domain.interactor.IMovieInteractor
import example.aleks.com.herokuapp.presentation.mvvm.IBaseViewModel
import example.aleks.com.herokuapp.utils.ISchedulersProvider
import javax.inject.Inject

/**
 * Created by aleks on 01/05/2018.
 */

class MoviesViewModel @Inject constructor(val moviesViewState: MoviesViewState,
                                          private val interactor: IMovieInteractor,
                                          private val schedulersProvider: ISchedulersProvider,
                                          private val baseViewModel: IBaseViewModel) : ViewModel(), IBaseViewModel by baseViewModel {

    private val tempMovies = mutableListOf<MovieItemModel>()

    override fun start() {

        dispose()

        tempMovies.clear()
        baseViewModel.start()
        moviesViewState.loading = true

        val disposable = interactor.getMovies()
                .map {
                    it.movies.map { mov ->
                        MovieItemModel().apply {

                            this.movieGenre = mov.movieGenre
                            this.moviePoster = mov.moviePosterUrl
                            this.movieTitle = mov.movieTitle
                        }
                    }
                }
                .subscribeOn(schedulersProvider.ioScheduler())
                .observeOn(schedulersProvider.mainScheduler())
                .subscribe({ movies ->

                    moviesViewState.loading = false
                    moviesViewState.movieItems.clear()
                    moviesViewState.movieItems.addAll(movies)
                    moviesViewState.update = true
                }, { error ->

                    moviesViewState.loading = false
                })

        add(disposable)
    }

    fun search(query: String): List<MovieItemModel> {

        val results = mutableListOf<MovieItemModel>()
        moviesViewState.query = query
        if (tempMovies.isEmpty()) {

            tempMovies.addAll(moviesViewState.movieItems)
        }

        if (tempMovies.isNotEmpty()) {

            tempMovies.forEach { movie ->

                if (movie.movieGenre.contains(other = query, ignoreCase = true)
                        || movie.movieTitle.contains(other = query, ignoreCase = true)) {
                    results.add(movie)
                }
            }
        }

        return results
    }
}