package com.zoe.wan.http

import java.io.Serializable

class BaseResponse<T> : Serializable {

    private var errorMsg: String? = null
    //errorCode = 0 代表执行成功，不建议依赖任何非0的 errorCode.
    //errorCode = -1001 代表登录失效，需要重新登录。
    private var errorCode: Int? = null
    private var data: T? = null


    fun getErrMsg(): String? {
        return errorMsg
    }

    fun setErrMsg(message: String?) {
        this.errorMsg = message
    }

    fun getData(): T? {
        return data
    }

    fun setData(data: T) {
        this.data = data
    }

    fun getErrCode(): Int? {
        return errorCode
    }

    fun setErrCode(errCode: Int?) {
        this.errorCode = errCode
    }
}

