package com.zoe.wan.android.activity.web

import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_BACK
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.blankj.utilcode.util.LogUtils
import com.zoe.wan.android.BR
import com.zoe.wan.android.R
import com.zoe.wan.android.databinding.ActivityWebBinding
import com.zoe.wan.base.BaseActivity


class WebActivity : BaseActivity<ActivityWebBinding, WebViewModel>() {
    companion object {
        const val intentKeyUrl = "intentKeyUrl"
        const val intentKeyTitle = "intentKeyTitle"

    }

    override fun onStop() {
        super.onStop()
        binding?.webView?.settings?.javaScriptEnabled = false
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_web
    }

    override fun getViewModelId(): Int {
        return BR.webVM
    }

    override fun initViewData() {
        val loadUrl = intent.getStringExtra(intentKeyUrl)
        var webTitle = intent.getStringExtra(intentKeyTitle)
        webTitle = if ((webTitle?.length ?: 0) >= 20) {
            webTitle?.substring(0, 20) + "..."
        } else {
            webTitle
        }
        viewModel?.webTitle?.set(webTitle)

        binding?.webView?.loadUrl(loadUrl ?: "")

        binding?.webViewBack?.setOnClickListener { finish() }

        binding?.webView?.settings?.apply {
            this.javaScriptEnabled = true
            //设置自适应屏幕，两者合用
            this.useWideViewPort = true
            this.loadWithOverviewMode = true

            //缩放操作
            this.setSupportZoom(true) //支持缩放，默认为true。是下面那个的前提。
            this.setBuiltInZoomControls(true) //设置内置的缩放控件。若为false，则该WebView不可缩放
            this.setDisplayZoomControls(false) //隐藏原生的缩放控件

            //其他细节操作
            this.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK) //关闭webview中缓存
            this.setAllowFileAccess(true) //设置可以访问文件
            this.setJavaScriptCanOpenWindowsAutomatically(true) //支持通过JS打开新窗口
            this.setLoadsImagesAutomatically(true) //支持自动加载图片
            this.setDefaultTextEncodingName("utf-8")//设置编码格式

        }

        binding?.webView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?, url: String?
            ): Boolean {
//                if(url?.startsWith("http://") == false || url?.startsWith("https://") == false){
//                    val intent =
//                        Intent(Intent.ACTION_VIEW, Uri.parse(url))
//                    startActivity(intent)
//                }else{
                LogUtils.d("网页内url变化：${url}")
                view?.loadUrl(url ?: "")
//                }
                return true
            }
        }

        binding?.webView?.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
            }
        }
    }

    /**
     * 假如在webview里按下返回键，优先返回webview内的页面
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if ((keyCode == KEYCODE_BACK) && (binding?.webView?.canGoBack() == true)) {
            binding?.webView?.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}
