package com.blackview.base.request


class BaseResponse<T> {
    var message: String? = null
    var status = 0
    var data: T? = null
}
