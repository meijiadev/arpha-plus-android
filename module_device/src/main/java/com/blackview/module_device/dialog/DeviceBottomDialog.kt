package com.blackview.module_device.dialog

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.blackview.module_device.R
import com.blackview.repository.entity.Device
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeviceBottomDialog : BottomSheetDialogFragment() {

    private var device: Device? = null

    fun newInstance(device: Device): DeviceBottomDialog {
        val args = Bundle()
        args.putParcelable("device", device)
        val fragment = DeviceBottomDialog()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.blackview.base.R.style.BottomSheetDialog)
        device = arguments?.getParcelable("device")

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_device_bottom_sheet, null)
        dialog.setContentView(view)
        initView(view)
        return dialog
    }

    private fun initView(rootView: View) {
        val tvNotice = rootView.findViewById<TextView>(R.id.tv_setting_notice)
        val tvName = rootView.findViewById<TextView>(R.id.tv_setting_name)
        val tvAllPermissions = rootView.findViewById<TextView>(R.id.tv_setting_all_permissions)
        val tvShare = rootView.findViewById<TextView>(R.id.tv_setting_share)
        val tvCheckNews = rootView.findViewById<TextView>(R.id.tv_setting_check_news)
        val tvDelete = rootView.findViewById<TextView>(R.id.tv_setting_delete)

        tvDelete.setOnClickListener {
            val delDeviceDialog = DelDeviceDialog().newInstance(device!!)
            delDeviceDialog.show(requireActivity().supportFragmentManager, "del")
        }

        tvNotice.setOnClickListener {
            val noticeDeviceDialog = NoticeDeviceDialog().newInstance(device!!)
            noticeDeviceDialog.show(requireActivity().supportFragmentManager, "notice")
        }

        tvName.setOnClickListener {
            val deviceDialog = DeviceDialog().newInstance(device!!, getString(com.blackview.common_res.R.string.button_tips2))
            deviceDialog.show(requireActivity().supportFragmentManager, "name1")
        }

        tvAllPermissions.setOnClickListener {
            val deviceDialog = DeviceDialog().newInstance(device!!, getString(com.blackview.common_res.R.string.button_tips3))
            deviceDialog.show(requireActivity().supportFragmentManager, "name2")
        }

        tvCheckNews.setOnClickListener {
            val infoDeviceDialog = InfoDeviceDialog().newInstance(device!!)
            infoDeviceDialog.show(requireActivity().supportFragmentManager, "info1")
        }

    }
}
