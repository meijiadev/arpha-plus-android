package com.blackview.module_device.add

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.base.SingleLiveEvent
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheck
import com.blackview.repository.entity.Product
import com.blackview.repository.entity.ProductType
import com.blackview.repository.httpService
import com.google.zxing.qrcode.encoder.QRCode
import org.json.JSONObject

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
 * Created by home on 2022/6/28.
 */
class AddModel : BaseViewModel() {

    val liveDataProductList = MutableLiveData<List<ProductType>>()
    val liveDataProducts = MutableLiveData<List<Product>>()
    val memberTokenEvent = SingleLiveEvent<String>()
    val matchCheckEvent = SingleLiveEvent<Void>()

    /**获取产品类型*/
    fun productType() {
        request({ httpService.produceTypes() }, {
            if (it.product_types.isNotEmpty()) {
                it.product_types[0].isSelect = true
                liveDataProductList.postValue(it.product_types)
                products(it.product_types[0].name)
            }
        })
    }

    /**产品列表*/
    fun products(name: String) {
        request({ httpService.products(name) }, {
            liveDataProducts.postValue(it.products)
        })
    }

    /**配网token*/
    fun getMemberToken() {
        requestNoCheck({ httpService.getMemberToken() }, {
            //{"code":20000,"message":"success","data":{"member_token":"a12f02"}}
            val member_token = JSONObject(it.string()).optJSONObject("data").optString("member_token")
            if (member_token.isNotEmpty()) {
                memberTokenEvent.postValue(member_token)
            }
        })
    }

    /**配网轮询*/
    fun matchCheck(member_token: String) {
        val params = ArrayMap<Any, Any>()
        params["member_token"] = member_token
        requestNoCheck({ httpService.matchCheck(params) }, {
            matchCheckEvent.post()
        })
    }

}