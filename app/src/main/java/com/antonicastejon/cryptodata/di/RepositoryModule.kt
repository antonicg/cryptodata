package com.antonicastejon.cryptodata.di

import com.antonicastejon.cryptodata.model.CoinMarketCapApi
import com.antonicastejon.cryptodata.model.CoinMarketCapDownloader
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Antoni Castejón on 04/01/2018.
 */
@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun providesCoinMarketCapRepository(coinMarketCapApi: CoinMarketCapApi): CoinMarketCapRepository = CoinMarketCapDownloader(coinMarketCapApi)


}