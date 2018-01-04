package com.antonicastejon.cryptodata.domain

import android.os.Parcel
import android.os.Parcelable
import io.reactivex.Flowable

/**
 * Created by Antoni Castejón on 31/12/2017.
 */
interface CryptoListUseCases {
    fun getCryptoListBy(page: Int) : Flowable<List<CryptoViewModel>>
}


data class CryptoViewModel(val name: String) : Parcelable {
    constructor(parcel: Parcel) : this(parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CryptoViewModel> {
        override fun createFromParcel(parcel: Parcel): CryptoViewModel {
            return CryptoViewModel(parcel)
        }

        override fun newArray(size: Int): Array<CryptoViewModel?> {
            return arrayOfNulls(size)
        }
    }
}