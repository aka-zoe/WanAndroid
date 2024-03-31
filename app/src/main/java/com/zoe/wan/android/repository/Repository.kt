package com.zoe.wan.android.repository

import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.repository.data.CommonItemListData
import com.zoe.wan.android.repository.data.HomeBannerData
import com.zoe.wan.android.repository.data.HomeListData
import com.zoe.wan.android.repository.data.KnowledgeDetailListData
import com.zoe.wan.android.repository.data.KnowledgeListData
import com.zoe.wan.android.repository.data.MyCollectListData
import com.zoe.wan.android.repository.data.SearchResultsData
import com.zoe.wan.android.repository.data.TopHomeListData
import com.zoe.wan.android.repository.data.UserData
import com.zoe.wan.http.BaseResponse
import com.zoe.wan.http.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


object Repository {
    private const val Success_Code = 0
    private const val Need_login_Code = -1001

    suspend fun getHomeList(pageCount: String): HomeListData? {
        val data: BaseResponse<HomeListData>? = getDefaultApi().homeList(pageCount)
        return responseCall(data)

    }

    /**
     * 获取首页置顶列表数据
     */
    suspend fun getTopHomeList(): TopHomeListData? {
        val data: BaseResponse<TopHomeListData?>? = getDefaultApi().topHomeList()

        return responseCall(data)

    }

    /**
     * 首页banner
     */
    suspend fun getHomeBanner(): HomeBannerData? {
        val data: BaseResponse<HomeBannerData>? = getDefaultApi()
            .homeBanner()
        return responseCall(data)

    }


    /**
     * 常用网站
     */
    suspend fun commonUseWebsite(): CommonItemListData? {
        val data: BaseResponse<CommonItemListData?>? = getDefaultApi()
            .commonUseWebsite()
        return responseCall(data)
    }

    /**
     * 搜索热词
     */
    suspend fun searchHotKeyList(): CommonItemListData? {
        val data: BaseResponse<CommonItemListData?>? = getDefaultApi().searchHotKey()
        return responseCall(data)
    }


    /**
     * 获取知识体系数据
     */
    suspend fun knowledgeList(): KnowledgeListData? {
        val data: BaseResponse<KnowledgeListData?>? = getDefaultApi().knowledgeList()
        return responseCall(data)
    }

    /**
     * 获取知识体系明细数据
     */
    suspend fun knowledgeListDetail(cid: String): KnowledgeDetailListData? {
        val data: BaseResponse<KnowledgeDetailListData?>? = getDefaultApi().knowledgeListDetail(cid = cid)
        return responseCall(data)
    }

    /**
     * 登录
     */
    suspend fun login(username: String, password: String): UserData? {
        val data: BaseResponse<UserData?>? = getDefaultApi().login(username, password)
        return responseCall(data)
    }

    /**
     * 注册
     */
    suspend fun register(username: String, password: String, passwordTwice: String): UserData? {
        val data: BaseResponse<UserData?>? = getDefaultApi().register(
            username,
            password,
            passwordTwice
        )
        return responseCall(data)

    }

    /**
     * 登出
     */
    suspend fun logout(): Boolean {
        val data: BaseResponse<Any>? = getDefaultApi().logout()
        return responseNoDataCall(data)
    }

    /**
     * 点击收藏（文章列表）
     */
    suspend fun collect(id: String): Boolean {
        val data: BaseResponse<Any>? = getDefaultApi().collect(id)
        return responseNoDataCall(data)
    }

    /**
     * 取消收藏（文章列表）
     */
    suspend fun cancelCollect(id: String): Boolean {
        val data: BaseResponse<Any>? = getDefaultApi().cancelCollect(id)
        return responseNoDataCall(data)
    }

    /**
     * 搜索
     */
    suspend fun search(pageCount: String = "0", keyWord: String): SearchResultsData? {
        val data: BaseResponse<SearchResultsData?>? = getDefaultApi().search(
            pageCount = pageCount, keyWord = keyWord
        )
        return responseCall(data)
    }

    /**
     * 我的收藏：文章列表
     */
    suspend fun myCollects(pageCount: String = "0"): MyCollectListData? {
        val data: BaseResponse<MyCollectListData?>? = getDefaultApi().myCollects(pageCount)
        return responseCall(data)
    }

    /**
     * 返回值处理(无data返回情况)
     */
    private fun responseNoDataCall(
        response: BaseResponse<Any>?,
        needLogin: (() -> Unit?)? = null
    ): Boolean {
        if (response == null) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort("请求异常")
            }
            return false
        }

        if (response.getErrCode() == Success_Code) {
            return true
        } else if (response.getErrCode() == Need_login_Code) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            needLogin?.invoke()
            return false
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            return false
        }
    }

    /**
     * 返回值处理
     */
    private fun <T> responseCall(
        response: BaseResponse<T>?,
        needLogin: (() -> Unit?)? = null
    ): T? {
        if (response == null) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort("请求异常")
            }
            return null
        }

        if (response.getErrCode() == Success_Code) {
            return response.getData()
        } else if (response.getErrCode() == Need_login_Code) {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            needLogin?.invoke()
            return null
        } else {
            GlobalScope.launch(Dispatchers.Main) {
                ToastUtils.showShort(response.getErrMsg() ?: "")
            }
            return null
        }
    }

    private fun getDefaultApi(): ApiService {
        return RetrofitClient.getInstance().getDefault(ApiService::class.java)
    }

}
