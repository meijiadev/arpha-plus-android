package com.blackview.module_vip.dialog

import android.content.Context
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


    override fun getImplLayoutId(): Int = R.layout.dialog_tips

    override fun onCreate() {
        super.onCreate()
    }


    fun setMessage(msg:String):TipsDialog=apply {
        this.message=msg
    }

    override fun init() {
        super.init()
        message?.let {
            tvMessage.text=it
        }
    }
}