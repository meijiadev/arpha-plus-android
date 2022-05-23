package com.blackview.arphaplus.app

import android.content.Context
import com.tencent.mmkv.MMKV
import timber.log.Timber


/**
 *    author : MJ
 *    time   : 2022/02/09
 *    desc   : 使用MMkv存储数据
 */
class SharedPreferencesManager {

    companion object{
        private lateinit var  kv:MMKV

        fun init(context: Context){
            // MMKV 初始化
            val rootDir: String = MMKV.initialize(context)
            Timber.i(rootDir)
            kv= MMKV.defaultMMKV()
        }

        /**
         * 存储字符串
         * @param key 键
         * @param value 值
         */
        fun putString(key:String,value:String?){
            kv.encode(key,value)
        }

        /**
         * 获取字符串数据
         * @param key 存储时的键
         * @param defaultValue 如果没有获取到数据显示默认字符串
         */
        fun getString(key:String,defaultValue:String): String? {
            return kv.decodeString(key, defaultValue)
        }

        /**
         * 获取字符串数据
         * @param key 存储时的键
         */
        fun getString(key:String): String? {
            return kv.decodeString(key,"")
        }

        /**
         * 存储字符串
         * @param key 键
         * @param value 值
         */
        fun putInt(key:String,value:Int){
            kv.encode(key,value)
        }

        /**
         * 获取字符串数据
         * @param key 存储时的键
         * @param defaultValue 如果没有获取到数据显示默认value
         */
        fun getInt(key:String,defaultValue:Int): Int {
            return kv.decodeInt(key, defaultValue)
        }

        /**
         * 获取字符串数据
         * @param key 存储时的键
         */
        fun getInt(key:String): Int {
            return kv.decodeInt(key,0)
        }




    }

}