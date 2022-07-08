package com.blackview.repository.api

import android.util.ArrayMap
import com.blackview.base.request.BaseResponse
import com.blackview.base.request.BaseResponseNotData
import com.blackview.repository.entity.*
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * Created by home on 2022/6/20.
 */
interface HttpService {
    //配网相关
    /**获取产品类型*/
    @GET("/api/product-types")
    suspend fun produceTypes(): BaseResponse<ProductList>

    /**产品列表*/
    @GET("/api/products/{product_type}")
    suspend fun products(@Path("product_type") product_type: String): BaseResponse<Products>

    /**配网token*/
    @GET("/api/member/get-member-token")
    suspend fun getMemberToken(): ResponseBody

    /**配网轮询*/
    @POST("/api/member/match-check")
    suspend fun matchCheck(@Body params: ArrayMap<Any, Any>): ResponseBody

    //装置相关的
    /**请求装置列表*/
    @GET("/api/devices")
    suspend fun devices(): BaseResponse<Devices>

    /**变更装置名称*/
    @PUT("/api/devices/change-name")
    suspend fun changeDeviceName(@Body params: ArrayMap<Any, Any>): ResponseBody

    /**删除装置*/
    @DELETE("/api/devices/delete")
    suspend fun deleteDevices(@Body params: ArrayMap<Any, Any>): ResponseBody

    /**获取通知设定*/
    @GET("/api/devices/notify-setting")
    suspend fun getNotifySetting(@Query("device_id") device_id: String)

    @PUT("/api/devices/update-notify")
    suspend fun updateNotify(@Body body: Dd)





}