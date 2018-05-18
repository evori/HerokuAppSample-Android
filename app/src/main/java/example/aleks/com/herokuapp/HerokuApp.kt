package example.aleks.com.herokuapp

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import example.aleks.com.herokuapp.di.components.DaggerAppComponent
import example.aleks.com.herokuapp.di.modules.NetworkModule
import java.io.File
import javax.inject.Inject

/**
 * Created by aleks on 01/05/2018.
 */
class HerokuApp : Application(), HasActivityInjector {

    //region properties
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>
    //endregion

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder()
                .application(this)
                .network(NetworkModule(File(cacheDir, "responses")))
                .build()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector
}