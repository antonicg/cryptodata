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

    val state =  MutableLiveData<CryptoListState>()
    val cryptoList by lazy { MutableLiveData<List<CryptoViewModel>>()}
    val isLastPage by lazy { MutableLiveData<Boolean>() }

    var page = 0

    init {
        state.value = CryptoListState(DEFAULT)
    }

    fun observeData(owner: LifecycleOwner, observer: Observer<List<CryptoViewModel>>) = cryptoList.observe(owner, observer)
    fun observeState(owner: LifecycleOwner, observer: Observer<CryptoListState>) = state.observe(owner, observer)
    fun observeLastPage(owner: LifecycleOwner, observer: Observer<Boolean>) = isLastPage.observe(owner, observer)

    fun refreshList() {
        state.value = CryptoListState(LOADING)
        page = 0
        getCryptoList(page)
    }

    fun loadNextPage() {
        state.value = CryptoListState(PAGINATING)
        page++
        getCryptoList(page)
    }
    private fun getCryptoList(page:Int) {
        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onCryptoListReceived, this::onError)
    }

    private fun onCryptoListReceived(cryptoList: List<CryptoViewModel>) {
        isLastPage.value = cryptoList.size < LIMIT_CRYPTO_LIST
        state.value = CryptoListState(DEFAULT)
        this.cryptoList.value = cryptoList
    }

    private fun onError(error: Throwable) {
        state.value = CryptoListState(ERROR_API)
        error.printStackTrace()
    }
}