package com.zoe.wan.android.fragment.knowledge

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.R
import com.zoe.wan.android.BR
import com.zoe.wan.android.activity.knowledge_detail.KnowledgeDetailActivity
import com.zoe.wan.android.common.AdapterItemListener
import com.zoe.wan.android.common.KnowledgeListAdapter
import com.zoe.wan.android.databinding.FragmentKnowledgeBinding
import com.zoe.wan.android.repository.data.KnowledgeItem
import com.zoe.wan.base.BaseFragment

/**
 * 作者：Gongsensen
 * 日期：2022/10/12
 * 说明：主页
 */
class FragKnowledge : BaseFragment<FragmentKnowledgeBinding, KnowledgeViewModel>() {

    private var adapter = KnowledgeListAdapter()
    override fun initVariableId(): Int {
        return BR.fragKlVm;
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_knowledge
    }


    override fun initViewData() {
        binding?.fragKnowledgeListView?.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        binding?.fragKnowledgeListView?.adapter = adapter
        //点击进入明细
        adapter.setItemClick(object : AdapterItemListener<KnowledgeItem>() {
            override fun itemClick(item: KnowledgeItem?, position: Int) {
                val intent = Intent(context, KnowledgeDetailActivity::class.java)
                startActivity(intent)
            }

        })

        //数据返回
        viewModel?.knowledgeList?.observe(viewLifecycleOwner) { list ->
            list?.let {
                adapter.setDataList(it)
            }
        }


    }

}
