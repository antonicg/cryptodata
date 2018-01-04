package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.antonicastejon.cryptodata.domain.CryptoListUseCases
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Antoni Castejón on 31/12/2017.
 */

private val TAG = CryptoListViewModel::class.java.name

class CryptoListViewModel
@Inject constructor(private val cryptoListUseCases: CryptoListUseCases) : ViewModel() {

    var page = 0

    val cryptoList: MutableLiveData<List<CryptoListViewModel>> by lazy {
        MutableLiveData<List<CryptoListViewModel>>()
    }

    fun getCryptoList() {
        // TODO
        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    list -> list.forEach { Log.d(TAG, it.toString()) }
                },
                        {
                            error -> error.printStackTrace()
                        })
    }
}