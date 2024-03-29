package com.zoe.wan.android.repository.data

/**
 * 搜索热词/常用网站列表
 */
class CommonItemListData : ArrayList<CommonItem>()

data class CommonItem(
    //常用网站列表才有这两个字段
    //分类
    val category: String?,
    val icon: String?,

    //---下面是两个接口通用字段：搜索热词/常用网站列表

    val id: Int?,
    //链接
    val link: String?,
    //名称
    val name: String?,
    val order: Int?,
    //是否显示
    val visible: Int?
){
    override fun toString(): String {
        return "CommonItem(category=$category, icon=$icon, id=$id, link=$link, name=$name, order=$order, visible=$visible)"
    }
}

