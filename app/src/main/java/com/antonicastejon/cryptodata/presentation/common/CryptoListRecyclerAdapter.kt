package com.antonicastejon.cryptodata.presentation.common

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonicastejon.cryptodata.R
import com.antonicastejon.cryptodata.common.formatTo
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import com.antonicastejon.cryptodata.domain.emptyCryptoViewModel
import com.antonicastejon.cryptodata.presentation.widgets.paginatedRecyclerView.PaginationAdapter
import kotlinx.android.synthetic.main.crypto_list_item.view.*

private const val DECIMALS_FIAT = 4
private const val DECIMALS_BTC = 7
private const val DECIMALS_CHANGE = 2

class CryptoListRecyclerAdapter : PaginationAdapter<CryptoViewModel>() {
    override fun addLoadingViewFooter() {
        addLoadingViewFooter(emptyCryptoViewModel)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is CryptoViewHolder) holder.bind(dataList[position])
    }

    override fun onCreateItemViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_list_item, parent, false)
        return CryptoViewHolder(view)
    }

    fun updateData(newData: List<CryptoViewModel>) {
        val fromIndex = dataList.size
        dataList = newData.toMutableList()
        notifyItemRangeInserted(fromIndex, newData.size)
    }
}

class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    val resources by lazy { itemView.context.resources }

    fun bind(item: CryptoViewModel) {
        itemView.apply {
            tvPosition.text = item.rank.toString()
            tvSymbol.text = item.symbol
            tvPrice.text = bindPrice(item)
            tvChange24h.text = bindChangeText(item)
            tvChange24h.setTextColor(bindChangeColor(item))
        }
    }

    fun bindPrice(item: CryptoViewModel) =
            if (item.isBtc()) resources.getString(R.string.price_btc, item.priceFiat.formatTo(DECIMALS_FIAT))
            else resources.getString(R.string.price_alts, item.priceFiat.formatTo(DECIMALS_FIAT), item.priceBtc.formatTo(DECIMALS_BTC))

    fun bindChangeColor(item: CryptoViewModel) =
            ContextCompat.getColor(itemView.context, if (item.change > 0) R.color.change_positive else R.color.change_negative)

    fun bindChangeText(item: CryptoViewModel) =
            resources.getString(R.string.change_percent, item.change.formatTo(DECIMALS_CHANGE))
}
