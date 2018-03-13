package com.antonicastejon.cryptodata.model

import android.arch.lifecycle.LiveData

interface CoinMarketCapRepository {

    fun getCryptoList(page:Int, limit:Int) : LiveData<ApiResponse<List<Crypto>>>
}