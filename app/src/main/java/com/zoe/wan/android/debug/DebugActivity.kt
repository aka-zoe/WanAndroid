package com.zoe.wan.android.debug

import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.databinding.ActivityDebugBinding
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.adapter.KnowledgeListAdapter
import com.zoe.wan.base.BaseActivity

class DebugActivity : BaseActivity<ActivityDebugBinding, DebugViewModel>() {
    private var adapter = KnowledgeListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.activity_debug
    }

    override fun getViewModelId(): Int {
        return BR.debugVm
    }

    override fun initViewData() {
        binding?.debutListView?.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL, false
        )

        binding?.debutListView?.adapter = adapter

        //数据返回
        viewModel?.knowledgeList?.observe(this) { list ->
            list?.let {
                adapter.setDataList(it)
            }
        }
    }

}
