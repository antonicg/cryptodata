package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonicastejon.cryptodata.R

/**
 * Created by Antoni Castejón on 29/12/2017.
 */
class CryptoListFragment: Fragment() {

    lateinit var viewModel:CryptoListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(CryptoListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.crypto_list_fragment, container, false)
        return view
    }
}