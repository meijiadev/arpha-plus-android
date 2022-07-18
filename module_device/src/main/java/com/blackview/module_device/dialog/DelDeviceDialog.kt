package com.blackview.module_device.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.blackview.module_device.DeviceModel
import com.blackview.module_device.R
import com.blackview.repository.entity.Device
import com.blackview.util.toastShort
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DelDeviceDialog(var model: DeviceModel, var device: Device) : DialogFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.blackview.base.R.style.BottomSheetDialog)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_device_del_device, null)
        dialog.setContentView(view)
        initView(view)
        return dialog
    }

    private fun initView(rootView: View) {
        val pwd = rootView.findViewById<TextView>(R.id.tv_del_device_pwd)
        val checkBox = rootView.findViewById<CheckBox>(R.id.cb_del_device_pwd)
        val tvCancel = rootView.findViewById<TextView>(R.id.btn_del_device_go1)
        val tvNext = rootView.findViewById<TextView>(R.id.btn_del_device_go2)

        checkBox.setOnCheckedChangeListener { _, b ->
            if (b) {
                pwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                pwd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        tvCancel.setOnClickListener { dismiss() }

        tvNext.setOnClickListener {
            if (pwd.text.toString().trim().isEmpty()) {
                toastShort(requireActivity(), getString(com.blackview.common_res.R.string.input_pwd))
            } else {
                model.deleteDevice(device.id, pwd.text.toString().trim())
            }
        }

        var list = ArrayList<Device>()
        model.liveDevices.observe(this) {
            list = it as ArrayList<Device>
        }
        model.deleteDeviceEvent.observe(this) {
            toastShort(requireActivity(), getString(com.blackview.common_res.R.string.success))
            dismiss()
            list.remove(device)
            model.liveDevices.postValue(list)
        }
        
    }
}
