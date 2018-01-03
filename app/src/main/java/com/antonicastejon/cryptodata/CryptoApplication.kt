package com.antonicastejon.cryptodata

import android.app.Activity
import android.app.Application
import com.antonicastejon.cryptodata.di.DaggerApplicationComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Antoni Castejón on 03/01/2018.
 */
class CryptoApplication : Application(), HasActivityInjector {

    @Inject lateinit var dispatchingActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        DaggerApplicationComponent.create()
                .inject(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingActivityInjector
}