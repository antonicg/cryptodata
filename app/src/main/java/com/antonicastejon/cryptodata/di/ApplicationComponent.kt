package com.antonicastejon.cryptodata.di

import com.alexfacciorusso.daggerviewmodel.DaggerViewModelInjectionModule
import com.antonicastejon.cryptodata.CryptoApplication
import com.antonicastejon.cryptodata.presentation.main.di.MainActivityModule
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by Antoni Castejón on 03/01/2018.
 */

@Singleton
@Component(modules = arrayOf(
        AndroidSupportInjectionModule::class,
        DaggerViewModelInjectionModule::class,
        ViewModelModule::class,
        MainActivityModule::class
        ))
interface ApplicationComponent {
    fun inject(app: CryptoApplication)
}