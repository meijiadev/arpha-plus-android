package com.blackview.module_device.add

import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.request
import com.blackview.repository.entity.Product
import com.blackview.repository.entity.ProductType
import com.blackview.repository.httpService

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

    fun productType() {
        request({ httpService.produceTypes() }, {
            if (it.product_types.isNotEmpty()) {
                it.product_types[0].isSelect = true
                liveDataProductList.postValue(it.product_types)
                products(it.product_types[0].name)
            }
        })
    }

    fun products(name: String) {
        request({ httpService.products(name) }, {
            liveDataProducts.postValue(it.products)
        })
    }

}