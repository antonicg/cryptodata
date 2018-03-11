package com.antonicastejon.cryptodata.model

import kotlinx.coroutines.experimental.Deferred
import retrofit2.Response

interface CoinMarketCapRepository {

    suspend fun getCryptoList(page:Int, limit:Int) : Deferred<Response<List<Crypto>>>
}