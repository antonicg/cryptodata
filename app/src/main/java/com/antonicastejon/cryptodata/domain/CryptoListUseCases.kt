package com.antonicastejon.cryptodata.domain

import android.os.Parcel
import android.os.Parcelable
import io.reactivex.Single

interface CryptoListUseCases {
    fun getCryptoListBy(page: Int) : Single<List<CryptoViewModel>>
}

val emptyCryptoViewModel = CryptoViewModel()

data class CryptoViewModel(val id: String, val name: String, val symbol: String, val rank: Int, val priceFiat: Float, val priceBtc: Float, val change: Float)
    : Parcelable {

    constructor() : this("", "", "", 0, 0f, 0f, 0f)

    fun isBtc() = symbol.equals("BTC")

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readInt(),
            parcel.readFloat(),
            parcel.readFloat(),
            parcel.readFloat()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(symbol)
        parcel.writeInt(rank)
        parcel.writeFloat(priceFiat)
        parcel.writeFloat(priceBtc)
        parcel.writeFloat(change)
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