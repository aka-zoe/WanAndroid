package com.zoe.wan.android.common

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.listener.OnBannerListener
import com.youth.banner.listener.OnPageChangeListener
import com.zoe.wan.android.R
import com.zoe.wan.android.activity.web.WebActivity
import com.zoe.wan.android.databinding.ItemHomeBannerBinding
import com.zoe.wan.android.databinding.ItemHomeListBinding
import com.zoe.wan.android.repository.data.HomeBannerData
import com.zoe.wan.android.repository.data.HomeBannerDataItem
import com.zoe.wan.android.repository.data.HomeListItemData


class HomeListAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        //banner item类型
        private const val ItemTypeBannerHeader = 0

        //列表item类型
        private const val ItemTypeList = 1

        //header数量
        private const val HeaderCount = 1
    }

    private var list: List<HomeListItemData?> = arrayListOf()
    private var homeBannerData: HomeBannerData? = null
    private var collectListener: AdapterCollectListener<HomeListItemData?>? = null
    fun setListData(list: List<HomeListItemData?>) {
        this.list = list
        notifyDataSetChanged()
    }

    fun setBannerData(bannerData: HomeBannerData?) {
        this.homeBannerData = bannerData
        notifyDataSetChanged()
    }

    fun setCollectListener(listener: AdapterCollectListener<HomeListItemData?>?) {
        this.collectListener = listener
    }

    /**
     * 收藏状态刷新
     */
    fun notifyCollectChange(position: Int, collect: Boolean) {
        if (list.isEmpty()) {
            return
        }

        list[position]?.collect = collect
        notifyDataSetChanged()
    }

    class MyViewHolder(itemDatabind: ItemHomeListBinding) :
        RecyclerView.ViewHolder(itemDatabind.root) {
        var itemDataBind: ItemHomeListBinding

        init {
            this.itemDataBind = itemDatabind
        }
    }

    class BannerViewHolder(itemHomeBannerBinding: ItemHomeBannerBinding) : RecyclerView.ViewHolder
        (itemHomeBannerBinding.root) {
        var itemDataBind: ItemHomeBannerBinding

        init {
            this.itemDataBind = itemHomeBannerBinding
        }
    }


    /**
     * 判断当前的item类型
     */
    override fun getItemViewType(position: Int): Int {
        if(HeaderCount != 0 && position < HeaderCount) {
            return ItemTypeBannerHeader
        } else {
            return ItemTypeList
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        if (viewType == ItemTypeList) {
            return MyViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context),
                    R.layout.item_home_list, parent, false
                )
            )
        } else {
            //banner类型
            return BannerViewHolder(
                DataBindingUtil.inflate(
                    LayoutInflater.from
                        (parent.context), R.layout.item_home_banner, parent, false
                )
            )
        }

    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        //绑定viewHolder，数据绑定
        if (holder is MyViewHolder) {
            val item: HomeListItemData? = list[position]
            holder.itemDataBind.item = item
            //文章是否收藏状态
            if (item?.collect == true) {
                holder.itemDataBind.itemHomeCollect.setBackgroundResource(R.drawable.img_collect)
            } else {
                holder.itemDataBind.itemHomeCollect.setBackgroundResource(
                    R.drawable
                        .img_collect_grey
                )
            }
            //是否置顶
            if (item?.type == 1) {
                holder.itemDataBind.itemHomeTopTag.text = "置顶"
            } else {
                holder.itemDataBind.itemHomeTopTag.text = ""
            }
            //点击进入链接
            holder.itemDataBind.itemHomeLinear.setOnClickListener {
                var intent = Intent(it.context, WebActivity::class.java)
                intent.putExtra(WebActivity.intentKeyTitle, item?.title)
                intent.putExtra(WebActivity.intentKeyUrl, item?.link)
                it.context.startActivity(intent)
//                Toast.makeText(it.context, "Item被点击了", Toast.LENGTH_LONG).show()
            }

            //点击查看专栏
            holder.itemDataBind.itemHomeChapter.setOnClickListener {
                Toast.makeText(it.context, "Chapter被点击了", Toast.LENGTH_LONG).show()
            }

            //点击收藏/取消收藏
            holder.itemDataBind.itemHomeCollect.setOnClickListener {
                if (item?.collect == true) {
                    //取消收藏
                    collectListener?.cancelCollect(position, "${item.id}")
                } else {
                    //收藏
                    collectListener?.collect(position, "${item?.id}")
                }
            }
        } else if (holder is BannerViewHolder) {
            //banner标题
            holder.itemDataBind.itemBannerTitle.text = homeBannerData?.get(0)?.title
            //配置适配器、指示器、滑动事件监听
            holder.itemDataBind.itemBanner.setAdapter(BannerAdapter(homeBannerData))
                .setIndicator(CircleIndicator(holder.itemDataBind.itemBanner.context))
                .addOnPageChangeListener(object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        //当banner切换时，标题同步切换
                        holder.itemDataBind.itemBannerTitle.text =
                            homeBannerData?.get(position)?.title

                    }

                    override fun onPageScrollStateChanged(state: Int) {

                    }

                }).setOnBannerListener(object : OnBannerListener<HomeBannerDataItem?> {
                    override fun OnBannerClick(data: HomeBannerDataItem?, position: Int) {
                        //banner点击事件
//                        Toast.makeText(
//                            holder.itemDataBind.itemBanner.context,
//                            "Banner被点击了",
//                            Toast.LENGTH_LONG
//                        ).show()

                        var intent =
                            Intent(holder.itemDataBind.itemBanner.context, WebActivity::class.java)
                        intent.putExtra(WebActivity.intentKeyTitle, data?.title)
                        intent.putExtra(WebActivity.intentKeyUrl, data?.url)
                        holder.itemDataBind.itemBanner.context.startActivity(intent)
                    }

                })
        }

    }

    /**
     * banner适配器
     */
    private class BannerAdapter(homeBannerData: HomeBannerData?) :
        BannerImageAdapter<HomeBannerDataItem?>
            (homeBannerData as List<HomeBannerDataItem?>?) {
        override fun onBindView(
            holder: BannerImageHolder?,
            data: HomeBannerDataItem?,
            position: Int,
            size: Int
        ) {
            //图片加载使用Glide
            holder?.itemView?.let {
                Glide.with(it)
                    .load(data?.imagePath ?: "")
                    .apply(RequestOptions.bitmapTransform(RoundedCorners(1)))
                    .into(holder.imageView)
            }
        }
    }
}

