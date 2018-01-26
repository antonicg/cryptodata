package com.antonicastejon.cryptodata.model

import io.reactivex.Single

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
class CoinMarketCapDownloader(private val coinMarketCapApi: CoinMarketCapApi) : CoinMarketCapRepository {

    override fun getCryptoList(page: Int, limit: Int): Single<List<Crypto>> = coinMarketCapApi.getCryptoList(page * limit, limit)

}