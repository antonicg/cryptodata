package com.antonicastejon.cryptodata.common

/**
 * Created by Antoni Castejón
 * 20/01/2018.
 */
fun Float.formatTo(numberOfDecimals:Int) =
        String.format("%.${numberOfDecimals}f", this)