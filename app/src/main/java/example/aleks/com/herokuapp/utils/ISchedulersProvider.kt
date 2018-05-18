package example.aleks.com.herokuapp.utils

import io.reactivex.Scheduler

/**
 * Created by aleks on 01/05/2018.
 */
interface ISchedulersProvider {

    fun ioScheduler(): Scheduler
    fun mainScheduler(): Scheduler
}