package com.blackview.base

import android.app.Application
import com.AiPN.AiPNDataCenter
import com.alibaba.android.arouter.launcher.ARouter
import com.blackview.contant.USER_TOKEN
import com.blackview.util.L
import com.blackview.util.SpUtil
import com.orhanobut.logger.*
import com.tencent.mmkv.MMKV

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * Created by home on 2022/5/30.
 */
class App : Application() {

    companion object {
        lateinit var instance: App
        lateinit var token: String
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        if (BuildConfig.DEBUG) {
            ARouter.openLog()
            ARouter.openDebug()
        }
        ARouter.init(instance)
        MMKV.initialize(instance)
        token = SpUtil.decodeString(USER_TOKEN) ?: ""
        L.e("token:$token")
        AiPNDataCenter.getInstance().configAiPNSDK(instance)
        initLog()

    }

    private fun initLog(){
        // 初始化日志打印
        if (BuildConfig.DEBUG) {
            val formatStrategy= PrettyFormatStrategy
                .newBuilder()
                .showThreadInfo(false)
                .methodCount(2)
                .tag("arpha-plus-debug")
                .build()
            Logger.addLogAdapter(AndroidLogAdapter(formatStrategy))
        }
    }


}