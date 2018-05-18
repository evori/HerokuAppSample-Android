package example.aleks.com.herokuapp.presentation.mvvm

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

/**
 * Created by aleks on 01/05/2018.
 */

class BaseViewModel @Inject constructor() : IBaseViewModel {

    private val compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun add(disposable: Disposable?){

        disposable?.let {

            compositeDisposable.add(it)
        }
    }

    override fun dispose() = compositeDisposable.clear()

    override fun start() {
    }
}