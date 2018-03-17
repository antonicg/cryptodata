package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.domain.CryptoListUseCase
import com.antonicastejon.cryptodata.domain.InteractorResponse
import javax.inject.Inject

private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCase: CryptoListUseCase) : ViewModel() {

    val stateLiveData: LiveData<CryptoListState> = Transformations.map(cryptoListUseCase.getLiveData(), ::getStateFromInteractorResponse)

    private fun getStateFromInteractorResponse(interactorResponse: InteractorResponse): CryptoListState {
        val isSuccessful = interactorResponse.isSuccessful
        return if (isSuccessful) {
            DefaultState(interactorResponse.data)
        } else {
            ErrorState(interactorResponse.errorMessage ?: "", emptyList())
        }
    }

    private fun getCryptoList(page:Int) = cryptoListUseCase.getCryptoListBy(page)


    fun requestCryptoList() {
        getCryptoList(0)
    }
}