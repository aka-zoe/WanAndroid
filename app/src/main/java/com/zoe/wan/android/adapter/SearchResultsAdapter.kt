package com.zoe.wan.android.adapter

import android.text.Html
import android.view.ViewGroup
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemSearchBinding
import com.zoe.wan.android.repository.data.SearchData
import com.zoe.wan.base.adapter.BaseRecyclerAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

/**
 * 搜索返回列表适配器
 */
class SearchResultsAdapter : BaseRecyclerAdapter<SearchData, SearchResultsAdapter.SearchViewHolder>() {

    class SearchViewHolder(itemSearchBinding: ItemSearchBinding) :
        BaseViewHolder<ItemSearchBinding>(itemSearchBinding)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        return SearchViewHolder(getBinding(parent, R.layout.item_search))
    }

    override fun bindHolder(holder: SearchViewHolder, position: Int) {
        getDataList()?.get(position)?.let { item ->
            holder.binding.itemSearch = item
            holder.binding.itemSearchTitle.text = Html.fromHtml(item.title)
        }
    }

}
