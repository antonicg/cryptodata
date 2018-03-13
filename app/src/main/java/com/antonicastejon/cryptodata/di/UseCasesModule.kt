package com.antonicastejon.cryptodata.di

import com.antonicastejon.cryptodata.domain.CryptoListInteractor
import com.antonicastejon.cryptodata.domain.CryptoListUseCase
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import dagger.Module
import dagger.Provides

@Module
class UseCasesModule {

    @Provides
    fun providesCryptoListUseCases(coinMarketCapRepository: CoinMarketCapRepository): CryptoListUseCase = CryptoListInteractor(coinMarketCapRepository)
}