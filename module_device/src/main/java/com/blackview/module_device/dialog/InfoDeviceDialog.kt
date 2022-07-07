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
import com.blackview.module_device.R
import com.blackview.repository.entity.Device
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoDeviceDialog : DialogFragment() {

    private var device: Device? = null

    fun newInstance(device: Device): InfoDeviceDialog {
        val args = Bundle()
        args.putParcelable("device", device)
        val fragment = InfoDeviceDialog()
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
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_device_info_device, null)
        dialog.setContentView(view)
        initView(view)
        return dialog
    }

    private fun initView(rootView: View) {
        
       


    }
}
