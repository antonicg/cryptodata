package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCases: CryptoListUseCases) : ViewModel() {

    val state =  MutableLiveData<CryptoListState>()
    val cryptoList by lazy { MutableLiveData<List<CryptoViewModel>>()}

    init {
        state.value = CryptoListState(DEFAULT)
    }

    fun observeData(owner: LifecycleOwner, observer: Observer<List<CryptoViewModel>>) = cryptoList.observe(owner, observer)
    fun observeState(owner: LifecycleOwner, observer: Observer<CryptoListState>) = state.observe(owner, observer)

    fun getCryptoList(page:Int) {
        if (page == 0) state.value = CryptoListState(LOADING)
        else state.value = CryptoListState(PAGINATING)

        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCryptoListReceived, this::onError)
    }

    fun onCryptoListReceived(cryptoList: List<CryptoViewModel>) {
        state.value = CryptoListState(DEFAULT)
        this.cryptoList.value = cryptoList
    }

    fun onError(error: Throwable) {
        state.value = CryptoListState(ERROR_API)
        error.printStackTrace()
    }
}