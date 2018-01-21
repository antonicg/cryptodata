package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.domain.LIMIT_CRYPTO_LIST
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCases: CryptoListUseCases) : ViewModel() {

    private val state =  MutableLiveData<CryptoListState>()
    private val cryptoListLiveData by lazy { MutableLiveData<List<CryptoViewModel>>()}
    private val cryptoList by lazy { mutableListOf<CryptoViewModel>() }

    init {
        state.value = CryptoListState(DEFAULT, 0, false)
    }

    fun observeData(owner: LifecycleOwner, observer: Observer<List<CryptoViewModel>>) = cryptoListLiveData.observe(owner, observer)
    fun observeState(owner: LifecycleOwner, observer: Observer<CryptoListState>) = state.observe(owner, observer)

    fun updateCryptoList() {
        val pageNum = state.value?.pageNum ?: 0
        state.value = CryptoListState(LOADING, pageNum, false)
        getCryptoList(pageNum)
    }

    private fun getCryptoList(page:Int) {
        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCryptoListReceived, this::onError)
    }

    private fun onCryptoListReceived(cryptoList: List<CryptoViewModel>) {
        state.value = CryptoListState(DEFAULT, (state.value?.pageNum ?: 0) + 1, cryptoList.size < LIMIT_CRYPTO_LIST)
        this.cryptoList.addAll(cryptoList)
        this.cryptoListLiveData.value = this.cryptoList
    }

    private fun onError(error: Throwable) {
        val pageNum = state.value?.pageNum ?: 0
        state.value = CryptoListState(ERROR_API, pageNum, state.value?.loadedAllItems ?: false)
        error.printStackTrace()
    }
}