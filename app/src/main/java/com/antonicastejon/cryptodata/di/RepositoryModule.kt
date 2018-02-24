package com.antonicastejon.cryptodata.di

import com.antonicastejon.cryptodata.model.CoinMarketCapApi
import com.antonicastejon.cryptodata.model.CoinMarketCapDownloader
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCoinMarketCapRepository(coinMarketCapApi: CoinMarketCapApi): CoinMarketCapRepository = CoinMarketCapDownloader(coinMarketCapApi)


}