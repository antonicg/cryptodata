package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.support.annotation.IntDef

/**
 * Created by Antoni Castejón
 * 20/01/2018.
 */

const val DEFAULT = 0L
const val LOADING = 1L
const val PAGINATING = 2L
const val ERROR_API = 3L
const val ERROR_NO_INTERNET = 4L
@IntDef(DEFAULT, LOADING, PAGINATING, ERROR_API, ERROR_NO_INTERNET)
@Retention(AnnotationRetention.SOURCE)
annotation class STATE

data class CryptoListState(@STATE val state:Long)