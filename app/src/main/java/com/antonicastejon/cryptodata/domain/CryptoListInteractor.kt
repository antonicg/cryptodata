package com.antonicastejon.cryptodata.domain

import android.arch.lifecycle.MutableLiveData
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import com.antonicastejon.cryptodata.model.Crypto
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async

const val LIMIT_CRYPTO_LIST = 1800

class CryptoListInteractor(private val coinMarketCapRepository: CoinMarketCapRepository) : CryptoListUseCases {


    private val liveData = MutableLiveData<InteractorResponse>()
    private val crashLogger = { throwable : Throwable -> throwable.printStackTrace() }

    override fun getSubscriber() = liveData

    override fun getCryptoListBy(page: Int) {
        async(UI) {
            try {
                val response = coinMarketCapRepository.getCryptoList(page, LIMIT_CRYPTO_LIST).await()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    val cryptoList = responseBody?.let {
                        it.filter { it.id != null && it.name != null }
                                .map(cryptoViewModelMapper)
                    } ?: emptyList()
                    liveData.value = InteractorResponse(cryptoList, true)
                } else {
                    liveData.value = InteractorResponse(emptyList(), false, response.errorBody()?.string())
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }

    private val cryptoViewModelMapper: (Crypto) -> CryptoViewModel = {
        crypto -> CryptoViewModel(
            crypto.id ?: "",
            crypto.name ?: "",
            crypto.symbol ?: "",
            crypto.rank,
            crypto.priceUsd?.toFloat() ?: 0f,
            crypto.priceBtc?.toFloat() ?: 0f,
            crypto.percentChange24h?.toFloat() ?: 0f)
    }
}

data class InteractorResponse(val data: List<CryptoViewModel>, val isSuccessful:Boolean, val errorMessage: String? = null)