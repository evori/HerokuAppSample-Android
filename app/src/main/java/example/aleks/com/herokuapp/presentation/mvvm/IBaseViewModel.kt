package example.aleks.com.herokuapp.presentation.mvvm

import io.reactivex.disposables.Disposable

/**
 * Created by aleks on 01/05/2018.
 */
interface IBaseViewModel {

    fun start()
    fun add(disposable: Disposable?)
    fun dispose()
}