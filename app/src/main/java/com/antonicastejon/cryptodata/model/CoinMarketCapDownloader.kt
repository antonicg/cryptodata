package com.antonicastejon.cryptodata.model

class CoinMarketCapDownloader(private val coinMarketCapApi: CoinMarketCapApi) : CoinMarketCapRepository {

    override suspend fun getCryptoList(page: Int, limit: Int) = coinMarketCapApi.getCryptoList(page * limit, limit)

}