package com.blackview.module_device

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.blackview.base.base.BaseMVActivity
import com.blackview.contant.PASSWORD
import com.blackview.contant.USER
import com.blackview.contant.device
import com.blackview.module_device.databinding.ActivityDeviceBinding
import com.blackview.repository.login.LoginActivity
import com.blackview.util.L
import com.blackview.util.SpUtil
import com.blackview.util.gotoAct

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
 * Created by home on 2022/6/14.
 */
@Route(path = device)
class DeviceActivity : BaseMVActivity<ActivityDeviceBinding, DeviceModel>() {

    override fun getViewBinding(): ActivityDeviceBinding {
        return ActivityDeviceBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        SpUtil.decodeString(USER)?.apply {
            L.e("user:$this")
        }
        SpUtil.decodeString(PASSWORD)?.apply {
            L.e("pwd:$this")
        }

    }
}