package example.aleks.com.herokuapp.rest.model

import com.google.gson.annotations.SerializedName

/**
 * Created by aleks on 01/05/2018.
 */

data class MoviesModel(@SerializedName("data") val movies: List<MovieModel>)