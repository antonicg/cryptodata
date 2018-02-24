package com.antonicastejon.cryptodata.presentation.main.crypto_list

import com.antonicastejon.cryptodata.domain.CryptoViewModel

sealed class CryptoListState {
    abstract val pageNum:Int
    abstract val loadedAllItems:Boolean
    abstract val data: List<CryptoViewModel>
}
data class DefaultState(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<CryptoViewModel>) : CryptoListState()
data class LoadingState(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<CryptoViewModel>) : CryptoListState()
data class PaginatingState(override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<CryptoViewModel>) : CryptoListState()
data class ErrorState(val errorMessage: String, override val pageNum: Int, override val loadedAllItems: Boolean, override val data: List<CryptoViewModel>) : CryptoListState()