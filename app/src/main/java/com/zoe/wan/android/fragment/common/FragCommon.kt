package com.zoe.wan.android.fragment.common

import android.content.Intent
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.activity.search.SearchActivity
import com.zoe.wan.android.activity.web.WebActivity
import com.zoe.wan.android.adapter.CommonItemListAdapter
import com.zoe.wan.android.databinding.FragmentCommonBinding
import com.zoe.wan.android.repository.data.CommonItem
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.adapter.AdapterItemListener
import com.zoe.wan.base.view.NoScrollLayoutManager

/**
 * 搜索热点、常用网站
 */
class FragCommon : BaseFragment<FragmentCommonBinding, CommonListViewModel>() {

    private var websiteListAdapter = CommonItemListAdapter()
    private var hotKeyListAdapter = CommonItemListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_common
    }

    override fun getViewModelId(): Int {
        return BR.itemListVm
    }


    override fun initViewData() {
        //点击进入搜索
        binding?.commonSearch?.setOnClickListener {
            val intent = Intent(context, SearchActivity::class.java)
            startActivity(intent)
        }
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


        //点击热点进入搜索页面
        hotKeyListAdapter.setItemListener(object : AdapterItemListener<CommonItem>() {

            override fun itemClick(item: CommonItem?, position: Int) {
                val intent = Intent(context, SearchActivity::class.java)
                intent.putExtra(SearchActivity.Itent_Keyword, item?.name)
                startActivity(intent)
            }

        })

        //点击进入常用网站
        websiteListAdapter.setItemListener(object : AdapterItemListener<CommonItem>() {

            override fun itemClick(item: CommonItem?, position: Int) {
                val intent = Intent(context, WebActivity::class.java)
                intent.putExtra(WebActivity.intentKeyTitle, item?.name)
                intent.putExtra(WebActivity.intentKeyUrl, item?.link)
                startActivity(intent)
            }

        })

    }
}
