package com.zoe.wan.base.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.viewpager2.adapter.FragmentStateAdapter

/**
 * 作者：Gongsensen
 * 日期：2022/10/12
 * 说明：首页viewpager2适配器
 */
class Pager2Adapter(fa: FragmentActivity) : FragmentStateAdapter(fa) {
    private val itemFragList = mutableListOf<Fragment>()

    override fun getItemCount() = itemFragList.size

    override fun createFragment(position: Int) = itemFragList[position]

    fun setData(newListData: List<Fragment>) {
        //使用DiffUtil更新数据
        val callback = PageDiffUtil(itemFragList, newListData)
        val difResult = DiffUtil.calculateDiff(callback)
        itemFragList.clear()
        itemFragList.addAll(newListData)
        difResult.dispatchUpdatesTo(this)
    }
}
