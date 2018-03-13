package com.antonicastejon.cryptodata.domain

import android.arch.lifecycle.MutableLiveData

interface UseCase<T> {

    val liveData: MutableLiveData<T?>
        get() = MutableLiveData()

    fun observe(callback: (T?) -> Unit) {
        liveData.observeForever{callback(it)}
    }

    fun clear(callback: (T?) -> Unit) {
        liveData.removeObserver(callback)
    }

}