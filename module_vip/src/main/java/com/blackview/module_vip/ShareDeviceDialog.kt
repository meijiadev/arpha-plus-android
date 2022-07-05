package com.blackview.module_vip

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.lxj.xpopup.core.CenterPopupView


/**
 *    author : MJ
 *    time   : 2022/07/05
 *    desc   : 分享设备的弹窗
 */
class ShareDeviceDialog(context: Context) : CenterPopupView(context) {

    private val tvTitle: AppCompatTextView by lazy { findViewById(R.id.tvTitle) }

    private val etPhone: EditText by lazy { findViewById(R.id.etPhone) }

    private val btJoin: AppCompatButton by lazy { findViewById(R.id.btJoin) }

    private val recyclerFriend: RecyclerView by lazy { findViewById(R.id.recyclerFriend) }

    private val btCancel: AppCompatButton by lazy { findViewById(R.id.btCancel) }

    private val btConfirm: AppCompatButton by lazy { findViewById(R.id.btConfirm) }


    private var onJoinAction: ((data: String) -> Unit)? = null

    private var onCancelAction: (() -> Unit)? = null

    private var onConfirmAction: (() -> Unit)? = null


    override fun getImplLayoutId(): Int = R.layout.dialog_share_device

    override fun onCreate() {
        super.onCreate()
        tvTitle.post {
            val drawable: Drawable =
                resources.getDrawable(com.blackview.common_res.R.drawable.yellow_line_l)
            val width = tvTitle.width
            drawable.setBounds(0, 0, width, 10)
            tvTitle.setCompoundDrawables(null, null, null, drawable)
        }

        btJoin.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            if (!phone.isNullOrEmpty()) {
                onJoinAction?.invoke(phone)
            } else {
                ToastUtils.showShort(com.blackview.common_res.R.string.input_phone)
            }
        }

        btCancel.setOnClickListener {
            onCancelAction?.invoke()
        }

        btConfirm.setOnClickListener {
            onConfirmAction?.invoke()
        }
    }

    fun onJoin(action: ((data: String) -> Unit)): ShareDeviceDialog = apply {
        onJoinAction = action
    }

    fun onCancel(action: () -> Unit): ShareDeviceDialog = apply {
        onCancelAction = action
    }

    fun onConfirm(action: () -> Unit): ShareDeviceDialog = apply {
        onConfirmAction = action
    }


}