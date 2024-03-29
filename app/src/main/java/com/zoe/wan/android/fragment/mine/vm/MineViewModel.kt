package com.zoe.wan.android.fragment.mine.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.repository.Repository
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class MineViewModel(application: Application) : BaseViewModel(application) {
    val logoutState = SingleLiveEvent<Boolean>()
    fun logOut() {
        viewModelScope.launch {
            logoutState.postValue(Repository.logout())
        }
    }
}
