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
        val packageInfo: PackageInfo? =
            application.applicationContext?.packageManager?.getPackageInfo(
                application
                    .packageName, 0
            )

//        AppUtils.getAppVersionName()
        version.set(packageInfo?.versionName ?: "")
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
        binding?.aboutUsHtmlText?.movementMethod = LinkMovementMethod.getInstance()

        binding?.aboutUsBack?.setOnClickListener { finish() }
    }

}


