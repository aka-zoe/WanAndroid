package com.zoe.wan.android.adapter

import android.view.ViewGroup
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ItemKnowledgeDetailBinding
import com.zoe.wan.android.repository.data.KnowledgeDetailData
import com.zoe.wan.base.adapter.BaseRecyclerAdapter
import com.zoe.wan.base.adapter.BaseViewHolder

/**
 * 知识体系明细列表数据适配器
 */
class DetailListAdapter : BaseRecyclerAdapter<KnowledgeDetailData, DetailListAdapter
.DetailListViewHolder>() {

    class DetailListViewHolder(binding: ItemKnowledgeDetailBinding) :
        BaseViewHolder<ItemKnowledgeDetailBinding>(binding)

    override fun getViewHolder(parent: ViewGroup, viewType: Int): DetailListViewHolder {
        return DetailListViewHolder(getBinding(parent, R.layout.item_knowledge_detail))
    }

    override fun bindHolder(holder: DetailListViewHolder, position: Int) {
        var name = if(getDataList()?.get(position)?.author?.isEmpty()==true){
            getDataList()?.get(position)?.shareUser
        }else{
            getDataList()?.get(position)?.author
        }
        holder.binding.itemKnowledgeTitle.text = name
        holder.binding.itemKnowledgeSubTitle.text = getDataList()?.get(position)?.title
    }
}
