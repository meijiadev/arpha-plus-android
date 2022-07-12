package com.blackview.repository.api

import android.util.ArrayMap
import com.blackview.base.request.BaseResponse
import com.blackview.base.request.BaseResponseNotData
import com.blackview.repository.entity.DeviceData
import com.blackview.repository.entity.SettingsData
import com.blackview.repository.entity.ShareMember
import com.blackview.repository.entity.VipMemberInfo
import okhttp3.ResponseBody
import retrofit2.http.*


/**
 *    author : MJ
 *    time   : 2022/07/08
 *    desc   : 把vip会员中心相关网络接口放到这里
 */
interface VipService {
    /**
     * 获取会员信息
     */
    @GET("api/member")
    suspend fun vipMember(): BaseResponse<VipMemberInfo>

    /**
     * 获取用户拥有的设备
     */
    @GET("api/share/owner-device")
    suspend fun  getOwnDevices(): BaseResponse<MutableList<DeviceData>>


    /**
     * 获取别人分享给用户的设备
     */
    @GET("api/share/accepted-device")
    suspend fun getShareDevices(): BaseResponse<MutableList<DeviceData>>

    /**
     * 校驗查詢賬號是否存在
     */
    @POST("api/share/check-member")
    suspend fun checkMember(@Body params: ArrayMap<Any, Any>): BaseResponseNotData

    /**
     * 获取装置分享的用户列表
     */
    @GET("api/share/share-list")
    suspend fun getShareList(
        @Query("device_id")
        deviceId:String
    ): BaseResponse<MutableList<ShareMember>>

    /**
     * 将设备共享给朋友
     */
    @POST("api/share/device-share")
    suspend fun shareDevices(
        @Body params: ArrayMap<Any, Any>
    ):BaseResponseNotData


    /**
     * 获取他人共享给自己的设备
     */
    @GET("api/share/accepted-device")
    suspend fun getAcceptedDevices():BaseResponse<MutableList<DeviceData>>

    /**
     * 删除别人共享的设备
     * 当使用delete时写法
     */
    @HTTP(method = "DELETE", path = "api/share/delete-accepted-device",hasBody = true)
    suspend fun deleteAcceptedDevices(@Body params: ArrayMap<Any, Any>):ResponseBody


    /**
     * 获取指定装置的通知设定项
     */
    @GET("api/devices/notify-setting")
    suspend fun getNotifySettings(
        @Query ("device_id")
        deviceId:String
    ):BaseResponse<SettingsData>


    /**
     *  更新通知设定
     */
    @PUT("api/devices/update-notify")
    suspend fun updateNotify(@Body params:ArrayMap<Any,Any>):ResponseBody


}