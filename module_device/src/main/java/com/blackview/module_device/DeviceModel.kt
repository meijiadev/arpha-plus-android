package com.blackview.module_device

import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheck
import com.blackview.repository.entity.Dd
import com.blackview.repository.entity.Device
import com.blackview.repository.entity.Noti
import com.blackview.repository.httpService
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody


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
class DeviceModel : BaseViewModel() {


    var liveDevices = MutableLiveData<List<Device>>()

    fun devices() {
        request({ httpService.devices() }, {
            liveDevices.postValue(it.devices)
        })
    }

    fun updateNotify(id: String) {
        val d = Noti(true, true, false)
        val bean = Dd(id, d)
        //val json = Gson().toJson(bean)
        //val requestBody: RequestBody = RequestBody.create(MediaType.parse("application/json"), json)
        requestNoCheck({ httpService.updateNotify(bean) }, {

        })
    }
    
}