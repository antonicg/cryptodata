package com.antonicastejon.cryptodata.model

import io.reactivex.Observable

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
interface CoinMarketCapRepository {

    fun getCryptoList(page:Int, limit:Int) : Observable<List<Crypto>>
}