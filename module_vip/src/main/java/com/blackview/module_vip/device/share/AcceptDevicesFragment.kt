package com.blackview.module_vip.device.share

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.R
import com.blackview.module_vip.adapter.DevicesAdapter
import com.blackview.module_vip.databinding.FragmentDevicesBinding
import com.blackview.module_vip.dialog.showSuccessTips
import com.blackview.repository.entity.DeviceData
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.interfaces.OnCancelListener
import com.lxj.xpopup.interfaces.OnConfirmListener
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   : 2022\07\05
 *    desc   : 接收装置
 */
class AcceptDevicesFragment : BaseMVFragment<FragmentDevicesBinding, AcceptDevicesModel>(),
    OnItemClickListener {

    private val devicesAdapter: DevicesAdapter by lazy { DevicesAdapter() }

    /**
     * 接收共享的设备列表
     */
    private var acceptedDevices = mutableListOf<DeviceData>()

    companion object {
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

    override fun initView() {
        super.initView()
        binding.recyclerDevices.layoutManager =
            GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        devicesAdapter.setSingle(false)
        binding.recyclerDevices.adapter = devicesAdapter
        devicesAdapter.setOnItemClickListener(this)
    }

    override fun initData() {
        super.initData()
        acceptedDevices.clear()
        viewModel.getAcceptedDevices()
        // 测试信息
        acceptedDevices.add(DeviceData(1, "jjjjjjjjjkkkkklll", "Test-share-device1"))
        acceptedDevices.add(DeviceData(2, "jjjjjjjjjkkkkklll", "Test-share-device2"))
        acceptedDevices.add(DeviceData(3, "jjjjjjjjjkkkkklll", "Test-share-device3"))
        acceptedDevices.add(DeviceData(4, "jjjjjjjjjkkkkklll", "Test-share-device4"))
        acceptedDevices.add(DeviceData(5, "jjjjjjjjjkkkkklll", "Test-share-device5"))
        devicesAdapter.data = acceptedDevices
    }

    override fun initListener() {
        super.initListener()

        binding.bottomButton.setOnClickListener {
            if (devicesAdapter.getSelectState()) {
                // 判断是否有选中要分享的设备
                val found = acceptedDevices.any { it.selected }
                if (found) {
                    showDeleteDialog()
                } else {
                    ToastUtils.showShort(com.blackview.common_res.R.string.please_select_delete)
                }
            } else {
                devicesAdapter.setSelectState(true)
                devicesAdapter.notifyDataSetChanged()
                binding.bottomButton.text =
                    getString(com.blackview.common_res.R.string.button_text_delete_device)
            }
        }

    }

    /**
     * 显示删除弹窗
     */
    private fun showDeleteDialog() {
        var title = ""
        if (selectedDevices().size > 1) {
            title = getString(com.blackview.common_res.R.string.message_dialog_title)
        } else if (selectedDevices().size == 1) {
            title = getString(com.blackview.common_res.R.string.message_dialog_title_1)
            title = String.format(title, selectedDevices()[0].nick_name)
        }
        val messageDialog = MessageDialog(requireContext())
            .setMesTitle(title)
            .onConfirm {
                viewModel.deleteAcceptedDevices(deviceIds().toTypedArray())
                resetLayout()
            }.onCancel {
                resetLayout()
            }
        XPopup.Builder(requireContext())
            .dismissOnTouchOutside(false)
            .dismissOnBackPressed(true)
            .isViewMode(true)
            .asCustom(messageDialog)
            .show()

    }

    /**
     * 获取选中的设备列表
     * 可以多选
     */
    private fun selectedDevices(): MutableList<DeviceData> {
        val devices = mutableListOf<DeviceData>()
        for (device in acceptedDevices) {
            if (device.selected) {
                devices.add(device)
            }
        }
        return devices
    }

    /**
     * 获得选取的设备的ID列表
     */
    private fun deviceIds():MutableList<Int>{
        val deviceIds= mutableListOf<Int>()
        for (device in selectedDevices()){
            deviceIds.add(device.device_id)
        }
        return deviceIds
    }


    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.deleteActionResult.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    showSuccessTips(getString(com.blackview.common_res.R.string.text_delete_success))
                }
            }
        }
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        Logger.i("点击当前设备：$position;-${acceptedDevices[position].nick_name}")
        if (devicesAdapter.getSelectState()) {
            val currentShareDev = acceptedDevices[position]
            val state = currentShareDev.selected
            acceptedDevices[position].selected = !state
            devicesAdapter.notifyDataSetChanged()
        }
    }

    /**
     * 清除设备列表的所有选中状态
     */
    private fun clearDevicesStatus() {
        for (device in acceptedDevices) {
            device.selected = false
        }
    }

    /**
     * 将UI恢复到选择设备分享前的状态
     */
    private fun resetLayout() {
        clearDevicesStatus()
        devicesAdapter.setSelectState(false)
        binding.bottomButton.text =
            getString(com.blackview.common_res.R.string.button_text_select_device)
        devicesAdapter.notifyDataSetChanged()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        Logger.i("当前fragment是否隐藏:$hidden")
        if (!hidden) {
            resetLayout()
        }
    }


}