package example.aleks.com.herokuapp.domain.interactor

import example.aleks.com.herokuapp.domain.model.MoviesData
import io.reactivex.Single

/**
 * Created by aleks on 01/05/2018.
 */
interface IMovieInteractor {

    fun getMovies(): Single<MoviesData>
}