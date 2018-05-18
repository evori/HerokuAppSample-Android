package example.aleks.com.herokuapp.di.modules

import android.arch.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import example.aleks.com.herokuapp.di.annotations.ViewModelKey
import example.aleks.com.herokuapp.presentation.model.MoviesViewModel
import example.aleks.com.herokuapp.presentation.mvvm.BaseViewModel
import example.aleks.com.herokuapp.presentation.mvvm.IBaseViewModel

/**
 * Created by aleks on 01/05/2018.
 */
@Module
abstract class ActivityModule {

    @Binds
    @IntoMap
    @ViewModelKey(MoviesViewModel::class)
    abstract fun bindsViewModel(mainViewModel: MoviesViewModel): ViewModel

    @Binds
    abstract fun bindsBaseViewModel(baseViewModel: BaseViewModel): IBaseViewModel
}