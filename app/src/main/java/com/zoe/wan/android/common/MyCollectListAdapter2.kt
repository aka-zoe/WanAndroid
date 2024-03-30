package com.zoe.wan.android.common

import android.view.ViewGroup
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemHomeListBinding
import com.zoe.wan.android.repository.data.CollectListData
import com.zoe.wan.base.adapter.BaseRecyclerAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

class MyCollectListAdapter2:BaseRecyclerAdapter<CollectListData?,ItemHomeListBinding,
    MyCollectListAdapter2.MyViewHolder>() {

    override fun bindHolder(holder: MyViewHolder, position: Int) {
        val item = getDataList()?.get(position)
    }

    override fun getViewHolder(parent: ViewGroup): MyCollectListAdapter2.MyViewHolder {
      return MyViewHolder(getBinding(parent,R.layout.item_home_list))
    }

    class MyViewHolder(itemHomeListBinding: ItemHomeListBinding):BaseViewHolder<ItemHomeListBinding>
        (itemHomeListBinding)

}
