package example.aleks.com.herokuapp.domain.model

import com.google.gson.annotations.SerializedName

/**
 * Created by aleks on 01/05/2018.
 */
data class MovieData(@SerializedName("id")
                      val movieId: Long,
                      @SerializedName("title")
                      val movieTitle: String,
                      @SerializedName("year")
                      val movieYear: String,
                      @SerializedName("genre")
                      val movieGenre: String,
                      @SerializedName("poster")
                      val moviePosterUrl: String)