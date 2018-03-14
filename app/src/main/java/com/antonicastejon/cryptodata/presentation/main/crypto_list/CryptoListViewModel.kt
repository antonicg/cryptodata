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

    val stateLiveData: LiveData<CryptoListState> = Transformations.switchMap(cryptoListUseCase.getLiveData(), ::getStateFromInteractorResponse)

    private fun getStateFromInteractorResponse(interactorResponse: InteractorResponse): LiveData<CryptoListState> {
        return object: LiveData<CryptoListState>() {
            override fun onActive() {
                super.onActive()
                val isSuccessful = interactorResponse.isSuccessful
                val cryptoListState = if (isSuccessful) {
                    DefaultState(1, true, interactorResponse.data)
                } else {
                    ErrorState(interactorResponse.errorMessage ?: "", 0, false, emptyList())
                }
                postValue(cryptoListState)
            }
        }
    }

    private fun getCryptoList(page:Int) = cryptoListUseCase.getCryptoListBy(page)


    fun requestCryptoList() {
        getCryptoList(1)
    }

//    override fun onCleared() {
//        cryptoListUseCase.clear(observer)
//    }

//    fun updateCryptoList() {
//        val pageNum = obtainCurrentPageNum()
//        stateLiveData.value = if (pageNum == 0)
//            LoadingState(pageNum, false, obtainCurrentData())
//        else
//            PaginatingState(pageNum, false, obtainCurrentData())
//        getCryptoList(pageNum)
//    }

//    fun resetCryptoList() {
//        val pageNum = 0
//        stateLiveData.value = LoadingState(pageNum, false, emptyList())
//        updateCryptoList()
//    }

//    private fun onCryptoListReceived(cryptoList: List<CryptoViewModel>): DefaultState {
//        val currentCryptoList = obtainCurrentData().toMutableList()
//        val currentPageNum = obtainCurrentPageNum() + 1
//        val areAllItemsLoaded = cryptoList.size < LIMIT_CRYPTO_LIST
//        currentCryptoList.addAll(cryptoList)
//        return DefaultState(currentPageNum, areAllItemsLoaded, currentCryptoList)
//    }
//
//    private fun onError(error: Throwable): ErrorState {
//        return ErrorState(error.message ?: "", 0, obtainCurrentLoadedAllItems(), obtainCurrentData())
//    }

//    private fun obtainCurrentPageNum() = stateLiveData.value?.pageNum ?: 0

//    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()

//    private fun obtainCurrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false

}