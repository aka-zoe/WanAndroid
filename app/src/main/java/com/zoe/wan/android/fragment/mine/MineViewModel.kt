package com.zoe.wan.android.fragment.mine

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.blankj.utilcode.util.SPUtils
import com.zoe.wan.android.common.Constants
import com.zoe.wan.android.repository.Repository
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class MineViewModel(application: Application) : BaseViewModel(application) {
    val logoutState = SingleLiveEvent<Boolean>()
    val loginState = ObservableField<Boolean>(false)
    val username = ObservableField<String>()

    init {
        val name = SPUtils.getInstance().getString(Constants.SP_USER_NAME)
        if (name.isNotEmpty()) {
            loginState.set(true)
            username.set(name)
        } else {
            loginState.set(false)
            username.set("未登录")
        }
    }

    fun logOut() {
        viewModelScope.launch {
            val data = Repository.logout()
            if (data) {
                SPUtils.getInstance().clear()
                loginState.set(false)
                username.set("未登录")
            }
            logoutState.postValue(data)
        }
    }
}
