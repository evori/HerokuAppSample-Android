package example.aleks.com.herokuapp

import example.aleks.com.herokuapp.domain.interactor.MovieInteractor
import example.aleks.com.herokuapp.domain.model.MovieData
import example.aleks.com.herokuapp.domain.model.MoviesData
import example.aleks.com.herokuapp.domain.repository.movie.IMovieRepository
import example.aleks.com.herokuapp.domain.repository.storage.ILocalStorageRepository
import example.aleks.com.herokuapp.utils.ISchedulersProvider
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.*

/**
 * Created by aleks on 01/05/2018.
 */
class InteractorUnitTest {

    @Mock
    lateinit var testSchedulersProvider: ISchedulersProvider

    @Mock
    lateinit var localStorageRepository: ILocalStorageRepository

    @Mock
    lateinit var moviesRepository: IMovieRepository

    @InjectMocks
    lateinit var movieInteractor: MovieInteractor

    private val testScheduler = TestScheduler()

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)
        Mockito.doReturn(testScheduler).`when`<ISchedulersProvider>(testSchedulersProvider).ioScheduler()
        Mockito.doReturn(testScheduler).`when`<ISchedulersProvider>(testSchedulersProvider).mainScheduler()
    }

    @Test
    fun loadCachedMovies_Success() {

        Mockito.doAnswer {

            val movies = MoviesData(mutableListOf(MovieData(1, "Cached", "2017", "Action", "URL")), System.currentTimeMillis())
            Single.just(movies)
        }.`when`(localStorageRepository).getMovies()

        Mockito.doAnswer {

            val movies = MoviesData(mutableListOf(MovieData(2, "Remote", "2017", "Action", "URL")), System.currentTimeMillis())
            Single.just(movies)
        }.`when`(moviesRepository).getMovies()

        movieInteractor.getMovies()
                .subscribeOn(testSchedulersProvider.ioScheduler())
                .subscribe({ success ->

                    val movie = success.movies.first()
                    Assert.assertEquals("Expected cached data", "Cached", movie.movieTitle)
                }, {error ->

                    Assert.assertNull("Unexpected error", error)
                })

        testScheduler.triggerActions()
    }

    @Test
    fun loadRemoteMovies_Success() {

        Mockito.doAnswer {

            val movies = MoviesData(mutableListOf(MovieData(1, "Cached", "2017", "Action", "URL")), 1000)
            Single.just(movies)
        }.`when`(localStorageRepository).getMovies()

        Mockito.doAnswer {

            val movies = MoviesData(mutableListOf(MovieData(2, "Remote", "2017", "Action", "URL")), System.currentTimeMillis())
            Single.just(movies)
        }.`when`(moviesRepository).getMovies()

        movieInteractor.getMovies()
                .subscribeOn(testSchedulersProvider.ioScheduler())
                .subscribe({ success ->

                    val movie = success.movies.first()
                    Assert.assertEquals("Expected cached data", "Remote", movie.movieTitle)
                }, {error ->

                    Assert.assertNull("Unexpected error", error)
                })

        testScheduler.triggerActions()
    }
}