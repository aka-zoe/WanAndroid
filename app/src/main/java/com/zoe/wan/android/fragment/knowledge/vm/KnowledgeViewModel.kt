package com.zoe.wan.android.fragment.knowledge.vm

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.repository.Repository
import com.zoe.wan.android.repository.data.KnowledgeItem
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class KnowledgeViewModel(application: Application) : BaseViewModel(application) {

    val knowledgeList = SingleLiveEvent<List<KnowledgeItem?>?>()

    init {
        knowledgeList()
    }

    private fun knowledgeList() {
        viewModelScope.launch {
            val data = Repository.knowledgeList()
            knowledgeList.postValue(data ?: emptyList())
        }
    }
}
