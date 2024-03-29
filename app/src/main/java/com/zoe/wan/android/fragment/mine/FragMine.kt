package com.zoe.wan.android.fragment.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.databinding.FragmentMineBinding
import com.zoe.wan.android.fragment.mine.vm.MineViewModel
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.activity.login.LoginActivity

class FragMine : BaseFragment<FragmentMineBinding, MineViewModel>() {
    override fun initVariableId(): Int {
        return BR.mineVm
    }

    override fun initContentView(
        inflater: LayoutInflater?,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): Int {
        return R.layout.fragment_mine
    }

    override fun initViewData() {


        //登录
        binding?.mineLogin?.setOnClickListener {
            activity?.finish()
            context?.startActivity(Intent(context, LoginActivity::class.java))
        }

        //登出
        binding?.mineLoginOut?.setOnClickListener {
            viewModel?.logOut()
        }


        viewModel?.logoutState?.observe(viewLifecycleOwner) {
            if (it == true) {
                ToastUtils.showShort("账号已退出登录")
            }
        }
    }
}
