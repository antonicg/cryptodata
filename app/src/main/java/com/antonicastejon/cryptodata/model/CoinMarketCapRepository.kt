package com.antonicastejon.cryptodata.model

import io.reactivex.Single

interface CoinMarketCapRepository {

    fun getCryptoList(page:Int, limit:Int) : Single<List<Crypto>>
}