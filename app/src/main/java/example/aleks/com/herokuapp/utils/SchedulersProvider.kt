package example.aleks.com.herokuapp.utils

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by aleks on 01/05/2018.
 */

class SchedulersProvider @Inject constructor() : ISchedulersProvider {

    override fun ioScheduler(): Scheduler = Schedulers.io()

    override fun mainScheduler(): Scheduler = AndroidSchedulers.mainThread()
}