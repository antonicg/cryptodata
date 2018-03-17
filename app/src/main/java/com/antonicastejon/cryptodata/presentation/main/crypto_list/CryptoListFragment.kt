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

val CRYPTO_LIST_FRAGMENT_TAG = CryptoListFragment::class.java.name

private val TAG = CryptoListFragment::class.java.name

fun newCryptoListFragment() = CryptoListFragment()

class CryptoListFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CryptoListViewModel

    private val cryptoListAdapter by lazy { CryptoListRecyclerAdapter() }

    private val stateObserver = Observer<CryptoListState> { state ->
        state?.let {
            when (state) {
                is DefaultState -> {
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.updateData(it.data)
                }
                is ErrorState -> {
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.removeLoadingViewFooter()
                }
            }
        }
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.crypto_list_fragment, container, false)
        initializeToolbar(view)
        initializeRecyclerView(view)
        initializeSwipeToRefreshView(view)
        return view
    }

    private fun initializeToolbar(view:View) {
        view.toolbar.title = getString(R.string.app_name)
    }

    private fun initializeRecyclerView(view:View) {
        val linearLayoutManager = LinearLayoutManager(context)
        view.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = cryptoListAdapter
        }
    }

    private fun initializeSwipeToRefreshView(view:View) {
        view.swipeRefreshLayout.setOnRefreshListener { requestCryptoListToViewModel() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptoListViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
        if (savedInstanceState == null) {
            requestCryptoListToViewModel()
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    private fun requestCryptoListToViewModel() {
        swipeRefreshLayout.isRefreshing = true
        viewModel.requestCryptoList()
    }
}