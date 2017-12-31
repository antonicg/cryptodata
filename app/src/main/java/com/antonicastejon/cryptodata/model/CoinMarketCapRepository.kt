package com.antonicastejon.cryptodata.model

import io.reactivex.Flowable

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
interface CoinMarketCapRepository {

    fun getCryptoList(page:Int, limit:Int) : Flowable<List<Crypto>>
}

data class Crypto(val name: String)