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
import com.blackview.module_device.DeviceModel
import com.blackview.module_device.R
import com.blackview.repository.entity.Device
import com.blackview.util.toastShort
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class DeviceDialog : DialogFragment() {

    private var device: Device? = null
    lateinit var viewModel: DeviceModel

    fun newInstance(device: Device, title: String): DeviceDialog {
        val args = Bundle()
        args.putParcelable("device", device)
        args.putString("title", title)
        val fragment = DeviceDialog()
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.blackview.base.R.style.BottomSheetDialog)
        device = arguments?.getParcelable("device")
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
        val title = arguments?.getString("title")
        tvTitle.text = title


        if (getString(com.blackview.common_res.R.string.button_tips3) == title) {
            editText.hint = getString(com.blackview.common_res.R.string.input_phone)
            editText.inputType = InputType.TYPE_CLASS_PHONE

            tvNext.setOnClickListener {
                if (editText.text.toString().trim().isEmpty()) {
                    toastShort(requireActivity(), getString(com.blackview.common_res.R.string.input_phone))
                } else {

                }
            }

        } else {
            editText.hint = getString(com.blackview.common_res.R.string.input_device_name)
            editText.inputType = InputType.TYPE_CLASS_TEXT
            tvNext.setOnClickListener {
                if (editText.text.toString().trim().isEmpty()) {
                    toastShort(requireActivity(), getString(com.blackview.common_res.R.string.input_device_name))
                } else {
                    viewModel.changeDeviceName(device?.id!!, editText.text.toString().trim())
                }
            }

        }

        tvCancel.setOnClickListener { dismiss() }

        viewModel.changeDeviceNameEvent.observe(this) {
            toastShort(requireActivity(), getString(com.blackview.common_res.R.string.success))
            dismiss()
        }
    }
}
