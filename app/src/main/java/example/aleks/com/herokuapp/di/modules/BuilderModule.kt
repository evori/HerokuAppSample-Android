package example.aleks.com.herokuapp.di.modules

import dagger.Module
import dagger.android.ContributesAndroidInjector
import example.aleks.com.herokuapp.di.annotations.ActivityScope
import example.aleks.com.herokuapp.presentation.MainActivity

/**
 * Created by aleks on 01/05/2018.
 */

@Module
abstract class BuilderModule {

    @ContributesAndroidInjector(modules = [ActivityModule::class])
    @ActivityScope
    abstract fun bindsMainActivity(): MainActivity
}