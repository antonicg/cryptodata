package com.antonicastejon.cryptodata.domain

import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import com.antonicastejon.cryptodata.model.Crypto
import io.reactivex.Observable

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

private const val LITMIT_CRYPTO_LIST = 20

class CryptoListInteractor(private val coinMarketCapRepository: CoinMarketCapRepository) : CryptoListUseCases {

    override fun getCryptoListBy(page: Int): Observable<List<CryptoViewModel>> {
        return coinMarketCapRepository.getCryptoList(page, LITMIT_CRYPTO_LIST)
                .map { cryptos -> cryptos.map(cryptoViewModelMapper) }
    }

    val cryptoViewModelMapper: (Crypto) -> CryptoViewModel = {
        crypto -> CryptoViewModel(crypto.id, crypto.name, crypto.symbol, crypto.rank, crypto.priceUsd.toFloat(), crypto.priceBtc.toFloat(), crypto.percentChange24h.toFloat())
    }
}