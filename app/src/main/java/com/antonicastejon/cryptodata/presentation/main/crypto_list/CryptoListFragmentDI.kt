package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.support.v4.app.Fragment
import dagger.Binds
import dagger.Module
import dagger.Subcomponent
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

@Subcomponent/*(modules = ...)*/
interface CryptoListFragmentSubcomponent: AndroidInjector<CryptoListFragment> {
    @Subcomponent.Builder
    abstract class Builder: AndroidInjector.Builder<CryptoListFragment>()
}

@Module(subcomponents = arrayOf(CryptoListFragmentSubcomponent::class))
abstract class CryptoListFragmentModule {
    @Binds
    @IntoMap
    @FragmentKey(CryptoListFragment::class)
    abstract fun bindCryptoListFragmentInjectorFactory(builder: CryptoListFragmentSubcomponent.Builder): AndroidInjector.Factory<out Fragment>
}