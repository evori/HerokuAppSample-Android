package example.aleks.com.herokuapp.rest.controler

/**
 * Created by aleks on 01/05/2018.
 */

import example.aleks.com.herokuapp.BuildConfig
import example.aleks.com.herokuapp.rest.model.MoviesModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers

/**
 * Created by aleks on 01/05/2018.
 */

interface MovieControler {

    @Headers("Cache-Control: max-age=${BuildConfig.CACHETIME_MS / 1000}")
    @GET("api/movies")
    fun getMoviels(): Single<MoviesModel>
}