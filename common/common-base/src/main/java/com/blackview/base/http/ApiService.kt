package com.blackview.base.http

import com.blackview.base.request.BaseResponse
import com.franmontiel.persistentcookiejar.BuildConfig
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by yi on 2021/11/4.
 */
interface ApiService {

    companion object {
        const val SERVER_URL = "http://www.baidu.com"
    }

    @GET("/")
    suspend fun getData1(): BaseResponse<String>

    @GET("/")
    suspend fun getData(): ResponseBody

}