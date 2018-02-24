package com.antonicastejon.cryptodata.model

import io.reactivex.Single

class CoinMarketCapDownloader(private val coinMarketCapApi: CoinMarketCapApi) : CoinMarketCapRepository {

    override fun getCryptoList(page: Int, limit: Int): Single<List<Crypto>> = coinMarketCapApi.getCryptoList(page * limit, limit)

}