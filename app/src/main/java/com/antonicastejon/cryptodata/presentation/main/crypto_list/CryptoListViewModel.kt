package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.domain.LIMIT_CRYPTO_LIST
import javax.inject.Inject

private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCases: CryptoListUseCases) : ViewModel() {

    val stateLiveData =  MutableLiveData<CryptoListState>()

    init {
        stateLiveData.value = DefaultState(0, false, emptyList())
        cryptoListUseCases.observe {
            it?.let {
                if (it.isSuccessful) {
                    onCryptoListReceived(it.data)
                } else {
                    onError(Throwable(it.errorMessage))
                }
            }
        }
    }

    override fun onCleared() {
        cryptoListUseCases.clear()
    }

    fun updateCryptoList() {
        val pageNum = obtainCurrentPageNum()
        stateLiveData.value = if (pageNum == 0)
            LoadingState(pageNum, false, obtainCurrentData())
        else
            PaginatingState(pageNum, false, obtainCurrentData())
        getCryptoList(pageNum)
    }

    fun resetCryptoList() {
        val pageNum = 0
        stateLiveData.value = LoadingState(pageNum, false, emptyList())
        updateCryptoList()
    }

    private fun getCryptoList(page:Int) {
        cryptoListUseCases.getCryptoListBy(page)
    }

    private fun onCryptoListReceived(cryptoList: List<CryptoViewModel>) {
        val currentCryptoList = obtainCurrentData().toMutableList()
        val currentPageNum = obtainCurrentPageNum() + 1
        val areAllItemsLoaded = cryptoList.size < LIMIT_CRYPTO_LIST
        currentCryptoList.addAll(cryptoList)
        stateLiveData.value = DefaultState(currentPageNum, areAllItemsLoaded, currentCryptoList)
    }

    private fun onError(error: Throwable) {
        val pageNum = stateLiveData.value?.pageNum ?: 0
        stateLiveData.value = ErrorState(error.message ?: "", pageNum, obtainCurrentLoadedAllItems(), obtainCurrentData())
    }

    private fun obtainCurrentPageNum() = stateLiveData.value?.pageNum ?: 0

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()

    private fun obtainCurrentLoadedAllItems() = stateLiveData.value?.loadedAllItems ?: false

}