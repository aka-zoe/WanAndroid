package com.zoe.wan.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<B : ViewDataBinding, VM : BaseViewModel> : Fragment() {

    open var binding: B? = null
    open var viewModel: VM? = null

    /**
     * 返回layout
     */
    abstract fun getLayoutId(): Int

    /**
     * 返回vm id，通过package.BR获取
     */
    abstract fun getViewModelId(): Int

    /**
     * 执行控件与业务逻辑
     */
    abstract fun initViewData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate<B>(
            LayoutInflater.from(context), getLayoutId(),
            container, false
        )

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBase()
        initViewData()
    }

    private fun initBase() {
        //获取ViewModel类型
        val modelClass: Class<BaseViewModel>
        //获取带有泛型的父类
        val type = javaClass.genericSuperclass
        //ParameterizedType参数化类型，即泛型
        modelClass = if (type is ParameterizedType) {
            //getActualTypeArguments获取参数化类型的数组，泛型可能有多个，这里我们默认第二个泛型是ViewModel
            type.actualTypeArguments[1] as Class<BaseViewModel>
        } else {
            //如果没有指定泛型参数，则默认使用ViewModel
            BaseViewModel::class.java
        }

        //初始化viewModel
        viewModel = createViewModel(this, modelClass as Class<VM>)
        //viewmodel与view绑定
        binding?.setVariable(getViewModelId(), viewModel)
        //绑定生命周期
        binding?.lifecycleOwner = this

    }


    open fun <T : ViewModel> createViewModel(fragment: Fragment?, cls: Class<T>?): T {
        return ViewModelProvider(fragment!!)[cls!!]
    }
}
