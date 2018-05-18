package example.aleks.com.herokuapp.rest.model

import com.google.gson.annotations.SerializedName

data class MovieModel(@SerializedName("id")
                      val movieId: Long,
                      @SerializedName("title")
                      val movieTitle: String,
                      @SerializedName("year")
                      val movieYear: String,
                      @SerializedName("genre")
                      val movieGenre: String,
                      @SerializedName("poster")
                      val moviePosterUrl: String)