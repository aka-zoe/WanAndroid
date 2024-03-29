package com.zoe.wan.android.repository.data

data class HomeListItemData(
    val adminAdd: Boolean?,
    val apkLink: String?,
    val audit: Int?,
    val author: String?,
    val canEdit: Boolean?,
    val chapterId: Int?,
    val chapterName: String?,
    //是否收藏
    var collect: Boolean?,
    val courseId: Int?,
    val desc: String?,
    val descMd: String?,
    val envelopePic: String?,
    val fresh: Boolean?,
    val host: String?,
    val id: Int?,
    val isAdminAdd: Boolean?,
    val link: String?,
    val niceDate: String?,
    val niceShareDate: String?,
    val origin: String?,
    val prefix: String?,
    val projectLink: String?,
    val publishTime: Long?,
    val realSuperChapterId: Int?,
    val selfVisible: Int?,
    val shareDate: Long?,
    val shareUser: String?,
    val superChapterId: Int?,
    val superChapterName: String?,
    val tags: List<HomeListTag>?,
    val title: String?,
    val type: Int?,
    val userId: Int?,
    val visible: Int?,
    val zan: Int?
)

data class HomeListTag(
    val name: String?,
    val url: String?
)

/**
 * 首页文章列表数据
 */
data class HomeListData(
    //当前页码
    val curPage: Int?,
    //文章列表数据
    val datas: List<HomeListItemData?>?,
    val offset: Int?,
    val over: Boolean?,
    //总共的页数
    val pageCount: Int?,
    //当前页数据长度
    val size: Int?,
    //总数据长度
    val total: Int?
) {
    override fun toString(): String {
        return "HomeListData(curPage=$curPage, datas=$datas, offset=$offset, over=$over, pageCount=$pageCount, size=$size, total=$total)"
    }
}

/**
 * 首页置顶列表数据
 */
class TopHomeListData : ArrayList<HomeListItemData?>()
