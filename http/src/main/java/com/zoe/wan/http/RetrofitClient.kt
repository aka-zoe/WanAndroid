package com.zoe.wan.http

import android.content.Context
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.ref.WeakReference
import java.util.concurrent.TimeUnit


class RetrofitClient {

    /**
     * retrofit 初始化build
     */
    private fun RetrofitClient() {}


    //做成单例
    companion object {
        private var weakContext: WeakReference<Context>? = null
        private var retrofitClient: RetrofitClient? = null
        private const val DEFAULT_TIME_OUT = 15
        fun getInstance(): RetrofitClient {
            if (retrofitClient == null) {
                synchronized(RetrofitClient::class.java) {
                    retrofitClient = RetrofitClient()
                    return retrofitClient as RetrofitClient
                }
            }
            return retrofitClient as RetrofitClient
        }

    }

    fun setContext(context: Context) {
        weakContext = WeakReference(context)
    }

    /**
     * 创建连接客户端
     */
    private fun createOkHttpClient(): OkHttpClient {

        //设置请求头拦截器
        //设置日志拦截器
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY


        //根据需求添加不同的拦截器
        return OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .writeTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(DEFAULT_TIME_OUT.toLong(), TimeUnit.SECONDS)
//            .cookieJar(object : CookieJar{
//                private val cookieStore = HashMap<HttpUrl, List<Cookie>>()
//
//                override fun saveFromResponse(url: HttpUrl, cookies: MutableList<Cookie>) {
//                    cookieStore[url] = cookies;
//
//                }
//
//                override fun loadForRequest(url: HttpUrl): List<Cookie>? {
//                    return cookieStore[url]
//                }
//
//            })
            .cookieJar(PersistentCookieJar(context = weakContext?.get()))
            .connectionPool(ConnectionPool(8, 10, TimeUnit.SECONDS)) //添加这两行代码
            .sslSocketFactory(TrustAllCerts.createSSLSocketFactory()!!, TrustAllCerts())
            .hostnameVerifier(TrustAllCerts.TrustAllHostnameVerifier())
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }


    /**
     * 根据host 类型判断是否需要重新创建Client，因为一个app 有不同的BaseUrl，切换BaseUrl 就需要重新创建Client
     * 所以，就根据类型来从map中取出对应的client
     */
    fun <T> getDefault(interfaceServer: Class<T>?): T {
        return create(interfaceServer)
    }


    /**
     *
     */
    private fun <T> create(interfaceServer: Class<T>?): T {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BaseUrlConstants.getHost())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(createOkHttpClient())
            .build()
        if (interfaceServer == null) {
            throw RuntimeException("The Api InterfaceServer is null!")
        }
        return retrofit.create(interfaceServer)
    }

}
