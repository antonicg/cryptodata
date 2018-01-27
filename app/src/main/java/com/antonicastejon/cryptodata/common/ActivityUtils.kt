package com.antonicastejon.cryptodata.common

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity

/**
 * Created by Antoni Castejón
 * 21/01/2018.
 */
fun AppCompatActivity.replaceFragment(@IdRes where: Int, fragment: Fragment, tag: String) {
    supportFragmentManager.beginTransaction()
            .replace(where, fragment, tag)
            .commit()
}