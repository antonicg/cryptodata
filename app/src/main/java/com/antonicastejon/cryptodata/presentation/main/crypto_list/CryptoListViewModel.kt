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

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCases: CryptoListUseCases, @Named(SCHEDULER_IO) val subscribeOnScheduler:Scheduler, @Named(SCHEDULER_MAIN_THREAD) val observeOnScheduler: Scheduler) : ViewModel() {

    val stateLiveData =  MutableLiveData<CryptoListState>()

    private val cryptoList = mutableListOf<CryptoViewModel>()

    init {
        stateLiveData.value = CryptoListState(DEFAULT, 0, false, cryptoList)
    }

    fun updateCryptoList() {
        val pageNum = stateLiveData.value?.pageNum ?: 0
        val state = if (pageNum == 0) LOADING else PAGINATING
        stateLiveData.value = CryptoListState(state, pageNum, false, this.cryptoList)
        getCryptoList(pageNum)
    }

    fun resetCryptoList() {
        val pageNum = 0
        cryptoList.clear()
        stateLiveData.value = CryptoListState(LOADING, pageNum, false, cryptoList)
        updateCryptoList()
    }

    private fun getCryptoList(page:Int) {
        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(subscribeOnScheduler)
                .observeOn(observeOnScheduler)
                .subscribe(this::onCryptoListReceived, this::onError)
    }

    private fun onCryptoListReceived(cryptoList: List<CryptoViewModel>) {
        this.cryptoList.addAll(cryptoList)
        stateLiveData.value = CryptoListState(DEFAULT, (stateLiveData.value?.pageNum ?: 0) + 1, cryptoList.size < LIMIT_CRYPTO_LIST, this.cryptoList)
    }

    private fun onError(error: Throwable) {
        val pageNum = stateLiveData.value?.pageNum ?: 0
        stateLiveData.value = CryptoListState(ERROR_API, pageNum, stateLiveData.value?.loadedAllItems ?: false, this.cryptoList)
        error.printStackTrace()
    }
}