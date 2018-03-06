package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.di.SCHEDULER_IO
import com.antonicastejon.cryptodata.di.SCHEDULER_MAIN_THREAD
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.domain.LIMIT_CRYPTO_LIST
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCases: CryptoListUseCases, @Named(SCHEDULER_IO) val subscribeOnScheduler:Scheduler, @Named(SCHEDULER_MAIN_THREAD) val observeOnScheduler: Scheduler) : ViewModel() {

    val stateLiveData =  MutableLiveData<CryptoListState>()
    private var pageNum = 0

    init {
        stateLiveData.value = DefaultState(false, emptyList())
    }

    fun updateCryptoList() {
        stateLiveData.value = if (pageNum == 0)
            LoadingState(false, obtainCurrentData())
        else
            PaginatingState(false, obtainCurrentData())
        getCryptoList(pageNum)
    }

    fun resetCryptoList() {
        pageNum = 0
        stateLiveData.value = LoadingState(false, emptyList())
        updateCryptoList()
    }

    private fun getCryptoList(page:Int) {
        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(this::onCryptoListReceived, this::onError)
    }

    private fun onCryptoListReceived(cryptoList: List<CryptoViewModel>) {
        val currentCryptoList = obtainCurrentData().toMutableList()
        pageNum++
        val areAllItemsLoaded = cryptoList.size < LIMIT_CRYPTO_LIST
        currentCryptoList.addAll(cryptoList)
        stateLiveData.value = DefaultState(areAllItemsLoaded, currentCryptoList)
    }

    private fun onError(error: Throwable) {
        stateLiveData.value = ErrorState(error.message ?: "", obtainCurrentData())
    }

    private fun obtainCurrentData() = stateLiveData.value?.data ?: emptyList()
}