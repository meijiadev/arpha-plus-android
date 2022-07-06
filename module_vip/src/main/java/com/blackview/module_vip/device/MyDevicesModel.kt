package com.blackview.module_vip.device

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheck
import com.blackview.repository.entity.ShareMember
import com.blackview.repository.httpService
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   : 2022/07/06
 *    desc   : 我的装置的Model
 */
class MyDevicesModel : BaseViewModel() {

    val shareMemberEvent=MutableLiveData<MutableList<ShareMember>>()

    /**
     * 获取装置共享者列表
     */
    fun getShareList(deviceId: String) {
        request(
            {
                httpService.getShareList(deviceId)
            },
            {
                shareMemberEvent.value=it
                Logger.i("获取当前用户的")
            }
        )
    }

    /**
     * 校验用户是否存在
     */
    fun checkMember(account: String) {
        val params = ArrayMap<Any, Any>()
        params["account"] = account
        params["manufacturer"] = "Arpha"
        requestNoCheck(
            {
                httpService.checkMember(params)
            }, {
                when (it.code) {
                    20000 -> Logger.i("该用户存在")
                    42202 -> Logger.i("查无账号")
                    42212 -> Logger.i("不能分享给自己")
                }
            }
        )
    }
}