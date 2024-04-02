package com.zoe.wan.android.fragment.home

import androidx.recyclerview.widget.LinearLayoutManager
import com.zoe.wan.android.R
import com.zoe.wan.android.BR
import com.zoe.wan.android.adapter.HomeListAdapter
import com.zoe.wan.android.databinding.FragmentHomeBinding
import com.zoe.wan.android.repository.data.HomeListItemData
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.base.adapter.AdapterCollectListener

/**
 * 首页
 */
class FragHome : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val adapter: HomeListAdapter = HomeListAdapter()

    override fun getLayoutId(): Int {
        return R.layout.fragment_home
    }

    override fun getViewModelId(): Int {
        return BR.fragHomeVm
    }

    override fun initViewData() {
        //初始化lieview
        initListView()

        //数据回调
        viewModel?.homeListData?.observe(viewLifecycleOwner) { list ->
            if (list != null && list?.isNotEmpty() == true) {
                //给适配器添加数据
                binding?.homeTabListView?.post {
                    adapter.setDataList(list)
                }

            }

        }

        viewModel?.bannerData?.observe(viewLifecycleOwner) { bannerData ->
            if (bannerData != null) {
                binding?.homeTabListView?.post {
                    adapter.setBannerData(bannerData)
                }

            }
        }
    }


    private fun initListView() {
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding?.homeTabListView?.layoutManager = manager
        binding?.homeTabListView?.adapter = adapter

        adapter.setCollectListener(object : AdapterCollectListener<HomeListItemData?>() {
            override fun collect(position: Int, id: String) {
                viewModel?.collect(id) {
                    if (it) {
                        //收藏状态刷新
                        adapter.notifyCollectChange(position, true)
                    }
                }
            }

            override fun cancelCollect(position: Int, id: String) {
                viewModel?.cancelCollect(id) {
                    if (it) {
                        //收藏状态刷新
                        adapter.notifyCollectChange(position, false)
                    }
                }
            }

        })
        //添加分割线
//        binding?.homeTabListView?.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                LinearLayoutManager.VERTICAL
//            )
//        )
    }

}
