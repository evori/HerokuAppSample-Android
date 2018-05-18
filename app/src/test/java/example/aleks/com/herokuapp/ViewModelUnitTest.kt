package example.aleks.com.herokuapp

import example.aleks.com.herokuapp.domain.interactor.IMovieInteractor
import example.aleks.com.herokuapp.domain.model.MovieData
import example.aleks.com.herokuapp.domain.model.MoviesData
import example.aleks.com.herokuapp.presentation.model.MovieItemModel
import example.aleks.com.herokuapp.presentation.model.MoviesViewModel
import example.aleks.com.herokuapp.presentation.model.MoviesViewState
import example.aleks.com.herokuapp.presentation.mvvm.IBaseViewModel
import example.aleks.com.herokuapp.utils.ISchedulersProvider
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.TestScheduler
import junit.framework.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

/**
 * Created by Aleksandar on 2.5.2018 г..
 */

class ViewModelUnitTest {

    @Mock
    lateinit var moviesViewState: MoviesViewState

    @Mock
    lateinit var baseViewModel: IBaseViewModel

    @Mock
    lateinit var testSchedulersProvider: ISchedulersProvider

    @Mock
    lateinit var movieInteractor: IMovieInteractor

    @InjectMocks
    lateinit var moviesViewModel: MoviesViewModel

    private val testScheduler = TestScheduler()

    private val movieItems = mutableListOf<MovieItemModel>()

    @Before
    fun start() {

        MockitoAnnotations.initMocks(this)
        Mockito.doReturn(testScheduler).`when`<ISchedulersProvider>(testSchedulersProvider).ioScheduler()
        Mockito.doReturn(testScheduler).`when`<ISchedulersProvider>(testSchedulersProvider).mainScheduler()
        Mockito.doAnswer {

            val movies = MoviesData(mutableListOf(MovieData(1, "Title 1", "2017", "Action", "URL"),
                    MovieData(2, "Title 2", "2017", "History", "URL")), System.currentTimeMillis())
            Single.just(movies)
        }.`when`(movieInteractor).getMovies()

        movieItems.add(MovieItemModel().apply {
            this.movieTitle = "Dunkirk"
            this.movieGenre = "History"
            this.moviePoster = "Poster 1"
        })

        movieItems.add(MovieItemModel().apply {
            this.movieTitle = "Jumanji"
            this.movieGenre = "Action"
            this.moviePoster = "Poster 2"
        })

        Mockito.doReturn(movieItems).`when`(moviesViewState).movieItems
    }

    @Test
    fun verify_notifyPropertyChanged_Loading() {

        moviesViewState.loading = true
        Mockito.verify(moviesViewState, Mockito.times(1)).notifyPropertyChanged(BR.loading)
    }

    @Test
    fun verify_notifyPropertyChanged_Update() {

        moviesViewState.update = true
        Mockito.verify(moviesViewState, Mockito.times(1)).notifyPropertyChanged(BR.update)
    }

    @Test
    fun viewModel_Verify_Dispose_Is_Added() {

        moviesViewModel.start()
        testScheduler.triggerActions()
        Mockito.verify(baseViewModel, Mockito.times(1)).add(Mockito.any(Disposable::class.java))
        Mockito.verify(baseViewModel, Mockito.times(1)).dispose()
        Mockito.verify(moviesViewModel.moviesViewState, Mockito.times(1)).notifyPropertyChanged(BR.update)
        Mockito.verify(moviesViewModel.moviesViewState, Mockito.times(2)).notifyPropertyChanged(BR.loading)
    }

    @Test
    fun viewModel_Verify_Search_Result() {

        val result = moviesViewModel.search("act")
        Assert.assertEquals("Expected 1 item in searched result", 1, result.size)
    }

    @Test
    fun viewModel_Verify_Search_Result_Empty_Query() {

        val result = moviesViewModel.search("")
        Assert.assertEquals("Expected 2 items in searched result", 2, result.size)
    }
}