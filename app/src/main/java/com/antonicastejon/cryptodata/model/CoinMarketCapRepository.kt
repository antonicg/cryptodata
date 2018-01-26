package com.antonicastejon.cryptodata.model

import io.reactivex.Single

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
interface CoinMarketCapRepository {

    fun getCryptoList(page:Int, limit:Int) : Single<List<Crypto>>
}