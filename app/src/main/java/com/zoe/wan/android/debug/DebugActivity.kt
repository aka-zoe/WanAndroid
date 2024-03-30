package com.zoe.wan.android.debug

import android.os.Bundle
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ActivityDebugBinding
import com.zoe.wan.base.BaseActivity

class DebugActivity : BaseActivity<ActivityDebugBinding, DebugViewModel>() {

    override fun initVariableId(): Int {
        return BR.debugVm
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_debug
    }

    override fun initView() {

    }
}
