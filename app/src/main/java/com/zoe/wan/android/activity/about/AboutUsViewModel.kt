package com.zoe.wan.android.activity.about

import android.app.Application
import android.content.pm.PackageInfo
import android.text.Html
import android.text.Spanned
import androidx.databinding.ObservableField
import com.zoe.wan.android.R
import com.zoe.wan.base.BaseViewModel

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
