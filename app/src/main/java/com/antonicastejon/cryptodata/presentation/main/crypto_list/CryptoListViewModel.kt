package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.domain.CryptoListUseCase
import com.antonicastejon.cryptodata.domain.InteractorResponse
import javax.inject.Inject

private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCase: CryptoListUseCase) : ViewModel() {

    private val pageLiveData = MutableLiveData<Int>()
    private val interactorResponseLiveData = Transformations.switchMap(pageLiveData, this::getCryptoList)
    val stateLiveData = Transformations.map(interactorResponseLiveData, this::getStateFromInteractorResponse)

//    val observer: (InteractorResponse?) -> Unit = {
//        it?.let {
//            if (it.isSuccessful) {
//                onCryptoListReceived(it.data)
//            } else {
//                onError(Throwable(it.errorMessage))
//            }
//        } ?: onError(Throwable("Unexpected"))
//    }

//    init {
//        stateLiveData.value = DefaultState(0, false, emptyList())
//    }

    private fun getCryptoList(page:Int) = cryptoListUseCase.getCryptoListBy(page)

    private fun getStateFromInteractorResponse(interactorResponse: InteractorResponse): CryptoListState {
        return if (interactorResponse.isSuccessful) {
            DefaultState(1, true, interactorResponse.data)
        } else {
            ErrorState(interactorResponse.errorMessage ?: "", 0, false, emptyList())
        }
    }

    fun requestCryptoList() {
        pageLiveData.value = 1
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