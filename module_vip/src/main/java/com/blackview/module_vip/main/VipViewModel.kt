package com.blackview.module_vip.main

import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheck
import com.blackview.repository.entity.VipMemberInfo
import com.blackview.repository.httpService
import com.blackview.repository.vipService
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   : 2022/06/30
 *    desc   : vip的ViewModel界面数据
 */
class VipViewModel:BaseViewModel() {
    /**
     * 监听vip信息是否更新
     */
    var vipMemberEvent=MutableLiveData<VipMemberInfo>()

    fun getVipMemberInfo(){
        request({
            vipService.vipMember()
        },{
            Logger.i("会员信息：$it")
            vipMemberEvent.value=it
        })
    }


}