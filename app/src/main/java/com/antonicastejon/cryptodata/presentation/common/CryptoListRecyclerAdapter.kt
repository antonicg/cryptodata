package com.antonicastejon.cryptodata.presentation.common

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.antonicastejon.cryptodata.R
import com.antonicastejon.cryptodata.domain.CryptoViewModel
import kotlinx.android.synthetic.main.crypto_list_item.view.*

/**
 * Created by Antoni Castejón on 07/01/2018.
 */
class CryptoListRecyclerAdapter : RecyclerView.Adapter<CryptoListRecyclerAdapter.CryptoViewHolder>()  {

    private val data = ArrayList<CryptoViewModel>()

    override fun onBindViewHolder(holder: CryptoViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CryptoViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.crypto_list_item, parent, false)
        return CryptoViewHolder(view)
    }

    fun updateData(newData: List<CryptoViewModel>) {
        data.clear()
        data.addAll(newData)
        notifyDataSetChanged()
    }

    class CryptoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val resources by lazy { itemView.context.resources }

        fun bind(item:CryptoViewModel) {
            itemView.tvSymbol.text = item.symbol
            itemView.tvPriceBtc.text = resources.getString(R.string.price_btc, item.priceBtc)
            itemView.tvPriceFiat.text = resources.getString(R.string.price_usd, item.priceFiat)
            itemView.tvChange24h.text = resources.getString(R.string.change_24h, item.change)
        }
    }
}
