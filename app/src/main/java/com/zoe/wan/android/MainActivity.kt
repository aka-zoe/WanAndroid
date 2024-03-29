package com.zoe.wan.android

import android.os.Bundle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.zoe.wan.android.common.CommonItemListAdapter
import com.zoe.wan.android.databinding.FragmentItemListBinding
import com.zoe.wan.android.fragment.item.vm.ItemListViewModel
import com.zoe.wan.base.BaseActivity

class MainActivity : BaseActivity<FragmentItemListBinding, ItemListViewModel>() {

    private var websiteListAdapter = CommonItemListAdapter()
    private var hotKeyListAdapter = CommonItemListAdapter()

    override fun initVariableId(): Int {
        return BR.itemListVm
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.fragment_item_list
    }

    override fun initView() {
        initListView()

        viewModel?.hotKeyList?.observe(this) {
            it?.forEach { item ->
                LogUtils.d("initViewObservable hotKeyList item = ${item.toString()}")
            }
            hotKeyListAdapter.setDataList(it ?: emptyList())
        }

        viewModel?.commonUseWebsiteList?.observe(this) {
            it?.forEach { item ->
                LogUtils.d("initViewObservable commonUseWebsiteList item = ${item.toString()}")
            }
            websiteListAdapter.setDataList(it ?: emptyList())
        }
    }

    private fun initListView() {
        binding?.commonWebSiteListView?.layoutManager = StaggeredGridLayoutManager(
            3,
            StaggeredGridLayoutManager.VERTICAL
        )
        binding?.commonWebSiteListView?.adapter = websiteListAdapter


        binding?.searchHotKeyListView?.layoutManager = StaggeredGridLayoutManager(
            3,
            StaggeredGridLayoutManager.VERTICAL
        )
        binding?.searchHotKeyListView?.adapter = hotKeyListAdapter
    }

}
