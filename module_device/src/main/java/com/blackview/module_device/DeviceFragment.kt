package com.blackview.module_device

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.base.BaseMVFragment
import com.blackview.module_device.add.AddAty
import com.blackview.module_device.databinding.FragmentDeviceBinding
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
 * Created by home on 2022/6/28.
 */
class DeviceFragment : BaseMVFragment<FragmentDeviceBinding, DeviceModel>() {

    override fun createViewModel(fragment: Fragment): DeviceModel {
        return ViewModelProvider(this).get(DeviceModel::class.java)
    }

    override fun initView() {
        super.initView()
        binding.ivDeviceAdd.setOnClickListener { 
            activity?.gotoAct<AddAty>()
        }
    }
    
    
}