package example.aleks.com.herokuapp.presentation.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import example.aleks.com.herokuapp.BR
import javax.inject.Inject

/**
 * Created by Aleksandar on 2.5.2018 г..
 */
class MovieItemModel @Inject constructor() : BaseObservable() {

    private var _movieGenre: String = ""
    var movieGenre: String
        @Bindable
        get() = _movieGenre
        set(value) {

            if (this._movieGenre != value) {
                this._movieGenre = value
                notifyPropertyChanged(BR.movieGenre)
            }
        }

    private var _moviePoster = ""
    var moviePoster: String
        @Bindable
        get() = _moviePoster
        set(value) {

            if (this._moviePoster != value) {
                this._moviePoster = value
                notifyPropertyChanged(BR.moviePoster)
            }
        }

    private var _movieTitle = ""
    var movieTitle: String
        @Bindable
        get() = _movieTitle
        set(value) {

            if (this._movieTitle != value) {
                this._movieTitle = value
                notifyPropertyChanged(BR.movieTitle)
            }
        }
}