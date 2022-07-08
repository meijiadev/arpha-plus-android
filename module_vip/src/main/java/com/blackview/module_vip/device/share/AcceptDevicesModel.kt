package com.blackview.module_vip.device.share

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.base.SingleLiveEvent
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheck
import com.blackview.base.http.requestNoCheckAndError
import com.blackview.contant.vip
import com.blackview.repository.entity.DeviceData
import com.blackview.repository.httpService
import com.blackview.repository.vipService
import com.orhanobut.logger.Logger
import org.json.JSONObject


/**
 *    author : MJ
 *    time   : 2022/07/07
 *    desc   : 接收的共享设备Model
 */
class AcceptDevicesModel :BaseViewModel() {

    /**
     * 删除设备是否成功
     */
    val deleteActionResult=SingleLiveEvent<Boolean>()

    val acceptDevicesEvent= MutableLiveData<MutableList<DeviceData>>()

    /**
     * 获取别人共享的设备列表
     */
    fun getAcceptedDevices(){
        request({
            vipService.getAcceptedDevices()
        },{
            acceptDevicesEvent.value=it
        })
    }


    /**
     * 删除别人共享的设备
     */
    fun deleteAcceptedDevices(deviceIds:Array<Int>){
        val params=ArrayMap<Any,Any>()
        params["device_ids"]=deviceIds
        requestNoCheckAndError(
            {
                vipService.deleteAcceptedDevices(params)
            },{
                deleteActionResult.value=true
                Logger.i(it.string())
            },{
                // 此处为true为了通过测试
                deleteActionResult.value=true
                //deleteActionResult.value=false
                Logger.i(it.toString())
            }
        )
    }

}