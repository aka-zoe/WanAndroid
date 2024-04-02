package com.zoe.wan.android.activity.search

import android.content.Intent
import android.view.KeyEvent
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.TextView.OnEditorActionListener
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.KeyboardUtils
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ActivitySearchBinding
import com.zoe.wan.android.BR
import com.zoe.wan.android.activity.web.WebActivity
import com.zoe.wan.android.adapter.SearchResultsAdapter
import com.zoe.wan.android.repository.data.SearchData
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.adapter.AdapterItemListener

class SearchActivity : BaseActivity<ActivitySearchBinding, SearchViewModel>() {

    private val adapter = SearchResultsAdapter()

    companion object {
        const val Itent_Keyword = "Itent_Keyword"
    }


    override fun getLayoutId(): Int {
        return R.layout.activity_search
    }

    override fun getViewModelId(): Int {
        return BR.searchVm
    }

    override fun initViewData() {
        //获取上一级页面可能携带来的搜索条件并搜索
        val keyword = intent.getStringExtra(Itent_Keyword)
        viewModel?.searchInput?.set(keyword)
        viewModel?.search()

        binding?.searchBack?.setOnClickListener {
            finish()
        }

        binding?.searchCancel?.setOnClickListener {
            binding?.searchInput?.clearFocus()
            binding?.searchInput?.setText("")
            //关闭软键盘
            KeyboardUtils.hideSoftInput(this@SearchActivity)
        }

        //监听软键盘搜索按钮事件
        binding?.searchInput?.setOnEditorActionListener(object : OnEditorActionListener {
            override fun onEditorAction(p0: TextView?, actionId: Int, p2: KeyEvent?): Boolean {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //按下搜索键开始搜索
                    viewModel?.search()//关闭软键盘
                    KeyboardUtils.hideSoftInput(this@SearchActivity)
                }

                return true
            }

        })

        initListView()
    }


    private fun initListView() {
        binding?.searchListView?.layoutManager = LinearLayoutManager(this@SearchActivity)
        binding?.searchListView?.adapter = adapter
        binding?.searchListView?.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )
        adapter.setItemListener(object : AdapterItemListener<SearchData>() {
            override fun itemClick(item: SearchData?, position: Int) {
                val intent = Intent(this@SearchActivity, WebActivity::class.java)
                intent.putExtra(WebActivity.intentKeyTitle, item?.title)
                intent.putExtra(WebActivity.intentKeyUrl, item?.link)
                startActivity(intent)
            }

        })

        viewModel?.results?.observe(this) { list ->
            if (list != null) {
                adapter.setDataList(list)
            }
        }

    }

}
