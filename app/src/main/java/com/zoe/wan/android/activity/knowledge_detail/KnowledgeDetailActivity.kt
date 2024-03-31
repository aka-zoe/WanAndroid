package com.zoe.wan.android.activity.knowledge_detail

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.zoe.wan.android.R
import com.zoe.wan.android.BR
import com.zoe.wan.android.activity.knowledge_detail.detail.FragmentDetailList
import com.zoe.wan.android.databinding.ActivityKnowledgeDetailBinding
import com.zoe.wan.android.databinding.TabItemBinding
import com.zoe.wan.android.repository.data.DetailIntentList
import com.zoe.wan.android.repository.data.DetailTabIntentData
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.adapter.Pager2Adapter

class KnowledgeDetailActivity : BaseActivity<ActivityKnowledgeDetailBinding,
    KnowledgeDetailViewModel>() {

    companion object {
        const val INTENT_TABS_LIST = "INTENT_TABS_LIST"
        const val INTENT_BUNDLE = "INTENT_BUNDLE"
    }

    override fun initVariableId(): Int {
        return BR.detailVm
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_knowledge_detail
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun initView() {

        binding?.knowledgeDetailBack?.setOnClickListener {
            finish()
        }
        //获取intent数据
        var intentList: DetailIntentList? = null
        val bundle: Bundle? = intent.extras
        bundle?.let {
            intentList = it.getParcelable(INTENT_TABS_LIST, DetailIntentList::class.java)
        }

        //先初始化ViewPager2
        initPageModule(intentList?.list ?: emptyList())

        //tablayout与viewpager2绑定
        TabLayoutMediator(
            binding?.detailTabLayout!!,
            binding?.detailViewPager2!!,
            true,
            true
        ) { tab, position ->
            val binding = DataBindingUtil.inflate<TabItemBinding>(
                layoutInflater,
                R.layout.tab_item,
                null,
                false
            )
            binding.tabItemTitle.text = intentList?.list?.get(position)?.name
            tab.customView = binding.root
        }.attach()


    }

    private fun initPageModule(list: List<DetailTabIntentData>) {
        val pageFragList = mutableListOf<Fragment>()
        list.forEach { data ->
            pageFragList.add(FragmentDetailList(name = data.name ?: "", cid = data.id))
        }
        val pageAdapter = Pager2Adapter(this)
        pageAdapter.setData(pageFragList)
        //默认不做预加载Fragment
        binding?.detailViewPager2?.offscreenPageLimit = ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT
        binding?.detailViewPager2?.adapter = pageAdapter
    }

}
