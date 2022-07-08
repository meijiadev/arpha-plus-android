package com.blackview.module_vip.dialog

import android.app.Activity
import androidx.fragment.app.Fragment

import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation


/**
 *    author : MJ
 *    time   : 2022/07/08
 *    desc   : 通用弹窗
 */

 fun Fragment.showSuccessTips(msg:String=getString(com.blackview.common_res.R.string.text_change_success)){
    XPopup.Builder(requireContext())
        .isViewMode(true)
        .dismissOnTouchOutside(false)
        .popupAnimation(PopupAnimation.TranslateFromBottom)
        .asCustom(TipsDialog(requireContext()).setMessage(msg))
        .show()
        .delayDismiss(3000)

}

fun Activity.showSuccessTips(msg:String=getString(com.blackview.common_res.R.string.text_change_success)){
    com.lxj.xpopup.XPopup.Builder(this)
        .isViewMode(true)
        .dismissOnTouchOutside(false)
        .popupAnimation(com.lxj.xpopup.enums.PopupAnimation.TranslateFromBottom)
        .asCustom(TipsDialog(this).setMessage(msg))
        .show()
        .delayDismiss(3000)

}