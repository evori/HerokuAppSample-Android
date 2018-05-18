package example.aleks.com.herokuapp.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by aleks on 01/05/2018.
 */
data class MoviesData(@SerializedName("movies")
                      val movies: List<MovieData>,
                      @SerializedName("createdAt")
                      val createdAt: Long) {

    companion object EMPTY {

        fun empty() = MoviesData(emptyList(), System.currentTimeMillis())
    }
}