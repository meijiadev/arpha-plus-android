package com.blackview.module_vip.dialog

import android.content.Context
import com.blackview.module_vip.R
import com.lxj.xpopup.core.CenterPopupView


/**
 *    author : MJ
 *    time   : 2022/07/07
 *    desc   : 变更成功提示弹窗
 */
class TipsDialog(context: Context) :CenterPopupView(context) {

    override fun getImplLayoutId(): Int= R.layout.dialog_tips

    override fun onCreate() {
        super.onCreate()
    }


    override fun init() {
        super.init()
    }
}