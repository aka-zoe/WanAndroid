package com.zoe.wan.android.common

/**
 * ListItem点击事件
 */
abstract class AdapterItemListener<T> {
    abstract fun itemClick(item: T?, position: Int)
}

/**
 * 点击收藏/取消收藏
 */
abstract class AdapterCollectListener<T> : AdapterItemListener<T>() {
    override fun  itemClick(item: T?, position: Int) {}

    abstract fun collect(position: Int, id: String)

    abstract fun cancelCollect(position: Int, id: String)
}
