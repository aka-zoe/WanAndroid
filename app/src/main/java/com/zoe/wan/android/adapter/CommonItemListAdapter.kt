package com.zoe.wan.android.adapter

import android.view.ViewGroup
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemItemBinding
import com.zoe.wan.android.repository.data.CommonItem
import com.zoe.wan.base.adapter.BaseRecyclerAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

/**
 * 热点/常用网站item列表适配器
 */
class CommonItemListAdapter :
    BaseRecyclerAdapter<CommonItem, CommonItemListAdapter.ItemViewHolder>() {

    override fun getViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(getBinding(parent, R.layout.item_item))
    }

    override fun bindHolder(holder: ItemViewHolder, position: Int) {
        val item = getDataList()?.get(position)

        holder.binding.itemData = item
        holder.binding.itemKeyName.text = item?.name
    }

    class ItemViewHolder(itemBinding: ItemItemBinding) :
        BaseViewHolder<ItemItemBinding>(itemBinding)
}
