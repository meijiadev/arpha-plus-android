package com.blackview.module_vip.dialog

import android.content.Context
import android.opengl.Visibility
import androidx.appcompat.widget.AppCompatTextView
import com.blackview.module_vip.R
import com.lxj.xpopup.core.CenterPopupView


/**
 *    author : MJ
 *    time   : 2022/07/07
 *    desc   : 变更成功提示弹窗
 */
class TipsDialog(context: Context) : CenterPopupView(context) {

    private val tvMessage: AppCompatTextView by lazy { findViewById(R.id.tvMessage) }

    private var message: String? = null

    // draw 图标是否显示
    private var drawIsShow: Boolean = true


    override fun getImplLayoutId(): Int = R.layout.dialog_tips

    override fun onCreate() {
        super.onCreate()
    }


    fun setMessage(msg: String): TipsDialog = apply {
        this.message = msg
    }

    fun setDrawVisible(visibility: Boolean): TipsDialog = apply {
        drawIsShow = visibility
    }

    override fun init() {
        super.init()
        message?.let {
            tvMessage.text = it
        }
        tvMessage.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            0,
            if (drawIsShow)
                com.blackview.common_res.R.drawable.image_success_60
            else
                0
        )

    }
}