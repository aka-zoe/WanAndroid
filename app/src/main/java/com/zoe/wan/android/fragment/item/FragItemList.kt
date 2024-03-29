package com.zoe.wan.android.fragment.item

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.common.CommonItemListAdapter
import com.zoe.wan.android.databinding.FragmentItemListBinding
import com.zoe.wan.android.fragment.item.vm.ItemListViewModel
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.view.NoScrollLayoutManager

class FragItemList : BaseFragment<FragmentItemListBinding, ItemListViewModel>() {

    private var websiteListAdapter = CommonItemListAdapter()
    private var hotKeyListAdapter = CommonItemListAdapter()
    override fun initVariableId(): Int {
        return BR.itemListVm
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_item_list
    }


    override fun initViewData() {
        initListView()

        viewModel?.hotKeyList?.observe(viewLifecycleOwner) {
            hotKeyListAdapter.setDataList(it ?: emptyList())
        }

        viewModel?.commonUseWebsiteList?.observe(viewLifecycleOwner) {
            websiteListAdapter.setDataList(it ?: emptyList())
        }
    }


    private fun initListView() {
        var websiteLayoutManager = context?.let { NoScrollLayoutManager(it, 3) }
        binding?.commonWebSiteListView?.layoutManager = websiteLayoutManager
        binding?.commonWebSiteListView?.adapter = websiteListAdapter


        var hotKeysLayoutManager = context?.let { NoScrollLayoutManager(it, 3) }
        binding?.searchHotKeyListView?.layoutManager = hotKeysLayoutManager
        binding?.searchHotKeyListView?.adapter = hotKeyListAdapter
    }
}
