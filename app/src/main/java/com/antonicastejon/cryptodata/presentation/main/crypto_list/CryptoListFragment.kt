package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonicastejon.cryptodata.R
import com.antonicastejon.cryptodata.presentation.common.CryptoListRecyclerAdapter
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.crypto_list_fragment.*
import kotlinx.android.synthetic.main.crypto_list_fragment.view.*
import javax.inject.Inject

/**
 * Created by Antoni Castejón on 29/12/2017.
 */
val CRYPTO_LIST_FRAGMENT_TAG = CryptoListFragment::class.java.name

private val TAG = CryptoListFragment::class.java.name

fun newCryptoListFragment() = CryptoListFragment()

class CryptoListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: CryptoListViewModel

    val adapter by lazy { CryptoListRecyclerAdapter() }
    var isLoading = false

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initializeRecyclerView()

        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptoListViewModel::class.java)
        observeViewModel()
        viewModel.getCryptoList(0)
    }

    private fun observeViewModel() {
        viewModel.observeData(this, Observer { cryptolist ->
            cryptolist?.let {
                adapter.updateData(cryptolist)
            }
        })
        viewModel.observeState(this, Observer { state ->
            state?.let {
                isLoading = it.state == LOADING || it.state == PAGINATING
                when (it.state) {
                    DEFAULT -> swipeRefreshLayout.isRefreshing = false
                    LOADING -> swipeRefreshLayout.isRefreshing = true
                    PAGINATING -> {
                    }
                    ERROR_API -> {
                    }
                    ERROR_NO_INTERNET -> {
                    }
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.crypto_list_fragment, container, false)
        initializeToolbar(view)
        return view
    }

    private fun initializeRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = this.adapter
    }

    private fun initializeToolbar(view: View) {
        view.toolbar.title = getString(R.string.app_name)
    }
}