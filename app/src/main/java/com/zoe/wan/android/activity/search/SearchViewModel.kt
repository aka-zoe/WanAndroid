package com.zoe.wan.android.activity.search

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.repository.Repository
import com.zoe.wan.android.repository.data.SearchData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class SearchViewModel(application: Application) : BaseViewModel(application) {

    val searchInput = ObservableField<String>()
    val results = SingleLiveEvent<List<SearchData?>?>()

    fun search() {
        if (searchInput.get()?.isEmpty() == true) {
            return
        }
        viewModelScope.launch {
            val result = Repository.search(keyWord = searchInput.get() ?: "")
            if (result?.datas == null || result.datas.isEmpty()) {
                results.postValue(emptyList())
                return@launch
            }
            results.postValue(result.datas)
        }
    }

}
