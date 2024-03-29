package com.zoe.wan.android.activity.web

import android.os.Bundle
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ActivityWebBinding
import com.zoe.wan.base.BaseActivity

class WebActivity : BaseActivity<ActivityWebBinding, WebViewModel>() {
    companion object {
        const val intentKeyUrl = "intentKeyUrl"
        const val intentKeyTitle = "intentKeyTitle"

    }

    override fun initVariableId(): Int {
        return BR.webVM
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_web
    }

    override fun initView() {


        val loadUrl = intent.getStringExtra(intentKeyUrl)
        val webTitle = intent.getStringExtra(intentKeyTitle)

        binding?.webView?.loadUrl(loadUrl ?: "")

    }

}
