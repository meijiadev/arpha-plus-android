package com.blackview.module_vip.device.share

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.adapter.DevicesAdapter
import com.blackview.module_vip.databinding.FragmentDevicesBinding
import com.blackview.repository.entity.DeviceData
import com.blackview.repository.entity.ShareMember


/**
 *    author : MJ
 *    time   : 2022\07\05
 *    desc   : 接收装置
 */
class AcceptDevicesFragment :BaseMVFragment<FragmentDevicesBinding,AcceptDevicesModel> (){

    private val devicesAdapter: DevicesAdapter by lazy { DevicesAdapter() }

    /**
     * 接收共享的设备列表
     */
    private var acceptedDevices = mutableListOf<DeviceData>()

    companion object{
        fun newInstance(): AcceptDevicesFragment {
            val args = Bundle()
            val fragment = AcceptDevicesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun createViewModel(fragment: Fragment): AcceptDevicesModel {
        return ViewModelProvider(this).get(AcceptDevicesModel::class.java)
    }



}