package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonicastejon.cryptodata.R
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

/**
 * Created by Antoni Castejón on 29/12/2017.
 */
val CRYPTO_LIST_FRAGMENT_TAG = CryptoListFragment::class.java.name

fun newCryptoListFragment() = CryptoListFragment()

class CryptoListFragment: Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel:CryptoListViewModel

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptoListViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.crypto_list_fragment, container, false)
        return view
    }
}