package example.aleks.com.herokuapp.domain.repository.storage

import example.aleks.com.herokuapp.domain.model.MoviesData
import io.reactivex.Single

/**
 * Created by aleks on 01/05/2018.
 */

interface ILocalStorageRepository {


    fun addMovies(movies: MoviesData)
    fun getMovies(): Single<MoviesData>
    fun deleteMovies()
}