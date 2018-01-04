package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import javax.inject.Inject

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCases: CryptoListUseCases) : ViewModel() {

    var page = 1

    val cryptoList: MutableLiveData<List<CryptoListViewModel>> by lazy {
        MutableLiveData<List<CryptoListViewModel>>()
    }

    fun getCryptoList() {
        // TODO
        cryptoListUseCases.getCryptoListBy(page)
                .subscribe{ list -> Log.d("test", "${list.size}") }
    }
}