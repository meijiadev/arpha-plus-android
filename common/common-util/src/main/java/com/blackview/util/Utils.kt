package com.blackview.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils


inline fun <reified T : Activity> Context.gotoAct() {
    val intent = Intent(this, T::class.java)
    if (this is Application) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

inline fun <reified T : Activity> Context.gotoAct(bundle: Bundle) {
    val intent = Intent(this, T::class.java)
    intent.putExtras(bundle)
    if (this is Application) {
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    }
    startActivity(intent)
}

fun gotoAct(path: String) {
    ARouter.getInstance().build(path).navigation()
}

fun gotoAct(path: String, func: Postcard.() -> Unit) = run {
    ARouter.getInstance().build(path).with {
        this.func()
    }.navigation()
}

private fun Postcard.with(func: Postcard.() -> Unit): Postcard = run {
    this.func()
    return this
}

fun toastShort(activity: Activity, msg: String) {
    ToastUtils.make().apply {
        setGravity(Gravity.CENTER, 0, 0)
        setBgColor(ContextCompat.getColor(activity, android.R.color.black))
        setTextColor(ContextCompat.getColor(activity, android.R.color.white))
        show(msg)
    }
}
