package com.zoe.wan.android.fragment.home

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.zoe.wan.android.repository.data.HomeListData
import com.zoe.wan.android.repository.Repository
import com.zoe.wan.android.repository.data.HomeBannerData
import com.zoe.wan.android.repository.data.HomeListItemData
import com.zoe.wan.android.repository.data.TopHomeListData
import com.zoe.wan.base.BaseViewModel
import com.zoe.wan.base.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : BaseViewModel(application) {

    var homeListData = SingleLiveEvent<List<HomeListItemData?>?>()
    var bannerData = SingleLiveEvent<HomeBannerData?>()

    init {
        getBannerData()
        getTopList {
            getHomeList(it)
        }
    }

    /**
     * 先获取置顶文章列表，获取成功后获取文章列表
     */
    private fun getTopList(callback: (List<HomeListItemData?>?) -> Unit) {
        viewModelScope.launch {
            var data: TopHomeListData? = Repository.getTopHomeList()

            if (data?.isNotEmpty() == true) {
                callback.invoke(data)
            } else {
                callback.invoke(emptyList())
            }
        }
    }

    private fun getHomeList(topList: List<HomeListItemData?>?) {
        viewModelScope.launch {
            var data: HomeListData? = Repository.getHomeList("0")
            if (data?.datas?.isNotEmpty() == true) {
                //置顶列表与文章列表数据类型是一致的，将两个list组合在一起
                var list = (topList ?: emptyList()) + (data.datas ?: emptyList())
                homeListData.postValue(list)
            } else {
                homeListData.postValue(topList)
            }
        }
    }

    private fun getBannerData() {
        viewModelScope.launch {
            var data: HomeBannerData? = Repository.getHomeBanner()
            bannerData.postValue(data)
        }
    }

    //点击收藏
    fun collect(id: String, callback: (state: Boolean) -> Unit) {
        viewModelScope.launch {
            val data = Repository.collect(id)
            callback.invoke(data)
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
