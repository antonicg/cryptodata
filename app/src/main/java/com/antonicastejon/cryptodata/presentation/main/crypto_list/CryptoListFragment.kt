package com.antonicastejon.cryptodata.presentation.main.crypto_list

import android.app.SearchManager
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import com.antonicastejon.cryptodata.R
import com.antonicastejon.cryptodata.domain.LIMIT_CRYPTO_LIST
import com.antonicastejon.cryptodata.presentation.common.CryptoListRecyclerAdapter
import com.antonicastejon.cryptodata.presentation.widgets.paginatedRecyclerView.PaginationScrollListener
import dagger.android.support.AndroidSupportInjection
import kotlinx.android.synthetic.main.crypto_list_fragment.*
import kotlinx.android.synthetic.main.crypto_list_fragment.view.*
import javax.inject.Inject

val CRYPTO_LIST_FRAGMENT_TAG = CryptoListFragment::class.java.name

private val TAG = CryptoListFragment::class.java.name

fun newCryptoListFragment() = CryptoListFragment()

class CryptoListFragment : Fragment(), SearchView.OnQueryTextListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: CryptoListViewModel

    private lateinit var searchView: SearchView

    private val cryptoListAdapter by lazy { CryptoListRecyclerAdapter() }
    private var isLoading = false
    private var isLastPage = false

    private val stateObserver = Observer<CryptoListState> { state ->
        state?.let {
            when (state) {
                is DefaultState -> {
                    isLoading = false
                    isLastPage = state.loadedAllItems
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.updateData(it.data)
                }
                is LoadingState -> {
                    isLastPage = state.loadedAllItems
                    swipeRefreshLayout.isRefreshing = true
                    isLoading = true
                }
                is PaginatingState -> {
                    isLastPage = state.loadedAllItems
                    isLoading = true
                }
                is ErrorState -> {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.removeLoadingViewFooter()
                }
                is SearchState -> {
                    isLoading = false
                    swipeRefreshLayout.isRefreshing = false
                    cryptoListAdapter.updateData(it.data)
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
        view.toolbar?.apply {
            title = getString(R.string.app_name)
            inflateMenu(R.menu.crypto_list_menu)
            searchView = obtainSearchView(menu)
        }
    }

    private fun obtainSearchView(menu: Menu): SearchView {
        val searchView = menu.findItem(R.id.search).actionView as SearchView
        activity?.let {
            val searchManager = it.getSystemService(Context.SEARCH_SERVICE) as SearchManager
            searchView.setSearchableInfo(searchManager.getSearchableInfo(it.componentName))
            searchView.setOnQueryTextListener(this)
        }
        return searchView
    }

    private fun initializeRecyclerView(view:View) {
        val linearLayoutManager = LinearLayoutManager(context)
        view.recyclerView.apply {
            layoutManager = linearLayoutManager
            adapter = cryptoListAdapter
            addOnScrollListener(OnScrollListener(linearLayoutManager))
        }
    }

    private fun initializeSwipeToRefreshView(view:View) {
        view.swipeRefreshLayout.setOnRefreshListener { viewModel.resetCryptoList() }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(CryptoListViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.crypto_list_menu, menu)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.invalidateOptionsMenu()

        observeViewModel()
        if (savedInstanceState == null) {
            viewModel.updateCryptoList()
        }
    }

    private fun observeViewModel() {
        viewModel.stateLiveData.observe(this, stateObserver)
    }

    private fun loadNextPage() {
        cryptoListAdapter.addLoadingViewFooter()
        viewModel.updateCryptoList()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d(TAG, "onQueryTextChange: $newText")
        return true
    }

    inner class OnScrollListener(layoutManager: LinearLayoutManager) : PaginationScrollListener(layoutManager) {
        override fun isLoading() = isLoading
        override fun loadMoreItems() = loadNextPage()
        override fun getTotalPageCount() = LIMIT_CRYPTO_LIST
        override fun isLastPage() = isLastPage
    }
}