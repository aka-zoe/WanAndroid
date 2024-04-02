package com.zoe.wan.android.activity.about

import android.text.method.LinkMovementMethod
import com.zoe.wan.android.databinding.ActivityAboutUsBinding
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.base.BaseActivity

/**
 * 关于我们
 */
class AboutUsActivity : BaseActivity<ActivityAboutUsBinding, AboutUsViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.activity_about_us
    }

    override fun getViewModelId(): Int {
        return BR.aboutVm
    }

    override fun initViewData() {
        //textView显示html文本有link链接，点击链接自动跳转到外部浏览器打开
        binding?.aboutUsHtmlText?.movementMethod = LinkMovementMethod.getInstance()

        binding?.aboutUsBack?.setOnClickListener { finish() }
    }

}


