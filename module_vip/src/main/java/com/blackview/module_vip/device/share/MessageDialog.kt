package com.blackview.module_vip.device.share

import android.content.Context
import android.widget.TextView
import com.blackview.module_vip.R
import com.caldremch.widget.round.RoundTextView
import com.lxj.xpopup.core.CenterPopupView
import org.w3c.dom.Text


/**
 *    author : MJ
 *    time   : 2022/07/08
 *    desc   : 信息确定弹窗
 */
class MessageDialog(context: Context) : CenterPopupView(context) {

    private var onCancelAction: (() -> Unit)? = null

    private var onConfirmAction: (() -> Unit)? = null

    private val tvMesTitle: TextView by lazy { findViewById(R.id.tvMesTitle) }

    private val tvCancel: RoundTextView by lazy { findViewById(R.id.tvCancel) }

    private val tvConfirm: RoundTextView by lazy { findViewById(R.id.tvConfirm) }

    private var message=""


    override fun getImplLayoutId(): Int = R.layout.dialog_message


    override fun onCreate() {
        super.onCreate()

        tvCancel.setOnClickListener {
            onCancelAction?.invoke()
            dismiss()
        }

        tvConfirm.setOnClickListener {
            onConfirmAction?.invoke()
            dismiss()
        }
    }

    override fun init() {
        super.init()
        tvMesTitle.text=message
    }

    fun onCancel(action: () -> Unit):MessageDialog=apply {
        onCancelAction = action
    }

    fun onConfirm(action: () -> Unit):MessageDialog=apply {
        onConfirmAction = action
    }

    fun setMesTitle(msg:String):MessageDialog=apply{
        message=msg
    }
}