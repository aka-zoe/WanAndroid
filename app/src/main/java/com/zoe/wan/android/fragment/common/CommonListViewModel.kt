package com.zoe.wan.android.fragment.common

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.repository.Repository
import com.zoe.wan.android.repository.data.CommonItem
import com.zoe.wan.android.repository.data.CommonItemListData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class CommonListViewModel(application: Application) : BaseViewModel(application) {

    var hotKeyList = SingleLiveEvent<List<CommonItem>?>()
    var commonUseWebsiteList = SingleLiveEvent<List<CommonItem>?>()

    init {
        commonWebSiteList()
        searchHotKeyList()
    }

    private fun commonWebSiteList() {
        viewModelScope.launch {
            val data: CommonItemListData? = Repository.commonUseWebsite()
            if (data?.isNotEmpty() == true) {
                commonUseWebsiteList.postValue(data)
            }
        }
    }

    private fun searchHotKeyList() {
        viewModelScope.launch {
            val data: CommonItemListData? = Repository.searchHotKeyList()
            if (data?.isNotEmpty() == true) {
                hotKeyList.postValue(data)
            }
        }
    }
}
