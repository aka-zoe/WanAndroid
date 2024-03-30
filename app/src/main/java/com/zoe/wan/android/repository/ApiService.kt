package com.zoe.wan.android.repository

import com.zoe.wan.android.repository.data.CommonItemListData
import com.zoe.wan.android.repository.data.HomeBannerData
import com.zoe.wan.android.repository.data.HomeListData
import com.zoe.wan.android.repository.data.KnowledgeDetailListData
import com.zoe.wan.android.repository.data.KnowledgeListData
import com.zoe.wan.android.repository.data.MyCollectListData
import com.zoe.wan.android.repository.data.SearchResultsData
import com.zoe.wan.android.repository.data.TopHomeListData
import com.zoe.wan.android.repository.data.UserData
import com.zoe.wan.http.ApiAddress
import com.zoe.wan.http.BaseResponse
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {


    /**
     * 获取首页列表数据
     */
    @GET(ApiAddress.Article_List + "{pageCount}/json")
    suspend fun homeList(@Path("pageCount") pageCount: String): BaseResponse<HomeListData>?


    /**
     * 获取首页置顶列表数据
     */
    @GET(ApiAddress.Top_Article_List)
    suspend fun topHomeList(): BaseResponse<TopHomeListData?>?

    /**
     * 获取banner数据
     */
    @GET(ApiAddress.Home_Banner)
    suspend fun homeBanner(): BaseResponse<HomeBannerData>?

    /**
     * 常用网站
     */
    @GET(ApiAddress.Common_Use_Website)
    suspend fun commonUseWebsite(): BaseResponse<CommonItemListData?>?


    /**
     * 搜索热词
     */
    @GET(ApiAddress.Search_Hot_Key)
    suspend fun searchHotKey(): BaseResponse<CommonItemListData?>?

    /**
     * 获取知识体系数据
     */
    @GET(ApiAddress.Knowledge_List)
    suspend fun knowledgeList(): BaseResponse<KnowledgeListData?>?

    /**
     * 获取知识体系数据明细
     */
    @GET(ApiAddress.Knowledge_List_detail + "{pageCount}/json")
    suspend fun knowledgeListDetail(
        @Path("pageCount") pageCount: String = "0",
        @Query("cid") cid: String
    ): BaseResponse<KnowledgeDetailListData?>?


    /**
     * 登录
     */
    @FormUrlEncoded
    @POST(ApiAddress.Login)
    suspend fun login(
        @Field("username") username: String, @Field("password") password: String
    ): BaseResponse<UserData?>?


    /**
     * 注册
     */
    @FormUrlEncoded
    @POST(ApiAddress.Register)
    suspend fun register(
        @Field("username") username: String, @Field("password") password: String, @Field
            ("repassword") repassword: String
    ): BaseResponse<UserData?>?

    /**
     * 登出
     */
    @GET(ApiAddress.Logout)
    suspend fun logout(): BaseResponse<Any>?

    /**
     * 点击收藏（文章列表）
     */
    @POST(ApiAddress.Collect + "{id}/json")
    suspend fun collect(@Path("id") id: String): BaseResponse<Any>?

    /**
     * 取消收藏（文章列表）
     */
    @POST(ApiAddress.Collect_Cancel + "{id}/json")
    suspend fun cancelCollect(@Path("id") id: String): BaseResponse<Any>?


    /**
     * 搜索
     */
    @FormUrlEncoded
    @POST(ApiAddress.Search + "{pageCount}/json")
    suspend fun search(@Path("pageCount") pageCount: String = "0", @Field("k") keyWord: String)
        : BaseResponse<SearchResultsData?>?

    /**
     * 我的收藏：文章列表
     */
    @GET(ApiAddress.My_Collect + "{pageCount}/json")
    suspend fun myCollects(@Path("pageCount") pageCount: String = "0")
        : BaseResponse<MyCollectListData?>?

    /**
     * post body
     */
    @POST("")
    suspend fun loginBody(@Body requestBody: RequestBody): BaseResponse<Any>
}


