package com.zoe.wan.android.activity.collect

import android.content.Intent
import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.databinding.ActivityCollectBinding
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.activity.web.WebActivity
import com.zoe.wan.android.adapter.MyCollectListAdapter
import com.zoe.wan.android.repository.data.CollectListData
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.adapter.AdapterCollectListener
import com.zoe.wan.base.adapter.AdapterItemListener

/**
 * 我的收藏
 */
class MyCollectActivity : BaseActivity<ActivityCollectBinding, MyCollectViewModel>() {
    private val adapter = MyCollectListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.activity_collect
    }

    override fun getViewModelId(): Int {
        return BR.myCollectVm
    }

    override fun initViewData() {
        binding?.collectBack?.setOnClickListener {
            finish()
        }
        binding?.collectListView?.layoutManager = LinearLayoutManager(this)
        binding?.collectListView?.adapter = adapter

        viewModel?.collectList?.observe(this) {
            it?.let { list ->
                adapter.setDataList(list)
            }
        }



        adapter.setItemListener(object : AdapterItemListener<CollectListData>() {
            override fun itemClick(item: CollectListData?, position: Int) {
                val intent = Intent(this@MyCollectActivity, WebActivity::class.java)
                intent.putExtra(WebActivity.intentKeyTitle, item?.title)
                intent.putExtra(WebActivity.intentKeyUrl, item?.link)
                startActivity(intent)
            }
        })

        adapter.setCollectListener(object : AdapterCollectListener<CollectListData?>() {
            override fun collect(position: Int, id: String) {}

            override fun cancelCollect(position: Int, id: String) {
                viewModel?.cancelCollect(id) {
                    if (it) {
                        //收藏状态刷新
                        adapter.notifyCollectChange(position, false)
                    }
                }
            }
        })
    }
}
