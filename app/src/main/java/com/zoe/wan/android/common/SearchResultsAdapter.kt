package com.zoe.wan.android.common

import android.text.Html
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemSearchBinding
import com.zoe.wan.android.repository.data.SearchData

/**
 * 搜索返回列表适配器
 */
class SearchResultsAdapter : RecyclerView.Adapter<SearchResultsAdapter.SearchViewHolder>() {
    private var dataList: List<SearchData?>? = null
    private var itemClick: AdapterItemListener<SearchData?>? = null

    class SearchViewHolder(itemSearchBinding: ItemSearchBinding) : RecyclerView.ViewHolder
        (itemSearchBinding.root) {
        var binding: ItemSearchBinding

        init {
            binding = itemSearchBinding
        }
    }

    fun setItemClick(listener: AdapterItemListener<SearchData?>) {
        this.itemClick = listener
    }

    fun setDataList(list: List<SearchData?>) {
        if (list.isNotEmpty()) {
            this.dataList = list
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_search, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        dataList?.get(position)?.let { item ->
            holder.binding.itemSearch = item
            holder.binding.itemSearchTitle.text = Html.fromHtml(item.title)
            holder.binding.itemSearchTitle.setOnClickListener {
                itemClick?.itemClick(item, position)
            }
        }


    }
}
