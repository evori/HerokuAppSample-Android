package example.aleks.com.herokuapp.domain.repository.movie

import example.aleks.com.herokuapp.domain.model.MovieData
import example.aleks.com.herokuapp.domain.model.MoviesData
import example.aleks.com.herokuapp.rest.controler.MovieControler
import io.reactivex.Single
import javax.inject.Inject

/**
 * Created by aleks on 01/05/2018.
 */

class MovieRepository @Inject constructor(private val movieControler: MovieControler) : IMovieRepository {

    override fun getMovies(): Single<MoviesData> =
            movieControler
                    .getMoviels()
                    .flatMap {

                        val moviesData = MoviesData( createdAt = System.currentTimeMillis(),
                                movies = it.movies.map { movie -> MovieData(movieId = movie.movieId,
                                movieTitle = movie.movieTitle,
                                movieGenre = movie.movieGenre,
                                movieYear = movie.movieYear,
                                moviePosterUrl = movie.moviePosterUrl) })

                        Single.just(moviesData)
                    }
}