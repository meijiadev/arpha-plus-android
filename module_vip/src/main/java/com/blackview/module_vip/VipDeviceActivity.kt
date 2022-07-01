package com.blackview.module_vip

import com.blackview.base.base.BaseMVActivity
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.databinding.ActivityVipDeviceBinding
import com.blankj.utilcode.util.ToastUtils


/**
 *    author : MJ
 *    time   : 2022/07/01
 *    desc   : 会员页面中的设备相关设置页
 */
const val WHERE_TO_JUMP_FROM = "where to jump from"
const val JUMP_FORM_DEVICE_SHARE = "device share"
const val JUMP_FORM_MESSAGE_SETTINGS = "notification settings"

class VipDeviceActivity : BaseMVActivity<ActivityVipDeviceBinding, BaseViewModel>() {

    override fun getViewBinding(): ActivityVipDeviceBinding {
        return ActivityVipDeviceBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        getBackButton().setOnClickListener {
            finish()
        }
    }

    override fun initData() {
        super.initData()
        val msg = intent.extras?.getString(WHERE_TO_JUMP_FROM)
        ToastUtils.showLong(msg)
        when (msg) {
            JUMP_FORM_DEVICE_SHARE -> setTitle(getString(com.blackview.common_res.R.string.device_share))
            JUMP_FORM_MESSAGE_SETTINGS -> setTitle(getString(com.blackview.common_res.R.string.message_settings))
        }
    }


}