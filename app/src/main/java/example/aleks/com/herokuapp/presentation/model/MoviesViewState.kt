package example.aleks.com.herokuapp.presentation.model

import android.databinding.BaseObservable
import android.databinding.Bindable
import example.aleks.com.herokuapp.BR
import javax.inject.Inject

/**
 * Created by Aleksandar on 2.5.2018 г..
 */

open class MoviesViewState @Inject constructor() : BaseObservable() {

    open val movieItems = mutableListOf<MovieItemModel>()

    var query: String = ""

    private var _loading = false
    var loading: Boolean
        @Bindable
        get() = _loading
        set(value) {

            if (this._loading != value) {
                this._loading = value
                notifyPropertyChanged(BR.loading)
            }
        }

    private var _update = false
    var update: Boolean
        @Bindable
        get() = _update
        set(value) {

            if (this._update != value) {
                this._update = value
                notifyPropertyChanged(BR.update)
            }
        }
}