package com.blackview.module_device.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.contant.device
import com.blackview.module_device.DeviceModel
import com.blackview.module_device.R
import com.blackview.repository.entity.Device
import com.blackview.util.toastShort
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeviceDialog(var type: Int, var device: Device, var model: DeviceModel,var pos:Int) : DialogFragment() {

    lateinit var viewModel: DeviceModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.blackview.base.R.style.BottomSheetDialog)
        viewModel = ViewModelProvider(this).get(DeviceModel::class.java)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_device_device, null)
        dialog.setContentView(view)
        initView(view)
        return dialog
    }

    private fun initView(rootView: View) {
        val tvTitle = rootView.findViewById<TextView>(R.id.tv_device_notice_title)
        val editText = rootView.findViewById<TextView>(R.id.et_device_name)
        val tvCancel = rootView.findViewById<TextView>(R.id.btn_device_go1)
        val tvNext = rootView.findViewById<TextView>(R.id.btn_device_go2)

        when (type) {
            1 -> {
                tvTitle.text = getString(com.blackview.common_res.R.string.button_tips2)
                editText.hint = getString(com.blackview.common_res.R.string.input_device_name)
                editText.inputType = InputType.TYPE_CLASS_TEXT
                tvNext.setOnClickListener {
                    if (editText.text.toString().trim().isEmpty()) {
                        toastShort(requireActivity(), getString(com.blackview.common_res.R.string.input_device_name))
                    } else {
                        viewModel.changeDeviceName(device.id, editText.text.toString().trim())
                    }
                }
            }
            2 -> {
                tvTitle.text = getString(com.blackview.common_res.R.string.button_tips3)
                editText.hint = getString(com.blackview.common_res.R.string.input_phone)
                editText.inputType = InputType.TYPE_CLASS_PHONE
                tvNext.setOnClickListener {
                    if (editText.text.toString().trim().isEmpty()) {
                        toastShort(requireActivity(), getString(com.blackview.common_res.R.string.input_phone))
                    } else {

                    }
                }
            }
        }

        tvCancel.setOnClickListener { dismiss() }

        var list = ArrayList<Device>()
        model.liveDevices.observe(this) {
            list = it as ArrayList<Device>
        }
        
        viewModel.changeDeviceNameEvent.observe(this) {
            toastShort(requireActivity(), getString(com.blackview.common_res.R.string.success))
            dismiss()
            device.device_name=editText.text.toString().trim()
            list[pos] = device
            model.liveDevices.postValue(list)
        }
    }
}
