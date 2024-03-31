package com.zoe.wan.android.activity.knowledge_detail.detail

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.repository.Repository
import com.zoe.wan.android.repository.data.KnowledgeDetailData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class FragDetailViewModel(application: Application) : BaseViewModel(application) {

    val list = SingleLiveEvent<List<KnowledgeDetailData?>?>()

    fun knowledgeDetail(name: String, cid: String) {
        viewModelScope.launch {
            val data = Repository.knowledgeListDetail(cid)
            if (data != null) {
                list.postValue(data.datas)
            }
        }
    }
}
