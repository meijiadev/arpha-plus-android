package com.blackview.arphaplus.constants

import com.blackview.arphaplus.BuildConfig


/**
 *    author : MJ
 *    time   : 2022/03/01
 *    desc   : 全局配置参数
 */
object AppConfig {

    /**
     * 微信 AppId
     */
    private const val WX_APP_ID = "wx1cf3bf2a1deac41f"

    /***
     * 微信 Secret
     */
    private const val WX_APP_SECRET = "8b5f1400b7d0f181c87c75588a75d3fb"

    /**
     * 获取微信code的key
     */
    const val WX_CODE = "WX_CODE"

    /**
     * 用于存储登录的token，key
     */
    const val APP_TOKEN = "token"

    /**
     * token为空
     */
    const val TOKEN_NULL="null"

    /**
     * 注册成功后该用户的userId
     */
    const val APP_USER_ID="userId"



    /**
     * 当前是否为调试模式
     */
    fun isDebug(): Boolean {
        return BuildConfig.DEBUG
    }

    /**
     * 获取当前构建的模式
     */
    fun getBuildType(): String {
        return BuildConfig.BUILD_TYPE
    }

    /**
     * 当前是否要开启日志打印功能
     */
    fun isLogEnable(): Boolean {
        return BuildConfig.LOG_ENABLE
    }

    /**
     * 获取当前应用的包名
     */
    fun getPackageName(): String{
        return BuildConfig.APPLICATION_ID
    }

    /**
     * 获取当前应用的版本名
     */
    fun getVersionName(): String {
        return BuildConfig.VERSION_NAME
    }

    /**
     * 获取当前应用的版本码
     */
    fun getVersionCode(): Int {
        return BuildConfig.VERSION_CODE
    }

    /**
     * 获取 Bugly Id
     */
    fun getBuglyId(): String {
        return BuildConfig.BUGLY_ID
    }

    /**
     * 获取服务器主机地址
     */
    fun getHostUrl(): String {
        return BuildConfig.HOST_URL
    }

    /**
     * @return 获取微信appid
     */
    fun getWxAppId(): String {
        return WX_APP_ID
    }

    /**
     * @return wx secret
     */
    fun getWxAppSecret(): String {
        return WX_APP_SECRET
    }

    /**
     * 获取bugly AppId
     * @return
     */
    fun getBuglyAppId(): String {
        return BuildConfig.BUGLY_ID
    }
}