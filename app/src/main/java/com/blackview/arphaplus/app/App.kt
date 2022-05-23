package com.blackview.arphaplus.app

import android.content.Context
import com.blackview.arphaplus.constants.AppConfig.getBuglyAppId
import com.blackview.arphaplus.constants.AppConfig.isDebug
import com.blackview.arphaplus.constants.AppConfig.isLogEnable
import com.example.architecture.BaseApplication
import com.hjq.bar.TitleBar
import com.hjq.bar.style.LightBarStyle
import com.hjq.toast.ToastUtils
import com.tencent.bugly.crashreport.CrashReport
import timber.log.Timber


/**
 *    author : MJ
 *    time   : 2022/03/02
 *    desc   : 程序入口
 */
class App : BaseApplication() {


    companion object {
        lateinit var appContext: Context
        lateinit var application: App

        /**
         * 判断当前环境是否是中国
         */
        fun isZh(): Boolean {
            if (appContext.resources.configuration.locale.language.endsWith("zh")) {
                return true
            }
            return false
        }
    }

    override fun onCreate() {
        super.onCreate()
        initSdk(this)
    }

    /**
     * 初始化Sdk
     */
    private fun initSdk(app: App) {
        application = app
        appContext = app
        // 初始化日志打印
        if (isLogEnable()) {
            Timber.plant()

        }
        // 建议在测试阶段建议设置成true，发布时设置为false
        CrashReport.initCrashReport(applicationContext, getBuglyAppId(), true)
        TitleBar.setDefaultStyle(LightBarStyle())
        SharedPreferencesManager.init(appContext.applicationContext)
        // 初始化吐司
        ToastUtils.init(app)
        // 设置调试模式
        ToastUtils.setDebugMode(isDebug())
    }
}