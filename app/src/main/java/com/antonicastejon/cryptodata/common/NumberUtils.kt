package com.antonicastejon.cryptodata.common

fun Float.formatTo(numberOfDecimals:Int) =
        String.format("%.${numberOfDecimals}f", this)