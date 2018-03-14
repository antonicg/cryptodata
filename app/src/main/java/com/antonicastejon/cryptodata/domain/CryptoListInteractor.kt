package com.antonicastejon.cryptodata.domain

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.antonicastejon.cryptodata.model.ApiResponse
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import com.antonicastejon.cryptodata.model.Crypto

const val LIMIT_CRYPTO_LIST = 1800

class CryptoListInteractor(private val coinMarketCapRepository: CoinMarketCapRepository) : CryptoListUseCase {

    private val pageLiveData = MutableLiveData<Int>()
    private val repositoryLiveData: LiveData<ApiResponse<List<Crypto>>> = Transformations.switchMap(pageLiveData, ::getCryptoList)
    private val interactorLiveData: LiveData<InteractorResponse> = Transformations.switchMap(repositoryLiveData, ::switchMapInteractorResponse)

    private fun switchMapInteractorResponse(response: ApiResponse<List<Crypto>>): LiveData<InteractorResponse> {
        return object: LiveData<InteractorResponse>() {
            override fun onActive() {
                super.onActive()
                val interactorResponse = mapApiResponse(response)
                postValue(interactorResponse)
            }
        }
    }

    private fun getCryptoList(page:Int): LiveData<ApiResponse<List<Crypto>>> {
        return coinMarketCapRepository.getCryptoList(page, LIMIT_CRYPTO_LIST)
    }
    override fun getCryptoListBy(page: Int) {
        pageLiveData.value = page
    }

    override fun getLiveData() = interactorLiveData

    private fun mapApiResponse(apiResponse: ApiResponse<List<Crypto>>): InteractorResponse {
        return if (apiResponse.isSuccessful) {
            val cryptoViewModelList = apiResponse.body?.let {
                it.filter { it.id != null && it.name != null }
                        .map(cryptoViewModelMapper)
            } ?: emptyList()
            InteractorResponse(cryptoViewModelList)
        } else {
            InteractorResponse(emptyList(), false, apiResponse.errorMessage)
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

data class InteractorResponse(val data: List<CryptoViewModel>, val isSuccessful:Boolean = true, val errorMessage: String? = null)