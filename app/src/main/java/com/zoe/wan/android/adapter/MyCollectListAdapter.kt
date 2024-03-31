package com.zoe.wan.android.adapter

import android.view.ViewGroup
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemMyCollectListBinding
import com.zoe.wan.android.repository.data.CollectListData
import com.zoe.wan.base.adapter.AdapterCollectListener
import com.zoe.wan.base.adapter.BaseRecyclerAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

/**
 * 我的收藏列表
 */
class MyCollectListAdapter : BaseRecyclerAdapter<CollectListData,
    MyCollectListAdapter.MyCollectListViewHolder>() {
    private var collectListener: AdapterCollectListener<CollectListData?>? = null

    fun setCollectListener(listener: AdapterCollectListener<CollectListData?>?) {
        this.collectListener = listener
    }

    /**
     * 收藏状态刷新
     */
    fun notifyCollectChange(position: Int, collect: Boolean) {
        if (getDataList()?.isEmpty() == true) {
            return
        }

        val list = getDataList()?.toMutableList()
        list?.removeAt(position)
        setDataList(list)
    }

    class MyCollectListViewHolder(binding: ItemMyCollectListBinding) :
        BaseViewHolder<ItemMyCollectListBinding>(binding)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): MyCollectListViewHolder {
        return MyCollectListViewHolder(getBinding(parent, R.layout.item_my_collect_list))
    }

    override fun bindHolder(holder: MyCollectListViewHolder, position: Int) {
        holder.binding.item = getDataList()?.get(position)
        //点击收藏/取消收藏
        holder.binding.itemHomeCollect.setOnClickListener {
            //取消收藏
            collectListener?.cancelCollect(position, "${getDataList()?.get(position)?.id}")
        }
    }

}
