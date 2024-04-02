package com.zoe.wan.android.fragment.mine

import android.content.Intent
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.databinding.FragmentMineBinding
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.activity.about.AboutUsActivity
import com.zoe.wan.android.activity.collect.MyCollectActivity
import com.zoe.wan.android.activity.login.LoginActivity
import com.zoe.wan.base.BaseFragment

/**
 * 我的页面
 */
class FragMine : BaseFragment<FragmentMineBinding, MineViewModel>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun getViewModelId(): Int {
        return BR.mineVm
    }

    override fun initViewData() {

        initClick()


        initObserverData()
    }

    private fun initClick() {
        //登录
        binding?.mineUserName?.setOnClickListener {
            if(viewModel?.loginState?.get() == true){
                return@setOnClickListener
            }
            login()
        }

        //登录
        binding?.mineUserHead?.setOnClickListener {
            if(viewModel?.loginState?.get() == true){
                return@setOnClickListener
            }
            login()
        }

        //登出
        binding?.mineLoginOut?.setOnClickListener {
            viewModel?.logOut()
        }

        //进入我的收藏
        binding?.mineCollect?.setOnClickListener {
            startActivity(Intent(context, MyCollectActivity::class.java))
        }

        //关于我们
        binding?.mineAboutUs?.setOnClickListener {
            startActivity(Intent(context, AboutUsActivity::class.java))
        }
    }

    private fun initObserverData() {
        viewModel?.logoutState?.observe(viewLifecycleOwner) {
            if (it == true) {
                ToastUtils.showShort("账号已退出登录")
            }
        }
    }

    private fun login() {
        activity?.finish()
        context?.startActivity(Intent(context, LoginActivity::class.java))
    }
}
