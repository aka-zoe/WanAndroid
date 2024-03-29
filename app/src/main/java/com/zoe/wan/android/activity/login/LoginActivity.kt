package com.zoe.wan.android.activity.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.zoe.wan.android.activity.login.vm.LoginViewModel
import com.zoe.wan.android.databinding.ActivityLoginBinding
import com.zoe.wan.base.BaseActivity
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.activity.home.TabActivity

class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {

    companion object {
        val Intent_Type = "intentLoginOrRegister"
        val loginType = 0
        val registerType = 1
    }

    private var type = loginType

    override fun initVariableId(): Int {
        return BR.loginVm
    }

    override fun initContentView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_login
    }

    override fun initView() {
        //当前意图是登录还是注册，不传默认登录
        type = intent.getIntExtra(Intent_Type, loginType)
        if (type == loginType) {
            initLoginView()
        } else {
            //注册
            initRegisterView()
        }

        initListener()

        viewModel?.actionState?.observe(this) { state ->
            if (state == true) {
                finish()
                val intent = Intent(this@LoginActivity, TabActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initListener() {
        //按钮事件
        binding?.loginOrRegisterBtn?.setOnClickListener {
            if (type == loginType) {
                //登录
                viewModel?.login()
            } else {
                //注册
                viewModel?.register()
            }

        }

        //点击注册
        binding?.registerAction?.setOnClickListener {
            finish()
            val intent = Intent(this@LoginActivity, LoginActivity::class.java)
            intent.putExtra(Intent_Type, registerType)
            startActivity(intent)
        }
    }

    private fun initLoginView() {
        //重复输入密码框隐藏
        binding?.inputPasswordTwice?.visibility = View.GONE
        //注册按钮显示
        binding?.registerAction?.visibility = View.VISIBLE
    }

    private fun initRegisterView() {
        //重复输入密码框显示
        binding?.inputPasswordTwice?.visibility = View.VISIBLE
        //注册按钮隐藏
        binding?.registerAction?.visibility = View.GONE
        //按钮文字修改
        binding?.loginOrRegisterBtn?.text = "确定注册"
    }

}