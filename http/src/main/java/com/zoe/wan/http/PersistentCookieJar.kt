package com.zoe.wan.http

import android.content.Context
import android.content.SharedPreferences
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import java.net.URL
import java.util.concurrent.ConcurrentHashMap

class PersistentCookieJar(context: Context?) : CookieJar {

    private var sharedPreferences: SharedPreferences? = null
    private val cookies: MutableMap<String, MutableList<Cookie>> = ConcurrentHashMap()

    init {
        context?.let {
            sharedPreferences = it.getSharedPreferences("CookiePrefs", Context.MODE_PRIVATE)
        }
        // Load cookies from SharedPreferences
        sharedPreferences?.all?.forEach { (host, cookieSet) ->
            val cookiesString = cookieSet as? String
            cookiesString?.let {
                val cookieList = mutableListOf<Cookie>()
                it.split(";").forEach { cookieString ->
                    Cookie.parse(HttpUrl.get(URL("https://"+host)), cookieString)?.let { cookie ->
                        cookieList.add(cookie)
                    }
                }
                cookies[host] = cookieList
            }
        }
    }

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        val host = url.host()
        val cookiesString = cookies.joinToString("; ") { it.toString() }
        val editor = sharedPreferences?.edit()
        editor?.putString(host, cookiesString)
        editor?.apply()
        this.cookies[host] = cookies.toMutableList()
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        val host = url.host()
        return cookies[host] ?: emptyList()
    }

    companion object {
        private const val TAG = "PersistentCookieJar"
    }
}
