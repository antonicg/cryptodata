package com.antonicastejon.cryptodata.presentation.main.crypto_list

import com.antonicastejon.cryptodata.domain.CryptoViewModel

sealed class CryptoListState {
    abstract val data: List<CryptoViewModel>
}
data class DefaultState(override val data: List<CryptoViewModel>) : CryptoListState()
data class ErrorState(val errorMessage: String, override val data: List<CryptoViewModel>) : CryptoListState()