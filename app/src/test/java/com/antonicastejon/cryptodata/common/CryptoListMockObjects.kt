package com.antonicastejon.cryptodata.common

import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.domain.LIMIT_CRYPTO_LIST
import com.antonicastejon.cryptodata.model.Crypto
import java.util.*
import kotlin.collections.ArrayList

fun oneSizeArrayEmptyCryptoViewModel(): List<CryptoViewModel> =
    ArrayList<CryptoViewModel>(Collections.nCopies(1, CryptoViewModel()))

fun limitCryptoListSizeArrayEmptyCryptoViewModel(): List<CryptoViewModel> =
    ArrayList<CryptoViewModel>(Collections.nCopies(LIMIT_CRYPTO_LIST, CryptoViewModel()))

fun cryptoPOJOmodel() =
        Crypto("1", "name", "sy", 10, "100", "0.1", "100", "1000", "0", "500", "5", "10", "-10", "")

fun cryptoViewModelFrom(crypto:Crypto) =
        CryptoViewModel(crypto.id, crypto.name, crypto.symbol, crypto.rank, crypto.priceUsd.toFloat(), crypto.priceBtc.toFloat(), crypto.percentChange24h.toFloat())