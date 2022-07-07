package com.blackview.repository

import android.widget.ImageView
import coil.load
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation

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
 * Created by home on 2022/7/7.
 */
fun ImageView.showImgCircle(url: String) {
    load(url) {
        crossfade(true) //渐进进出
        placeholder(com.blackview.common_res.R.drawable.image_home_80) //加载中占位图
        transformations(CircleCropTransformation())  //圆形图
        error(com.blackview.common_res.R.drawable.image_home_80) //加载错误占位图
    }
}

fun ImageView.showImgRound(url: String) {
    load(url) {
        crossfade(true) //渐进进出
        placeholder(com.blackview.common_res.R.drawable.image_home_80) //加载中占位图
        transformations(RoundedCornersTransformation(20f))  //圆形图
        error(com.blackview.common_res.R.drawable.image_home_80) //加载错误占位图
    }
}