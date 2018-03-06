package com.antonicastejon.cryptodata.presentation.main.crypto_list

import com.antonicastejon.cryptodata.domain.CryptoViewModel

sealed class CryptoListState {
    abstract val data: List<CryptoViewModel>
}
data class DefaultState(val loadedAllItems: Boolean, override val data: List<CryptoViewModel>) : CryptoListState()
data class LoadingState(val loadedAllItems: Boolean, override val data: List<CryptoViewModel>) : CryptoListState()
data class PaginatingState(val loadedAllItems: Boolean, override val data: List<CryptoViewModel>) : CryptoListState()
data class ErrorState(val errorMessage: String, override val data: List<CryptoViewModel>) : CryptoListState()
data class SearchState(override val data: List<CryptoViewModel>) : CryptoListState()