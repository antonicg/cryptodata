package com.antonicastejon.cryptodata.domain

import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import io.reactivex.Flowable

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

private const val LITMIT_CRYPTO_LIST = 20

class CryptoListInteractor(private val coinMarketCapRepository: CoinMarketCapRepository) : CryptoListUseCases {

    override fun getCryptoListBy(page: Int): Flowable<List<CryptoViewModel>> {
        return coinMarketCapRepository.getCryptoList(page, LITMIT_CRYPTO_LIST)
                .map { cryptos -> cryptos.map { CryptoViewModel(it.name) } }
    }
}