package com.blackview.module_vip.device.share

import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.request
import com.blackview.repository.httpService
import com.blackview.repository.vipService


/**
 *    author : MJ
 *    time   : 2022/07/07
 *    desc   : 接收的共享设备Model
 */
class AcceptDevicesModel :BaseViewModel() {

    /**
     * 获取别人共享的设备列表
     */
    fun getAcceptedDevices(){
        request({
            vipService.getAcceptedDevices()
        },{

        })
    }

}