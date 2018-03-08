package com.antonicastejon.cryptodata.model

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response

class CoinMarketCapDownloader(private val coinMarketCapApi: CoinMarketCapApi) : CoinMarketCapRepository {

    override fun getCryptoList(page: Int, limit: Int): Deferred<Response<List<Crypto>>> = coinMarketCapApi.getCryptoList(page * limit, limit)

}