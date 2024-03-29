package com.zoe.wan.base.tab

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.viewpager2.widget.ViewPager2
import com.zoe.wan.base.R
import java.lang.IndexOutOfBoundsException
import java.lang.RuntimeException

/**
 * 作者：Gongsensen
 * 日期：2022/10/12
 * 说明：
 */
class NavigationBottomBar(context: Context, attr: AttributeSet) : ConstraintLayout(context, attr) {
    companion object {
        private const val TAG: String = "NavigationBottomBar"
        const val BOTTOM_TAB_TYPE_ONLY_ICON = 0
        const val BOTTOM_TAB_TYPE_ONLY_TEXT = 1
        const val BOTTOM_TAB_TYPE_BOTH_HAVE = 2
    }


    private val rootView: ConstraintLayout? by lazy {
        LayoutInflater.from(context)
            .inflate(R.layout.view_bottom_tab_bar, this, false) as ConstraintLayout
    }

    private var bottomTabLinear: LinearLayoutCompat? = null

    //适配viewPager2
    private var viewPager2: ViewPager2? = null

    //底部按钮图标(选中)
    private val selectedIcons = mutableListOf<Bitmap>()

    //底部按钮图标(未选中)
    private val unselectIcons = mutableListOf<Bitmap>()

    //底部按钮文字
    private val textList = mutableListOf<String>()

    private val tabListView = mutableListOf<TabIconView>()

    private var orientation: Int = LinearLayoutCompat.HORIZONTAL

    //默认icon大小75
    private var iconSize: Int = 75

    private var tabClickListener: OnBottomTabClickListener? = null

    init {
        initView()
        addView(rootView)
    }

    private fun initView() {
        bottomTabLinear = rootView?.findViewById(R.id.bottomTabLinear)
    }

    /**
     * 选中状态的icon
     */
    fun setSelectedIcons(selectedIcons: List<Bitmap>?) = apply {
        if (selectedIcons.isNullOrEmpty()) {
            throw RuntimeException("$TAG selectedIcons not null or empty!")
        }
        this.selectedIcons.clear()
        this.selectedIcons.addAll(selectedIcons)
    }

    /**
     * 未选中状态的icon
     */
    fun setUnselectIcons(unselectIcons: List<Bitmap>?) = apply {
        if (unselectIcons.isNullOrEmpty()) {
            throw RuntimeException("$TAG unselectIcons not null or empty!")
        }
        this.unselectIcons.clear()
        this.unselectIcons.addAll(unselectIcons)
    }

    /**
     * tab按钮的文字
     */
    fun setTabText(tabTextList: List<String>?) = apply {
        if (tabTextList.isNullOrEmpty()) {
            throw RuntimeException("$TAG tabTextList not null or empty!")
        }
        this.textList.clear()
        this.textList.addAll(tabTextList)
    }

