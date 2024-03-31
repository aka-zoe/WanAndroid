package com.zoe.wan.android.activity.collect

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.repository.Repository
import com.zoe.wan.android.repository.data.CollectListData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class MyCollectViewModel(application: Application) : BaseViewModel(application) {

    val collectList = SingleLiveEvent<List<CollectListData?>>()

    init {
        myCollects()
    }
    private fun myCollects() {
        viewModelScope.launch {
            val data = Repository.myCollects()
            data?.datas?.let { list ->
                if (list.isNotEmpty()) {
                    collectList.postValue(list)
                }
            }
        }
    }

    //取消收藏
    fun cancelCollect(id: String, callback: (state: Boolean) -> Unit) {
        viewModelScope.launch {
            val data = Repository.cancelCollect(id)
            callback.invoke(data)
        }
    }
}
