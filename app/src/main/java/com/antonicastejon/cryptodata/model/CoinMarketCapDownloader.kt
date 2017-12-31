package com.antonicastejon.cryptodata.model

import io.reactivex.Flowable

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
class CoinMarketCapDownloader() : CoinMarketCapRepository {

    override fun getCryptoList(page: Int, limit: Int): Flowable<List<Crypto>> {
        // TODO implement
        return Flowable.just(listOf(Crypto("BTC"), Crypto("ETH"), Crypto("LTC")))
    }
}