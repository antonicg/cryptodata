package com.antonicastejon.cryptodata.presentation.main.di

import com.antonicastejon.cryptodata.presentation.main.MainActivity
import com.antonicastejon.cryptodata.presentation.main.crypto_list.CryptoListFragmentModule
import dagger.Subcomponent
import dagger.android.AndroidInjector

@Subcomponent(modules = arrayOf(CryptoListFragmentModule::class))
interface MainActivitySubcomponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}