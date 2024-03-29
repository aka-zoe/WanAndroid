package com.zoe.wan.android.repository

import com.zoe.wan.android.repository.data.CommonItemListData
import com.zoe.wan.android.repository.data.HomeBannerData
import com.zoe.wan.android.repository.data.HomeListData
import com.zoe.wan.android.repository.data.KnowledgeListData
import com.zoe.wan.android.repository.data.TopHomeListData
import com.zoe.wan.android.repository.data.UserData
import com.zoe.wan.http.BaseResponse
import com.zoe.wan.http.RetrofitClient
import okhttp3.MediaType
import okhttp3.RequestBody


object Repository {
    private const val Success_Code = 0
    private const val Need_login_Code = -1001

    suspend fun getHomeList(pageCount: String): HomeListData? {
        val data: BaseResponse<HomeListData> = getDefaultApi().homeList(pageCount)

        if (data.getErrCode() == Success_Code) {
            return data.getData()
        }
        return null

    }

    /**
     * 获取首页置顶列表数据
     */
    suspend fun getTopHomeList(): TopHomeListData? {
        val data: BaseResponse<TopHomeListData?> = getDefaultApi().topHomeList()

        if (data.getErrCode() == Success_Code) {
            return data.getData()
        }
        return null

    }

    /**
     * 首页banner
     */
    suspend fun getHomeBanner(): HomeBannerData? {
        val data: BaseResponse<HomeBannerData> = getDefaultApi()
            .homeBanner()
        if (data.getErrCode() == Success_Code) {
            return data.getData()
        }
        return null

    }


    /**
     * 常用网站
     */
    suspend fun commonUseWebsite(): CommonItemListData? {
        val data: BaseResponse<CommonItemListData?> = getDefaultApi()
            .commonUseWebsite()
        if (data.getErrCode() == Success_Code) {
            return data.getData()
        }
        return null
    }

    /**
     * 搜索热词
     */
    suspend fun searchHotKeyList(): CommonItemListData? {
        val data: BaseResponse<CommonItemListData?> = getDefaultApi().searchHotKey()
        if (data.getErrCode() == Success_Code) {
            return data.getData()
        }
        return null
    }


    /**
     * 获取知识体系数据
     */
    suspend fun knowledgeList(): KnowledgeListData? {
        val data: BaseResponse<KnowledgeListData?> = getDefaultApi().knowledgeList()
        if (data.getErrCode() == Success_Code) {
            return data.getData()
        }
        return null
    }

    /**
     * 登录
     */
    suspend fun login(username: String, password: String): UserData? {
        val data: BaseResponse<UserData?> = getDefaultApi().login(username, password)
        if (data.getErrCode() == Success_Code) {
            return data.getData()
        }
        return null
    }

    /**
     * 注册
     */
    suspend fun register(username: String, password: String, passwordTwice: String): UserData? {
        val data: BaseResponse<UserData?> = getDefaultApi().register(
            username,
            password,
            passwordTwice
        )
        if (data.getErrCode() == Success_Code) {
            return data.getData()
        }
        return null
    }

    /**
     * 登出
     */
    suspend fun logout(): Boolean {
        val data: BaseResponse<Any> = getDefaultApi().logout()
        return data.getErrCode() == Success_Code
    }

    /**
     * 转换为 form-data
     * @param requestDataMap
     * @return
     */
    private fun generateRequestBody(requestDataMap: Map<String, String>): Map<String, RequestBody> {
        val requestBodyMap: MutableMap<String, RequestBody> = HashMap()
        for (key in requestDataMap.keys) {
            val requestBody = RequestBody.create(
                MediaType.parse("multipart/form-data"),
                requestDataMap[key] ?: ""
            )
            requestBodyMap[key] = requestBody
        }
        return requestBodyMap
    }

    private fun getDefaultApi(): ApiService {
        return RetrofitClient.getInstance().getDefault(ApiService::class.java)
    }

}
