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

    var page = 0

    val cryptoList: MutableLiveData<List<CryptoViewModel>> by lazy {
        MutableLiveData<List<CryptoViewModel>>()
    }

    fun observe(owner: LifecycleOwner, observer: Observer<List<CryptoViewModel>>) {
        cryptoList.observe(owner, observer)
    }

    fun getCryptoList() {
        // TODO
        cryptoListUseCases.getCryptoListBy(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { list -> cryptoList.value = list },
                        { error -> error.printStackTrace() }
                )
    }
}