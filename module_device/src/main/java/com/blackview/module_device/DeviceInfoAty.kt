package com.blackview.module_device

import com.blackview.base.base.BaseMVActivity
import com.blackview.module_device.databinding.ActivityDeviceInfoBinding
import com.blackview.repository.entity.Device

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
 * Created by home on 2022/7/6.
 */
class DeviceInfoAty : BaseMVActivity<ActivityDeviceInfoBinding, DeviceModel>() {

    var device: Device? = null

    override fun getViewBinding(): ActivityDeviceInfoBinding {
        return ActivityDeviceInfoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        device = intent?.getParcelableExtra("deviceTitle")
        getPageHead(this).setTitleText(device?.device_name)
    }

}