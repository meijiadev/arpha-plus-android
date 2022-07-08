package com.blackview.module_vip.device.mydevice

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.base.base.BaseMVFragment
import com.blackview.module_vip.dialog.ShareDeviceDialog
import com.blackview.module_vip.adapter.DevicesAdapter
import com.blackview.module_vip.databinding.FragmentDevicesBinding
import com.blackview.module_vip.dialog.TipsDialog
import com.blackview.repository.entity.DeviceData
import com.blackview.repository.entity.ShareMember
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   : 2022\07\05
 *    desc   : 我的设备
 */
class MyDevicesFragment : BaseMVFragment<FragmentDevicesBinding, MyDevicesModel>(),
    OnItemClickListener {

    private val devicesAdapter: DevicesAdapter by lazy { DevicesAdapter() }

    /**
     * 该设备共享的好友列表
     */
    private var membersList= mutableListOf<ShareMember>()

    /**
     * 自身的设备列表
     */
    private var devicesData = mutableListOf<DeviceData>()

    /**
     * 当前选中要分享的设备
     */
    private lateinit var currentShareDev:DeviceData

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
                if (found) {
                    // 获取该设备的共享者列表
                    viewModel.getShareList(currentShareDev.device_id.toString())
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
                membersList=it
                // 测试专用
                if (membersList.size==0){
                    membersList.add(ShareMember(1,"test1"))
                    membersList.add(ShareMember(2,"test2"))
                    membersList.add(ShareMember(3,"test3"))
                    membersList.add(ShareMember(4,"test4"))
                    membersList.add(ShareMember(5,"test5"))
                    membersList.add(ShareMember(6,"test6"))
                }
                showShareDialog()
            }
        }

        viewModel.sharDevicesSuccess.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    showSuccessTips()
                }

            }
        }


    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        Logger.i("点击当前设备：$position;-${devicesData[position].nick_name}")
        if (devicesAdapter.getSelectState()){
            clearDevicesStatus()
            currentShareDev=devicesData[position]
            val state = currentShareDev.selected
            devicesData[position].selected = !state
            devicesAdapter.notifyDataSetChanged()
        }

    }

    private fun showShareDialog() {
        val shareDeviceDialog = ShareDeviceDialog(requireContext())
            .onJoin {
                Logger.i("校验的phone:$it")
                viewModel.checkMember(it)
            }
            .onCancel {
                Logger.i("取消")
                resetLayout()

            }
            .onConfirm {
                //clearDevicesStatus()
                if (it?.size>0){
                    // 将设备共享给选择的朋友
                    viewModel.shareDevices(currentShareDev.device_id,it)
                }else{
                    resetLayout()
                    ToastUtils.showShort(com.blackview.common_res.R.string.please_add_member)
                }
            }
            .setData(membersList)
        XPopup.Builder(requireContext())
            .isViewMode(true)
            .dismissOnTouchOutside(false)
            .popupAnimation(PopupAnimation.TranslateFromBottom)
            .asCustom(shareDeviceDialog)
            .show()
    }

    private fun showSuccessTips(){
        XPopup.Builder(requireContext())
            .isViewMode(true)
            .dismissOnTouchOutside(false)
            .popupAnimation(PopupAnimation.TranslateFromBottom)
            .asCustom(TipsDialog(requireContext()))
            .show()
            .delayDismiss(3000)

    }


    /**
     * 清除设备列表的所有选中状态
     */
    private fun clearDevicesStatus(){
        for (device in devicesData){
            device.selected=false
        }
    }

    /**
     * 将UI恢复到选择设备分享前的状态
     */
    private fun resetLayout(){
        clearDevicesStatus()
        devicesAdapter.setSelectState(false)
        binding.bottomButton.text=getString(com.blackview.common_res.R.string.button_text_select_device)
        devicesAdapter.notifyDataSetChanged()
    }


}