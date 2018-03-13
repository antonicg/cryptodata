package com.antonicastejon.cryptodata.model

class CoinMarketCapDownloader(private val coinMarketCapApi: CoinMarketCapApi) : CoinMarketCapRepository {

    // TODO: Remove 0 hardcoded
    override fun getCryptoList(page: Int, limit: Int) = coinMarketCapApi.getCryptoList(0, limit)

}