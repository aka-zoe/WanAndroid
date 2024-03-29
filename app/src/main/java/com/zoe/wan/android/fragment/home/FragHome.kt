package com.zoe.wan.android.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.LogUtils
import com.zoe.wan.android.R
import com.zoe.wan.android.BR
import com.zoe.wan.android.common.HomeListAdapter
import com.zoe.wan.android.databinding.FragmentHomeBinding
import com.zoe.wan.android.fragment.home.vm.HomeViewModel
import com.zoe.wan.base.BaseFragment

/**
 * 作者：Gongsensen
 * 日期：2022/10/12
 * 说明：主页
 */
class FragHome : BaseFragment<FragmentHomeBinding, HomeViewModel>() {
    private val adapter: HomeListAdapter = HomeListAdapter()

    override fun initVariableId(): Int {
        return BR.fragHomeVm;
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_home
    }

    override fun initViewData() {
        //初始化lieview
        initListView()

        //数据回调
        viewModel?.homeListData?.observe(viewLifecycleOwner) { list ->
            LogUtils.d("livedata = ${list?.get(0)?.author}")
            if (list != null && list?.isNotEmpty() == true) {
                LogUtils.d("查看当前线程 homeListData ${Thread.currentThread().id}")
                //给适配器添加数据
                binding?.homeTabListView?.post {
                    LogUtils.d("查看当前线程 post ${Thread.currentThread().id}")
                    adapter.setListData(list)
                }

            }

        }

        viewModel?.bannerData?.observe(viewLifecycleOwner) { bannerData ->
            if (bannerData != null) {
                LogUtils.d("查看当前线程 bannerData ${Thread.currentThread().id}")
                binding?.homeTabListView?.post {
                    LogUtils.d("查看当前线程 post ${Thread.currentThread().id}")
                    adapter.setBannerData(bannerData)
                }

            }
        }
    }


    private fun initListView() {
        LogUtils.d("查看当前线程 initListView ${Thread.currentThread().id}")
        val manager = LinearLayoutManager(context)
        manager.orientation = LinearLayoutManager.VERTICAL
        binding?.homeTabListView?.layoutManager = manager
        binding?.homeTabListView?.adapter = adapter
        //添加分割线
//        binding?.homeTabListView?.addItemDecoration(
//            DividerItemDecoration(
//                context,
//                LinearLayoutManager.VERTICAL
//            )
//        )
    }

}
