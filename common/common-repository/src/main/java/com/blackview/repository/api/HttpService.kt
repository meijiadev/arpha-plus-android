package com.blackview.repository.api

import android.util.ArrayMap
import com.blackview.base.request.BaseResponse
import com.blackview.repository.entity.VipMemberInfo
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

    companion object {
        const val HTTP_URL = "http://54.251.22.185/"
    }

    //注册
    @POST("api/register")
    suspend fun register(@Body params: ArrayMap<Any, Any>): ResponseBody

    //登录
    @POST("api/login")
    suspend fun login(@Body params: ArrayMap<Any, Any>): ResponseBody

    //退出
    @POST("api/logout")
    suspend fun logout()

    //输入验证码
    @POST("api/valid")
    suspend fun validCode(@Body params: ArrayMap<Any, Any>):ResponseBody

    //忘记密码--检查账号
    @POST("api/forget-check")
    suspend fun forgetCheck(@Body params: ArrayMap<Any, Any>):ResponseBody

    //忘记密码--输入验证码
    @POST("api/forget-validate")
    suspend fun forgetValidate(@Body params: ArrayMap<Any, Any>):ResponseBody

    //忘记密码--更新密码
    @POST("api/reset-password")
    suspend fun resetPwd(@Body params: ArrayMap<Any, Any>):ResponseBody

    //微信登录
    @POST("api/oauth/wechat")
    suspend fun wechatLogin()

    @GET("api/member")
    suspend fun  vipMember(@Header("Authorization") token:String):BaseResponse<VipMemberInfo>

}