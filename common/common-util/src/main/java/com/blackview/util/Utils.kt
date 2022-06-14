package com.blackview.util

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.launcher.ARouter

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