package com.zoe.wan.android.activity.about

import android.app.Application
import android.content.pm.PackageInfo
import android.os.Bundle
import android.text.Html
import android.text.Spanned
import android.text.method.LinkMovementMethod
import androidx.databinding.ObservableField
import com.zoe.wan.android.databinding.ActivityAboutUsBinding
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.android.BR
import com.zoe.wan.android.R

class AboutUsViewModel(application: Application) : BaseViewModel(application) {
    val version = ObservableField<String>()
    val htmlText = ObservableField<Spanned>()

    init {
        //通过packageManager获取PackageInfo
        val packageInfo: PackageInfo? =
            application.applicationContext?.packageManager?.getPackageInfo(
                application
                    .packageName, 0
            )

        //显示版本号
        version.set(packageInfo?.versionName ?: "")

        //textView显示html文本
        htmlText.set(Html.fromHtml(application.applicationContext.getString(R.string.about_content)))
    }
}

class AboutUsActivity : BaseActivity<ActivityAboutUsBinding, AboutUsViewModel>() {
    override fun initVariableId(): Int {
        return BR.aboutVm
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_about_us
    }

    override fun initView() {
        //textView显示html文本有link链接，点击链接自动跳转到外部浏览器打开
        binding?.aboutUsHtmlText?.movementMethod = LinkMovementMethod.getInstance()

        binding?.aboutUsBack?.setOnClickListener { finish() }
    }

}


