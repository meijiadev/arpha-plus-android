package com.blackview.module_device.dialog

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.blackview.contant.device
import com.blackview.module_device.DeviceActivity
import com.blackview.module_device.DeviceFragment
import com.blackview.module_device.DeviceModel
import com.blackview.module_device.R
import com.blackview.repository.entity.Device
import com.blackview.util.L
import com.blackview.util.toastShort
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeviceBottomDialog(private val model: DeviceModel, var device: Device, var pos: Int) :
    BottomSheetDialogFragment() {

    private var delDeviceDialog: DelDeviceDialog? = null
    private var noticeDeviceDialog: NoticeDeviceDialog? = null
    private var deviceDialog1: DeviceDialog? = null
    private var deviceDialog2: DeviceDialog? = null
    private var infoDeviceDialog: InfoDeviceDialog? = null


    interface DismissListener {
        fun onDismiss()
    }

    var listener: DismissListener? = null

    fun setDismissListener(listener: DismissListener) {
        this.listener = listener
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        if (listener != null) {
            listener!!.onDismiss()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.blackview.base.R.style.BottomSheetDialog)
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
            if (delDeviceDialog == null) {
                delDeviceDialog = DelDeviceDialog(model,device)
            }
            delDeviceDialog?.show(requireActivity().supportFragmentManager, "del")
        }

        tvNotice.setOnClickListener {
            if (noticeDeviceDialog == null) {
                noticeDeviceDialog = NoticeDeviceDialog(model, device, pos)
            }
            noticeDeviceDialog?.show(requireActivity().supportFragmentManager, "notice")
        }

        tvName.setOnClickListener {
            if (deviceDialog1 == null) {
                deviceDialog1 = DeviceDialog(1, device, model, pos)
            }
            deviceDialog1?.show(requireActivity().supportFragmentManager, "name1")
        }

        tvAllPermissions.setOnClickListener {
            if (deviceDialog2 == null) {
                deviceDialog2 = DeviceDialog(2, device, model, pos)
            }
            deviceDialog2?.show(requireActivity().supportFragmentManager, "name2")
        }

        tvCheckNews.setOnClickListener {
            if (infoDeviceDialog == null) {
                infoDeviceDialog = InfoDeviceDialog()
            }
            infoDeviceDialog?.show(requireActivity().supportFragmentManager, "info1")
        }

    }
}
