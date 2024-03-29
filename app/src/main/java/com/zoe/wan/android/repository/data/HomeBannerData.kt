package com.zoe.wan.android.repository.data

class HomeBannerData : ArrayList<HomeBannerDataItem>()

data class HomeBannerDataItem(
    //说明
    val desc: String?,
    val id: Int?,
    //图片路径
    val imagePath: String?,
    //是否显示
    val isVisible: Int?,
    val order: Int?,
    //标题
    val title: String?,
    val type: Int?,
    //链接
    val url: String?
)
