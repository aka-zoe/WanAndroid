package com.zoe.wan.android.activity.home

import android.graphics.BitmapFactory
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.blankj.utilcode.util.LogUtils
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ActivityTabBinding
import com.zoe.wan.android.fragment.home.FragHome
import com.zoe.wan.android.fragment.common.FragCommon
import com.zoe.wan.android.fragment.knowledge.FragKnowledge
import com.zoe.wan.android.fragment.mine.FragMine
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.adapter.Pager2Adapter
import com.zoe.wan.base.tab.NavigationBottomBar

class TabActivity : BaseActivity<ActivityTabBinding, TabViewModel>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_tab
    }

    override fun getViewModelId(): Int {
        return BR.homeVm
    }

    override fun initViewData() {
        initPageModule()
        val icons = arrayOf(
            BitmapFactory.decodeResource(resources, R.drawable.icon_home_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_hot_key_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_knowledge_selected),
            BitmapFactory.decodeResource(resources, R.drawable.icon_personal_selected)
        )
        val icons2 = arrayOf(
            BitmapFactory.decodeResource(resources, R.drawable.icon_home_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_hot_key_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_knowledge_grey),
            BitmapFactory.decodeResource(resources, R.drawable.icon_personal_grey)
        )
        val tabTexts = arrayOf("首页", "热点", "体系", "个人")
        binding?.tabBottomBar?.let {
            it.setSelectedIcons(icons.toList())
                .setUnselectIcons(icons2.toList())
                .setTabText(tabTexts.toList())
                .setupViewpager(binding?.tabViewPager2)
                .start()
        }
        binding?.tabBottomBar?.registerTabClickListener(object :
            NavigationBottomBar.OnBottomTabClickListener {
            override fun tabClick(position: Int) {
                LogUtils.d("registerTabClickListener position=$position")
            }
        })
    }

    private fun initPageModule() {
        val pageFragList = mutableListOf<Fragment>()
        pageFragList.add(FragHome())
        pageFragList.add(FragCommon())
        pageFragList.add(FragKnowledge())
        pageFragList.add(FragMine())

        val pageAdapter = Pager2Adapter(this)
        pageAdapter.setData(pageFragList)
        //默认不做预加载Fragment
        binding?.tabViewPager2?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding?.tabViewPager2?.adapter = pageAdapter
    }

}
