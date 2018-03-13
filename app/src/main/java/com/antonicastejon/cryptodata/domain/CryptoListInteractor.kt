package com.antonicastejon.cryptodata.domain

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import com.antonicastejon.cryptodata.model.ApiResponse
import com.antonicastejon.cryptodata.model.CoinMarketCapRepository
import com.antonicastejon.cryptodata.model.Crypto

const val LIMIT_CRYPTO_LIST = 1800

class CryptoListInteractor(private val coinMarketCapRepository: CoinMarketCapRepository) : CryptoListUseCase {

    val pageLiveData = MutableLiveData<Int>()
    val repositoryLiveData: LiveData<ApiResponse<List<Crypto>>>
    val interactorLiveData: LiveData<InteractorResponse>

    init {
        repositoryLiveData = Transformations.switchMap(pageLiveData, this::getCryptoList)
        interactorLiveData = Transformations.map(repositoryLiveData, this::mapApiResponse)
    }

    override fun getCryptoListBy(page: Int): LiveData<InteractorResponse> {
        pageLiveData.value = page
        return interactorLiveData
    }

    private fun getCryptoList(page:Int) = coinMarketCapRepository.getCryptoList(page, LIMIT_CRYPTO_LIST)
    private fun mapApiResponse(apiResponse: ApiResponse<List<Crypto>>) =
        if (apiResponse.isSuccessful) {
            val cryptoViewModelList = apiResponse.body?.let {
                it.filter { it.id != null && it.name != null }
                        .map(cryptoViewModelMapper)
            } ?: emptyList()
            InteractorResponse(cryptoViewModelList)
        } else {
            InteractorResponse(emptyList(), false, apiResponse.errorMessage)
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