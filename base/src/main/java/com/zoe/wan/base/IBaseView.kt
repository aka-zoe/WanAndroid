package com.zoe.wan.base

interface IBaseView {
    /**
     * 初始化界面
     */
    fun initView()


    /**
     * 初始化界面观察者的监听
     */
    fun initObservableData()
}
