package com.zoe.wan.android.common

import android.view.ViewGroup
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemHomeListBinding
import com.zoe.wan.android.repository.data.CollectListData
import com.zoe.wan.base.adapter.BaseRecyclerAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

/**
 * 我的收藏列表
 */
class MyCollectListAdapter : BaseRecyclerAdapter<CollectListData,
    MyCollectListAdapter.MyCollectListViewHolder>() {

    class MyCollectListViewHolder(itemHomeListBinding: ItemHomeListBinding) :
        BaseViewHolder<ItemHomeListBinding>(itemHomeListBinding)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): MyCollectListViewHolder {
        return MyCollectListViewHolder(getBinding(parent, R.layout.item_home_list))
    }

    override fun bindHolder(holder: MyCollectListViewHolder, position: Int) {
        holder.binding.itemHomeListTitle.text = getDataList()?.get(position)?.title
    }

}
