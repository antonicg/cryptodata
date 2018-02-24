package com.antonicastejon.cryptodata.presentation.widgets.paginatedRecyclerView

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

private val TAG: String = PaginationScrollListener::class.java.name

abstract class PaginationScrollListener(val layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    protected abstract fun loadMoreItems()
    abstract fun getTotalPageCount(): Int
    abstract fun isLastPage(): Boolean
    abstract fun isLoading(): Boolean

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (allowLoadMore() && isNearToLastItem(layoutManager)) {
            loadMoreItems()
        }
    }

    private fun allowLoadMore() = !isLoading() && !isLastPage()

    private fun isNearToLastItem(layoutManager: LinearLayoutManager): Boolean {
        val visibleItemCount = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        return visibleItemCount + firstVisibleItemPosition >= totalItemCount &&
                firstVisibleItemPosition >= 0
    }
}