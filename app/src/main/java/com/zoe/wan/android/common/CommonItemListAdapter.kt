package com.zoe.wan.android.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemItemBinding
import com.zoe.wan.android.repository.data.CommonItem

/**
 * 热点/常用网站item列表适配器
 */
class CommonItemListAdapter : RecyclerView.Adapter<CommonItemListAdapter.ItemViewHolder>() {

    private var dataList = mutableListOf<CommonItem>()
    private var itemClick: AdapterItemListener<CommonItem>? = null

    fun setDataList(list: List<CommonItem>?) {
        if (list != null) {
            this.dataList.clear()
            this.dataList.addAll(list)
            notifyDataSetChanged()
        }

    }

    fun setItemClick(listener: AdapterItemListener<CommonItem>) {
        this.itemClick = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R.layout.item_item, parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {

        val item = dataList[position]

        holder.binding.itemData = item
        holder.binding.itemKeyName.text = item.name
        holder.binding.itemCard.setOnClickListener {
            this.itemClick?.itemClick(item, position)
        }
    }

    class ItemViewHolder(itemBinding: ItemItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {
        var binding: ItemItemBinding

        init {
            this.binding = itemBinding
        }
    }

}
