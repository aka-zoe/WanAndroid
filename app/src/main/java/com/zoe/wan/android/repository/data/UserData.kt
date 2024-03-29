package com.zoe.wan.android.repository.data

/**
 * 注册或者登录后返回的用户数据
 */
data class UserData(
    //是否为管理员
    val admin: Boolean?,
    val chapterTops: List<Any?>?,
    val coinCount: Int?,
    val collectIds: List<Any?>?,
    val email: String?,
    val icon: String?,
    val id: Int?,
    val nickname: String?,
    val password: String?,
    val publicName: String?,
    val token: String?,
    val type: Int?,
    val username: String?
)
