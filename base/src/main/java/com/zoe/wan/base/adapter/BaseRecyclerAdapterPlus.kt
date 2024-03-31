package com.zoe.wan.base.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

abstract class BaseRecyclerAdapter<D, V : RecyclerView.ViewHolder> :
    RecyclerView.Adapter<V>() {

    private var listData: List<D?>? = null
    private var itemListener: AdapterItemListener<D>? = null

    /**
     * 赋值并刷新列表
     */
    fun setDataList(data: List<D?>?) {
        listData = data
        notifyDataSetChanged()
    }

    /**
     * 方便获取数据源
     */
    fun getDataList(): List<D?>? {
        return listData
    }

    /**
     * 封装点击事件回调
     */
    fun setItemListener(listener: AdapterItemListener<D>?) {
        itemListener = listener
    }

    /**
     * 返回 R.package.layout
     */
//    abstract fun getLayoutId(): Int

    /**
     * 通过[getBinding]获取ViewHolder
     */
    abstract fun getViewHolder(parent: ViewGroup, viewType: Int): V

    /**
     * 在重载方法[onBindViewHolder]内调用，在此方法内实现item业务逻辑
     */
    abstract fun bindHolder(holder: V, position: Int)

    /**
     * 使用[DataBindingUtil]获取item布局返回binding
     */
    fun <B : ViewDataBinding> getBinding(parent: ViewGroup, layoutId: Int): B {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), layoutId, parent, false
        )
    }


    /**
     * 返回[listData]长度
     */
    override fun getItemCount(): Int {
        return listData?.size ?: 0
    }

    /**
     * 通过[getViewHolder]获取ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): V {
        return getViewHolder(parent, viewType)
    }

    /**
     * 封装了item的点击事件，并调用[bindHolder]
     */
    override fun onBindViewHolder(holder: V, position: Int) {
        holder.itemView.setOnClickListener {
            //item点击事件
            itemListener?.itemClick(getDataList()?.get(position), position)
        }
        bindHolder(holder, position)
    }

}

/**
 * ViewHolder基类，封装binding赋值
 */
abstract class BaseViewHolder<B : ViewDataBinding>(b: B) :
    RecyclerView.ViewHolder(b.root) {
    var binding: B

    init {
        this.binding = b
    }
}
