package com.blackview.repository.api

import android.util.ArrayMap
import com.blackview.base.request.BaseResponse
import com.blackview.base.request.BaseResponseNotData
import com.blackview.repository.entity.*
import com.blackview.repository.entity.ProductList
import com.blackview.repository.entity.Products
import com.blackview.repository.entity.VipMemberInfo
import com.blackview.repository.entity.*
import okhttp3.RequestBody
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

    /**获取产品类型*/
    @GET("/api/product-types")
    suspend fun produceTypes(): BaseResponse<ProductList>

    @GET("/api/products/{product_type}")
    suspend fun products(@Path("product_type") product_type:String): BaseResponse<Products>

    @GET("/api/member/get-member-token")
    suspend fun getMemberToken()

    /**
     * 获取会员信息
     */
    @GET("api/member")
    suspend fun vipMember():BaseResponse<VipMemberInfo>

    /**
     * 获取用户拥有的设备
     */
    @GET("api/share/owner-device")
    suspend fun  getOwnDevices():BaseResponse<MutableList<DeviceData>>


    /**
     * 获取别人分享给用户的设备
     */
    @GET("api/share/accepted-device")
    suspend fun getShareDevices():BaseResponse<MutableList<DeviceData>>

    /**
     * 校驗查詢賬號是否存在
     */
    @POST("api/share/check-member")
    suspend fun checkMember(@Body params:ArrayMap<Any,Any>):BaseResponse<String>

    /**
     * 获取装置分享的用户列表
     */
    @GET("api/share/share-list")
    suspend fun getShareList(
        @Query("device_id")
        deviceId:String
    ):BaseResponse<MutableList<ShareMember>>


    @PUT("/api/devices/update-notify")
    suspend fun updateNotify(@Body body: Dd)
    @POST("api/share/device-share")
    suspend fun shareDevices(
        @Body params: ArrayMap<Any, Any>
    ):BaseResponseNotData





}