package com.zoe.wan.android.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemKnowledgeBinding
import com.zoe.wan.android.repository.data.KnowledgeItem
import java.lang.StringBuilder

/**
 * 知识体系列表适配器
 */
class KnowledgeListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var dataList: List<KnowledgeItem?>? = mutableListOf()
    private var itemClick: AdapterItemListener<KnowledgeItem>? = null

    fun setDataList(list: List<KnowledgeItem?>?) {
        if (list?.isNotEmpty() == true) {
            dataList = list
            notifyDataSetChanged()
        }
    }

    fun setItemClick(listener: AdapterItemListener<KnowledgeItem>) {
        this.itemClick = listener
    }

    class KnowledgeViewHolder(itemKnowledgeBinding: ItemKnowledgeBinding) : RecyclerView.ViewHolder
        (itemKnowledgeBinding.root) {
        var binding: ItemKnowledgeBinding

        init {
            binding = itemKnowledgeBinding
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return KnowledgeViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context), R
                    .layout.item_knowledge, parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return dataList?.size ?: 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is KnowledgeViewHolder) {
            val item = dataList?.get(position)
            holder.binding.itemKnowledgeTitle.text = item?.name
            val sb = StringBuilder()
            item?.children?.forEach { child ->
                child?.name?.let {
                    sb.append(it)
                    sb.append("  ")
                }
            }
            holder.binding.itemKnowledgeSubTitle.text = sb.toString()
            //点击进入明细列表
            holder.binding.itemKnowledgeRoot.setOnClickListener {
                itemClick?.itemClick(item, position)
            }
        }
    }
}
