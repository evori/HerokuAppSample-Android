package example.aleks.com.herokuapp.domain.interactor

import example.aleks.com.herokuapp.BuildConfig
import example.aleks.com.herokuapp.domain.model.MoviesData
import example.aleks.com.herokuapp.domain.repository.movie.IMovieRepository
import example.aleks.com.herokuapp.domain.repository.storage.ILocalStorageRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by aleks on 01/05/2018.
 */
class MovieInteractor @Inject constructor(private val movieRepository: IMovieRepository,
                                          private val localStorageRepository: ILocalStorageRepository) : IMovieInteractor {

    override fun getMovies(): Single<MoviesData> {

        return getCachedMovies()
                .flatMap {

                    val cachedForMs = System.currentTimeMillis() - it.createdAt
                    if (it.movies.isNotEmpty() && cachedForMs <= BuildConfig.CACHETIME_MS) {
                        Single.just(it)
                    } else {
                        getMoviesFromServer()
                    }
                }
    }

    private fun getMoviesFromServer(): Single<MoviesData> {

        return movieRepository.getMovies()
                .doOnSuccess {

                    localStorageRepository.addMovies(it)
                }
    }

    private fun getCachedMovies(): Single<MoviesData> = localStorageRepository.getMovies()
}