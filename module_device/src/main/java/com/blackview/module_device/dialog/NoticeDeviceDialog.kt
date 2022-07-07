package com.blackview.module_device.dialog

import android.app.Dialog
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import androidx.fragment.app.DialogFragment
import com.blackview.module_device.R
import com.blackview.repository.entity.Device
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class NoticeDeviceDialog : DialogFragment() {

    private var device: Device? = null
    var isSelect = true

    fun newInstance(device: Device): NoticeDeviceDialog {
        val args = Bundle()
        args.putParcelable("device", device)
        val fragment = NoticeDeviceDialog()
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
        val view = LayoutInflater.from(context).inflate(R.layout.dialog_device_notice_device, null)
        dialog.setContentView(view)
        initView(view)
        return dialog
    }

    private fun initView(rootView: View) {

        val tvCancel = rootView.findViewById<TextView>(R.id.btn_notice_device_go1)
        val tvNext = rootView.findViewById<TextView>(R.id.btn_notice_device_go2)
        val selectAll = rootView.findViewById<TextView>(R.id.tv_notice_select_all)

        val switchCompat1 = rootView.findViewById<SwitchCompat>(R.id.notice_switch1)
        val switchCompat2 = rootView.findViewById<SwitchCompat>(R.id.notice_switch2)
        val switchCompat3 = rootView.findViewById<SwitchCompat>(R.id.notice_switch3)
        val switchCompat4 = rootView.findViewById<SwitchCompat>(R.id.notice_switch4)
        val switchCompat5 = rootView.findViewById<SwitchCompat>(R.id.notice_switch5)
        val switchCompat6 = rootView.findViewById<SwitchCompat>(R.id.notice_switch6)
        val switchCompat7 = rootView.findViewById<SwitchCompat>(R.id.notice_switch7)
        val switchCompat8 = rootView.findViewById<SwitchCompat>(R.id.notice_switch8)
        val switchCompat9 = rootView.findViewById<SwitchCompat>(R.id.notice_switch9)
        val switchCompat10 = rootView.findViewById<SwitchCompat>(R.id.notice_switch10)
        val switchCompat11 = rootView.findViewById<SwitchCompat>(R.id.notice_switch11)
        val switchCompat12 = rootView.findViewById<SwitchCompat>(R.id.notice_switch12)
        val switchCompat13 = rootView.findViewById<SwitchCompat>(R.id.notice_switch13)
        val switchCompat14 = rootView.findViewById<SwitchCompat>(R.id.notice_switch14)

        val listSwitch = listOf(
            switchCompat1, switchCompat2, switchCompat3, switchCompat4,
            switchCompat5, switchCompat6, switchCompat7, switchCompat8,
            switchCompat9, switchCompat10, switchCompat11, switchCompat12,
            switchCompat13, switchCompat14
        )

        tvCancel.setOnClickListener { dismiss() }

        tvNext.setOnClickListener {

        }

        selectAll.setOnClickListener {
            if (isSelect) {
                isSelect = false
                selectAll.text = getString(com.blackview.common_res.R.string.cancel)
                listSwitch.forEach {
                    it.isChecked = true
                }
            } else {
                isSelect = true
                selectAll.text = getString(com.blackview.common_res.R.string.all_select)
                listSwitch.forEach {
                    it.isChecked = false
                }
            }

        }

    }
}
