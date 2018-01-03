package com.antonicastejon.cryptodata.di

import android.arch.lifecycle.ViewModel
import com.alexfacciorusso.daggerviewmodel.ViewModelKey
import com.antonicastejon.cryptodata.presentation.main.crypto_list.CryptoListViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created by Antoni Castejón on 03/01/2018.
 */
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CryptoListViewModel::class)
    abstract fun bindCryptoListViewModel(viewModel: CryptoListViewModel) : ViewModel
}