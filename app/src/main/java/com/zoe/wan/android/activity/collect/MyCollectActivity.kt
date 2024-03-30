package com.zoe.wan.android.activity.collect

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.databinding.ActivityCollectBinding
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.common.AdapterItemListener
import com.zoe.wan.android.common.MyCollectListAdapter
import com.zoe.wan.android.repository.data.CollectListData

/**
 * 我的收藏
 */
class MyCollectActivity : BaseActivity<ActivityCollectBinding, MyCollectViewModel>() {
    private val adapter = MyCollectListAdapter()
    override fun initVariableId(): Int {
        return BR.myCollectVm
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_collect
    }

    override fun initView() {
        binding?.collectListView?.layoutManager = LinearLayoutManager(this)
        binding?.collectListView?.adapter = adapter

        viewModel?.collectList?.observe(this) {
            it?.let { list ->
                adapter.setDataList(list)
            }
        }

        adapter.setItemClick(object:AdapterItemListener<CollectListData>(){
            override fun itemClick(item: CollectListData?, position: Int) {

            }
        })
    }
}
