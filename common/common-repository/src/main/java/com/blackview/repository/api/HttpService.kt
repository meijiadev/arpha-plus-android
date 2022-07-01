package com.blackview.repository.api

import android.util.ArrayMap
import com.blackview.base.request.BaseResponse
import com.blackview.repository.entity.ProductList
import com.blackview.repository.entity.Products
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
    @PUT("/api/member/match-check")
    suspend fun matchCheck(@Body params: ArrayMap<Any, Any>): ResponseBody

    @GET("api/member")
    suspend fun vipMember(@Header("Authorization") token: String): BaseResponse<VipMemberInfo>


}