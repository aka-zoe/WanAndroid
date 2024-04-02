package com.zoe.wan.android.fragment.knowledge

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.zoe.wan.android.R
import com.zoe.wan.android.BR
import com.zoe.wan.android.activity.knowledge_detail.KnowledgeDetailActivity
import com.zoe.wan.android.adapter.KnowledgeListAdapter
import com.zoe.wan.android.databinding.FragmentKnowledgeBinding
import com.zoe.wan.android.repository.data.DetailIntentList
import com.zoe.wan.android.repository.data.DetailTabIntentData
import com.zoe.wan.android.repository.data.KnowledgeItem
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.adapter.AdapterItemListener

/**
 * 知识体系
 */
class FragKnowledge : BaseFragment<FragmentKnowledgeBinding, KnowledgeViewModel>() {

    private var adapter = KnowledgeListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_knowledge
    }

    override fun getViewModelId(): Int {
        return BR.fragKlVm
    }


    override fun initViewData() {
        binding?.fragKnowledgeListView?.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.VERTICAL, false
        )

        binding?.fragKnowledgeListView?.adapter = adapter
        //点击进入明细
        adapter.setItemListener(object : AdapterItemListener<KnowledgeItem>() {
            override fun itemClick(item: KnowledgeItem?, position: Int) {

                //初始化传递的数据
                val intentList: DetailIntentList
                val tabList = mutableListOf<DetailTabIntentData>()

                item?.let {
                    it.children?.forEach { c ->
                        tabList.add(DetailTabIntentData(c?.name ?: "", "${c?.id}"))
                        LogUtils.d("知识体系：name=${c?.name} id=${c?.id}")
                    }
                }
                intentList = DetailIntentList(tabList)

                //存到bundle里通过intent传递过去
                val intent = Intent(context, KnowledgeDetailActivity::class.java)
                val bundle = Bundle()
                bundle.putParcelable(KnowledgeDetailActivity.INTENT_TABS_LIST, intentList)
                intent.putExtras(bundle)
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
