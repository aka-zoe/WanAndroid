package com.zoe.wan.android.common

import android.view.ViewGroup
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemKnowledgeBinding
import com.zoe.wan.android.repository.data.KnowledgeItem
import com.zoe.wan.base.adapter.BaseRecyclerAdapter
import com.zoe.wan.base.adapter.BaseViewHolder
import java.lang.StringBuilder

/**
 * 知识体系列表适配器
 */
class KnowledgeListAdapter :
    BaseRecyclerAdapter<KnowledgeItem, KnowledgeListAdapter.KnowledgeViewHolder>() {

    class KnowledgeViewHolder(itemKnowledgeBinding: ItemKnowledgeBinding) :
        BaseViewHolder<ItemKnowledgeBinding>(itemKnowledgeBinding)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): KnowledgeViewHolder {
        return KnowledgeViewHolder(getBinding(parent ,R.layout.item_knowledge))
    }

    override fun bindHolder(holder: KnowledgeViewHolder, position: Int) {
        val item = getDataList()?.get(position)
        holder.binding.itemKnowledgeTitle.text = item?.name
        val sb = StringBuilder()
        item?.children?.forEach { child ->
            child?.name?.let {
                sb.append(it)
                sb.append("  ")
            }
        }
        holder.binding.itemKnowledgeSubTitle.text = sb.toString()
    }


}
