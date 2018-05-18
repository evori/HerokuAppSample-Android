package example.aleks.com.herokuapp.di.modules

import android.arch.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import example.aleks.com.herokuapp.HerokuApp
import example.aleks.com.herokuapp.domain.interactor.IMovieInteractor
import example.aleks.com.herokuapp.domain.interactor.MovieInteractor
import example.aleks.com.herokuapp.domain.repository.movie.IMovieRepository
import example.aleks.com.herokuapp.domain.repository.movie.MovieRepository
import example.aleks.com.herokuapp.domain.repository.storage.ILocalStorageRepository
import example.aleks.com.herokuapp.domain.repository.storage.LocalStorageRepository
import example.aleks.com.herokuapp.presentation.mvvm.ViewModelFactory
import example.aleks.com.herokuapp.utils.ISchedulersProvider
import example.aleks.com.herokuapp.utils.SchedulersProvider
import javax.inject.Singleton

/**
 * Created by aleks on 01/05/2018.
 */
@Module
class AppModule {

    @Provides
    @Singleton
    fun bindsSchedulersProvider(schedulersProvider: SchedulersProvider): ISchedulersProvider = schedulersProvider

    @Provides
    fun bindsMovieRepository(movieRepository: MovieRepository): IMovieRepository = movieRepository

    @Provides
    fun bindsMovieInteractor(movieInteractor: MovieInteractor): IMovieInteractor = movieInteractor

    @Provides
    fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory = factory

    @Provides
    @Singleton
    fun providesGson(): Gson = GsonBuilder().create()

    @Provides
    fun providesLocalStoragerepository(herokuApp: HerokuApp, gson: Gson): ILocalStorageRepository {

        return LocalStorageRepository(herokuApp.cacheDir, gson)
    }
}