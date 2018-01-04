package com.antonicastejon.cryptodata.di

import com.antonicastejon.cryptodata.domain.CryptoListInteractor
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import dagger.Module
import dagger.Provides

/**
 * Created by Antoni Castejón on 04/01/2018.
 */
@Module
class UseCasesModule {

    @Provides
    fun providesCryptoListUseCases(coinMarketCapRepository: CoinMarketCapRepository): CryptoListUseCases = CryptoListInteractor(coinMarketCapRepository)
}