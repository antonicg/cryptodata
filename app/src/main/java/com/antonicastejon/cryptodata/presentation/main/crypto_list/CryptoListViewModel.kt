package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.antonicastejon.cryptodata.domain.CryptoListInteractor

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
class CryptoListViewModel(private val cryptoListInteractor: CryptoListInteractor) : ViewModel() {

    var page = 1

    val cryptoList: MutableLiveData<List<CryptoListViewModel>> by lazy {
        MutableLiveData<List<CryptoListViewModel>>()
    }

    fun getCryptoList() {
        // TODO
//        cryptoListInteractor.getCryptoListBy(page)
//                .subscribe()
    }
}