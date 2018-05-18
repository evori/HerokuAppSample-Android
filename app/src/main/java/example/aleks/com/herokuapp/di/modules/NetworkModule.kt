package example.aleks.com.herokuapp.di.modules

import dagger.Module
import dagger.Provides
import example.aleks.com.herokuapp.BuildConfig
import example.aleks.com.herokuapp.rest.controler.MovieControler
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import javax.inject.Singleton

/**
 * Created by aleks on 01/05/2018.
 */

@Module
class NetworkModule(private var cacheFile: File) {

    private val cache: Cache? by lazy {

        var cache: Cache? = null

        try {

            cache = Cache(cacheFile, (10 * 1024 * 1024).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        cache
    }

    @Provides
    @Singleton
    internal fun providesRetrofit(): Retrofit {

        val okHttpClient = OkHttpClient.Builder()
                .addInterceptor { chain ->
                    val original = chain.request()

                    // Customize the request
                    val request = original.newBuilder()
                            .header("Content-Type", "application/json")
                            .build()

                    val response = chain.proceed(request)
                    response.cacheResponse()
                    // Customize or return the response
                    response
                }
                .cache(cache)
                .build()


        return Retrofit.Builder()
                .baseUrl(BuildConfig.BASEURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providesMovieControler(retrofit: Retrofit) = retrofit.create(MovieControler::class.java)
}