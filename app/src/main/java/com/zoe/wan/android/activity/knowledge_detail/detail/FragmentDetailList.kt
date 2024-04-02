package com.zoe.wan.android.activity.knowledge_detail.detail

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.R
import com.zoe.wan.android.BR
import com.zoe.wan.android.activity.web.WebActivity
import com.zoe.wan.android.adapter.DetailListAdapter
import com.zoe.wan.android.databinding.FragmentKnowledgeDetailBinding
import com.zoe.wan.android.repository.data.KnowledgeDetailData
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.adapter.AdapterItemListener

/**
 * 知识体系明细列表数据页面
 */
class FragmentDetailList(val name: String, val cid: String) :
    BaseFragment<FragmentKnowledgeDetailBinding,
        FragDetailViewModel>() {

    private val adapter = DetailListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_knowledge_detail
    }

    override fun getViewModelId(): Int {
        return BR.fragDetailVm
    }

    override fun initViewData() {
        binding?.detailListView?.layoutManager = LinearLayoutManager(context)
        binding?.detailListView?.adapter = adapter


        viewModel?.knowledgeDetail(name, cid)

        viewModel?.list?.observe(viewLifecycleOwner) {
            adapter.setDataList(it)
        }

        adapter.setItemListener(object:AdapterItemListener<KnowledgeDetailData>(){
            override fun itemClick(item: KnowledgeDetailData?, position: Int) {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra(WebActivity.intentKeyTitle, item?.title)
                intent.putExtra(WebActivity.intentKeyUrl, item?.link)
                startActivity(intent)
            }
        })
    }
}

