package com.zoe.wan.android.fragment.mine

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.ToastUtils
import com.zoe.wan.android.databinding.FragmentMineBinding
import com.zoe.wan.base.BaseFragment
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.activity.about.AboutUsActivity
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

        initClick()


        initObserverData()
    }

    private fun initClick() {
        //登录
        binding?.mineUserName?.setOnClickListener {
            login()
        }
//        binding?.mineUserName?.text = Html.fromHtml(getString(R.string.about_content))
//        binding?.mineUserName?.movementMethod = LinkMovementMethod.getInstance()

        //登录
        binding?.mineUserHead?.setOnClickListener {
            login()
        }

        //登出
        binding?.mineLoginOut?.setOnClickListener {
            viewModel?.logOut()
        }

        //进入我的收藏
        binding?.mineCollect?.setOnClickListener { }

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
