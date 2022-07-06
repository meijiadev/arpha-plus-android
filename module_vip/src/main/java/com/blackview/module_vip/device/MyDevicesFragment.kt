package com.blackview.module_vip.device

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.R
import com.blackview.module_vip.ShareDeviceDialog
import com.blackview.module_vip.adapter.DevicesAdapter
import com.blackview.module_vip.databinding.FragmentDevicesBinding
import com.blackview.repository.entity.DeviceData
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.orhanobut.logger.Logger
import java.util.stream.IntStream


/**
 *    author : MJ
 *    time   : 2022\07\05
 *    desc   : 我的设备
 */
class MyDevicesFragment : BaseMVFragment<FragmentDevicesBinding, MyDevicesModel>(),
    OnItemClickListener {

    private val devicesAdapter: DevicesAdapter by lazy { DevicesAdapter() }

    /**
     * 自身的设备列表
     */
    private var devicesData = mutableListOf<DeviceData>()

    companion object {
        fun newInstance(): MyDevicesFragment {
            val args = Bundle()
            val fragment = MyDevicesFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun createViewModel(fragment: Fragment): MyDevicesModel {
        return ViewModelProvider(this).get(MyDevicesModel::class.java)
    }


    override fun initView() {
        super.initView()
        binding.recyclerDevices.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        binding.recyclerDevices.adapter = devicesAdapter
        devicesAdapter.setOnItemClickListener(this)
    }


    override fun initData() {
        super.initData()
        devicesData.clear()
        devicesData.add(DeviceData(1, "jjjjjjjjjkkkkklll", "Test-device1"))
        devicesData.add(DeviceData(2, "jjjjjjjjjkkkkklll", "Test-device2"))
        devicesData.add(DeviceData(3, "jjjjjjjjjkkkkklll", "Test-device3"))
        devicesData.add(DeviceData(4, "jjjjjjjjjkkkkklll", "Test-device4"))
        devicesData.add(DeviceData(5, "jjjjjjjjjkkkkklll", "Test-device5"))
        devicesAdapter.data = devicesData
    }

    override fun initListener() {
        super.initListener()
        binding.bottomButton.setOnClickListener {
            if (devicesAdapter.getSelectState()) {
                // 判断是否有选中要分享的设备
                val found = devicesData.any { it.selected }
                val device:DeviceData=devicesData.first{it.selected}
                if (found) {
                    viewModel.getShareList(device.device_id.toString())
                   // showShareDialog()
                } else {
                    ToastUtils.showShort(com.blackview.common_res.R.string.please_select_device)
                }
            } else {
                devicesAdapter.setSelectState(true)
                devicesAdapter.notifyDataSetChanged()
                binding.bottomButton.text =
                    getString(com.blackview.common_res.R.string.button_text_share_device)
            }
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.shareMemberEvent.observe(viewLifecycleOwner){
            it?.let {
                showShareDialog()
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        Logger.i("点击当前设备：$position;-${devicesData[position].nick_name}")
        val state = devicesData[position].selected
        devicesData[position].selected = !state
        devicesAdapter.notifyDataSetChanged()

    }

    private fun showShareDialog() {
        val shareDeviceDialog = ShareDeviceDialog(requireContext())
            .onJoin {
                Logger.i("校验的phone:$it")
                viewModel.checkMember(it)
            }
            .onCancel {
                Logger.i("取消")

            }
            .onConfirm {
                Logger.i("确认")
            }
        XPopup.Builder(requireContext())
            .isViewMode(true)
            .dismissOnTouchOutside(true)
            .popupAnimation(PopupAnimation.TranslateFromBottom)
            .asCustom(shareDeviceDialog)
            .show()
    }


}