    /**
     * [orientation] 0 = HORIZONTAL; 1 = VERTICAL;
     * 默认VERTICAL
     */
    fun setOrientation(orientation: Int) = apply {
        if (orientation != LinearLayoutCompat.VERTICAL || orientation != LinearLayoutCompat.HORIZONTAL) {
            throw RuntimeException("NavigationBottomBar orientation must 0 or 1 !")
        }
        this.orientation = orientation
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
     * 与viewpager2的适配
     */
    fun setupViewpager(viewPager2: ViewPager2?) = apply {
        if (viewPager2 == null) {
            throw RuntimeException("$TAG viewPager2 must be not null!")
        }
        this.viewPager2 = viewPager2
    }

    /**
     * 注册点击回调
     */
    fun registerTabClickListener(onBottomTabClickListener: OnBottomTabClickListener?) {
        this.tabClickListener = onBottomTabClickListener
        setTabClickListener()
    }

    /**
     * 在最后调用
     */
    fun start() {
        initTabLinear()
        itemChanged()
    }

    private fun initTabLinear() {
        bottomTabLinear?.let {
            it.layoutParams = rootView?.layoutParams
            it.gravity = Gravity.CENTER
            it.orientation = this.orientation
        }
        Log.d(
            TAG,
            "initTabLinear: selectedIcons=${selectedIcons.size} unselectIcons=${unselectIcons.size} textList=${textList.size}"
        )
        //5. 图标文字都没有
        if (selectedIcons.isEmpty() && unselectIcons.isEmpty() && textList.isEmpty()) {
            throw RuntimeException("$TAG iconList or textList must have one!")
        }

        //1. 只有图标
        if (selectedIcons.isNotEmpty() && unselectIcons.isNotEmpty() && textList.isEmpty()) {
            if (selectedIcons.size != unselectIcons.size) {
                throw RuntimeException("$TAG selectedIcons or unselectIcons must keep same length!")
            }
            initBottomTabView(selectedIcons.size, BOTTOM_TAB_TYPE_ONLY_ICON)
            return
        }

        //2. 只有文字
        if (selectedIcons.isEmpty() && unselectIcons.isEmpty() && textList.isNotEmpty()) {
            initBottomTabView(textList.size, BOTTOM_TAB_TYPE_ONLY_TEXT)
            return
        }

        //3. 图标文字都有
        if (selectedIcons.isNotEmpty() && unselectIcons.isNotEmpty() && textList.isNotEmpty()) {
            //4. 图标文字都有的时候两个数组长度必须一致
            if (selectedIcons.size != textList.size) {
                throw RuntimeException("$TAG iconList or textList must keep same length!")
            }
            initBottomTabView(selectedIcons.size, BOTTOM_TAB_TYPE_BOTH_HAVE)
        }
    }

    /**
     * 初始化底部栏View
     */
    private fun initBottomTabView(btnCount: Int, type: Int) {
        var i = 0
        while (true) {
            if (i >= btnCount) {
                return
            }
            val height =
                bottomTabLinear?.layoutParams?.height ?: 150
            val tabIconView = TabIconView(context).apply {
                this.setHeight(height)
                    .init(type, textList[i])
            }
            if (i == 0) {
                tabIconView.setTabIcon(selectedIcons[i])
                tabIconView.setClicked(true)
            } else {
                tabIconView.setTabIcon(unselectIcons[i])
                tabIconView.setClicked(false)
            }

            tabListView.add(tabIconView)
            bottomTabLinear?.addView(tabIconView)
            i++
        }
    }


    /**
     * tab按钮的点击事件，假如配置了viewpager2，需要动态切换item
     */
    private fun setTabClickListener() {
        if (tabListView.isEmpty()) {
            return
        }
        tabListView.forEachIndexed { index, tabIconView ->
            //点击事件回调，切换item
            tabIconView.setOnClickListener {
                //当前点击的如果是未选中，则选中按钮、切换界面、把其它tab变成未选中
                val cl = tabIconView.getClicked()
                if (!cl) {
                    tabIconView.setTabIcon(selectedIcons[index])
                    tabIconView.setClicked(true)
                    //再遍历数组，把其它tab变成未选中
                    tabListView.forEachIndexed { j, tab ->
                        if (index != j && tab.getClicked()) {
                            tab.setTabIcon(unselectIcons[j])
                            tab.setClicked(false)
                        }
                    }
                }
                //回调出去
                this.tabClickListener?.tabClick(index)
                //配置viewPager2
                if (viewPager2 != null) {
                    viewPager2?.currentItem = index
                }
            }
        }
    }

    private fun itemChanged() {
        if (viewPager2 != null && tabListView.isNotEmpty()) {
            viewPager2?.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    try {
                        if (selectedIcons.isNotEmpty()) {
                            tabListView[position].setTabIcon(selectedIcons[position])
                        }
                        tabListView[position].setClicked(true)
                        //再遍历数组，把其它tab变成未选中
                        tabListView.forEachIndexed { j, tab ->
                            if (position != j && tab.getClicked()) {
                                if (unselectIcons.isNotEmpty()) {
                                    tab.setTabIcon(unselectIcons[j])
                                }
                                tab.setClicked(false)
                            }
                        }
                    } catch (e: IndexOutOfBoundsException) {
                        e.printStackTrace()
                    }
                }
            })
        }
    }

    interface OnBottomTabClickListener {
        fun tabClick(position: Int)
    }
}
