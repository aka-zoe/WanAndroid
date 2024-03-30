package com.zoe.wan.android.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemHomeListBinding
import com.zoe.wan.android.repository.data.CollectListData

/**
 * 我的收藏列表
 */
class MyCollectListAdapter : RecyclerView.Adapter<MyCollectListAdapter.MyCollectListViewHolder>() {

    private var dataList: List<CollectListData?>? = null
    private var itemClick: AdapterItemListener<CollectListData>? = null

    class MyCollectListViewHolder(itemHomeListBinding: ItemHomeListBinding) : RecyclerView
    .ViewHolder(itemHomeListBinding.root) {
        var binding: ItemHomeListBinding

        init {
            binding = itemHomeListBinding
        }
    }

    fun setDataList(list: List<CollectListData?>) {
        if (list.isNotEmpty()) {
            dataList = list
            notifyDataSetChanged()
        }
    }

    fun setItemClick(listener: AdapterItemListener<CollectListData>) {
        this.itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyCollectListViewHolder {
        return MyCollectListViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_home_list, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyCollectListViewHolder, position: Int) {
        holder.binding.itemHomeListTitle.text = dataList?.get(position)?.title
        holder.binding.itemHomeLinear.setOnClickListener {
            this.itemClick?.itemClick(dataList?.get(position), position)
        }
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }
}
