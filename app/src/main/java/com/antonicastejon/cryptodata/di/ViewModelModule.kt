package com.antonicastejon.cryptodata.di

import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.presentation.main.crypto_list.CryptoListViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CryptoListViewModel::class)
    abstract fun bindCryptoListViewModel(viewModel: CryptoListViewModel) : ViewModel
}