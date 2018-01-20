package com.antonicastejon.cryptodata.presentation.widgets.paginatedRecyclerView

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonicastejon.cryptodata.R

/**
 * Created by Antoni Castejón
 * 20/01/2018.
 */

private const val LOADING_VIEW_TYPE = 0
private const val ITEM_VIEW_TYPE = 1

abstract class PaginationAdapter<D> : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    abstract fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder
    abstract fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int)

    private var isLoadingViewAdded = false
    protected val dataList = mutableListOf<D>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            LOADING_VIEW_TYPE -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.list_loading_footer_item, parent, false)
                LoadingViewHolder(view)
            }
            else -> onCreateItemViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (getItemViewType(position) != LOADING_VIEW_TYPE) onBindItemViewHolder(holder, position)
    }

    override fun getItemCount() = dataList.size

    override fun getItemViewType(position: Int) =
            if (position == dataList.size -1 && isLoadingViewAdded) LOADING_VIEW_TYPE else ITEM_VIEW_TYPE

    fun addLoadingViewFooter(emptyDataObject: D) {
        if (dataList.size > 0) {
            isLoadingViewAdded = true
            dataList.add(emptyDataObject)
            notifyItemInserted(dataList.size - 1)
        }
    }

    fun removeLoadingViewFooter() {
        if (isLoadingViewAdded && dataList.size > 0) {
            isLoadingViewAdded = false
            dataList.removeAt(dataList.size-1)
            notifyItemRemoved(dataList.size)
        }
    }
}

class LoadingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)