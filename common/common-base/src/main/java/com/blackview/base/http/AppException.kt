package com.blackview.base.http

/**
 * Created by yi on 2021/11/4.
 * 自定义错误信息异常
 */
class AppException : Exception {

    var status: Int = 0 //错误码
    var msg: String? = null //错误日志
    var throwable: Throwable? = null

    constructor(status: Int, message: String? = "", throwable: Throwable? = null) : super(message) {
        this.msg = message ?: "请求失败，请稍后再试"
        this.status = status
        this.throwable = throwable
    }

    constructor(error: Error, e: Throwable?) {
        status = error.getKey()
        msg = e?.message
        throwable = e
    }
}