package com.antonicastejon.cryptodata.model

import io.reactivex.Observable

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
class CoinMarketCapDownloader(private val coinMarketCapApi: CoinMarketCapApi) : CoinMarketCapRepository {

    override fun getCryptoList(page: Int, limit: Int): Observable<List<Crypto>> = coinMarketCapApi.getCryptoList(page * limit, limit)

}