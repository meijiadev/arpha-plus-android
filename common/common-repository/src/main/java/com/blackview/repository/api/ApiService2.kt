package com.blackview.repository.api

import com.blackview.base.request.BaseResponse
import com.blackview.repository.entity.PhoneInfo
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * Created by yi on 2021/11/4.
 */
interface ApiService2 {

    companion object {
        const val SERVER_URL = "https://www.sogou.com/"
    }

    @GET
    suspend fun getData1(@Url url: String, @Query("number") number: String): BaseResponse<PhoneInfo>

    @GET("/")
    suspend fun getData(): ResponseBody


}