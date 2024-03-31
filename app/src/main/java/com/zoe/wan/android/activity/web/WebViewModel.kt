package com.zoe.wan.android.activity.web

import android.app.Application
import androidx.databinding.ObservableField
import com.zoe.wan.base.BaseViewModel

class WebViewModel(application: Application) : BaseViewModel(application = application) {
    val webTitle = ObservableField<String>()

}
