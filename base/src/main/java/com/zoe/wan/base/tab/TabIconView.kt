package com.zoe.wan.base.tab

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat

/**
 * 作者：Gongsensen
 * 日期：2022/10/12
 * 说明：
 */
class TabIconView(context: Context) : LinearLayoutCompat(context) {

    //点击状态 false=未点击 ，true=已点击
    private var isClicked = false

    //高度
    private var tabHeight = 150

    //宽度
    private var tabWidth = 0

    //默认icon大小75
    private var iconSize: Int = 75

    private var iconImg: ImageView? = null

    private var iconText: TextView? = null

    fun setClicked(isClicked: Boolean?) {
        if (isClicked == null) {
            return
        }
        this.isClicked = isClicked
    }

    fun getClicked(): Boolean {
        return isClicked
    }

    fun setHeight(tabHeight: Int?) = apply {
        tabHeight?.let {
            this.tabHeight = it
        }
    }

    fun setWidth(tabWidth: Int?) = apply {
        tabWidth?.let {
            this.tabWidth = it
        }
    }

    /**
     * icon大小设置，默认75
     */
    fun setIconSize(size: Int?) = apply {
        size?.let {
            this.iconSize = size
        }
    }

    /**
     * 设置图标
     */
    fun setTabIcon(bitmap: Bitmap?) {
        if (bitmap != null || iconImg != null) {
            iconImg?.setImageBitmap(bitmap)
        }
    }

    fun setTabTextSize(tvSize: Float?) {
        if (iconText != null && tvSize != null) {
            iconText?.textSize = tvSize
        }
    }

    fun init(type: Int, string: String?) {
        initTab(type, string)
    }

    private fun initTab(type: Int, string: String?) {
        val l = LayoutParams(tabWidth, tabHeight, 1f)
        this.layoutParams = l
        this.orientation = VERTICAL
        this.gravity = Gravity.CENTER
        Log.d("TabIconView", "initTab: type=$type string=$string")
        if (type == NavigationBottomBar.BOTTOM_TAB_TYPE_BOTH_HAVE) {
            iconImg = getIconView(iconSize)
            iconText = getTabTextView(string)
            this.addView(iconImg)
            this.addView(iconText)

        }
        if (type == NavigationBottomBar.BOTTOM_TAB_TYPE_ONLY_ICON) {
            iconImg = getIconView(iconSize)
            this.addView(iconImg)
        }
        if (type == NavigationBottomBar.BOTTOM_TAB_TYPE_ONLY_TEXT) {
            iconText = getTabTextView(string)
            this.addView(iconText)
        }
    }

    private fun getIconView(iconSize: Int?): ImageView? {
        if (iconSize == null) {
            return null
        }
        val icon = ImageView(context).apply {
            this.layoutParams = LayoutParams(iconSize, iconSize)
        }
        return icon
    }

    private fun getTabTextView(string: String?): TextView? {
        if (string.isNullOrEmpty()) {
            return null
        }
        val text = TextView(context).apply {
            this.text = string
            this.textSize = 12f
            this.gravity = Gravity.CENTER
        }
        return text

    }

}
