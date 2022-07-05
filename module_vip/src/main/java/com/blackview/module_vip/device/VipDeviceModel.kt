package com.blackview.module_vip.device

import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.request
import com.blackview.repository.entity.DeviceData
import com.blackview.repository.httpService
import com.blackview.util.L
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   : 2022/07/04
 *    desc   : 设备相关（我的设备、接收设备）
 */
class VipDeviceModel:BaseViewModel() {

    /**
     * 自身的设备
     */
    var ownDevicesList=MutableLiveData<MutableList<DeviceData>>()

    /**
     * 被分享的设备
     */
    var shareDevicesList=MutableLiveData<MutableList<DeviceData>>()

    /**
     * 获取用户自己的设备
     */
    fun getOwnDevices(){
        request({
            httpService.getOwnDevices()
        },{
            Logger.i("设备数量：${it.size}")
            ownDevicesList.value=it
        })
    }

    /**
     * 获取别人分享的设备
     */
    fun getShareDevices(){
        request({
            httpService.getShareDevices()
        },{
            Logger.i("分享的设备数量：${it.size}")
            shareDevicesList.value=it
        })
    }
}