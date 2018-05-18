package example.aleks.com.herokuapp.di.components

import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import example.aleks.com.herokuapp.HerokuApp
import example.aleks.com.herokuapp.di.modules.AppModule
import example.aleks.com.herokuapp.di.modules.BuilderModule
import example.aleks.com.herokuapp.di.modules.NetworkModule
import javax.inject.Singleton

/**
 * Created by aleks on 01/05/2018.
 */

@Singleton
@Component(modules = [AndroidSupportInjectionModule::class,
    AppModule::class,
    BuilderModule::class,
    NetworkModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(herokuApp: HerokuApp): Builder
        fun network(networkModule: NetworkModule): Builder
        fun build(): AppComponent
    }

    fun inject(herokuApp: HerokuApp)
}