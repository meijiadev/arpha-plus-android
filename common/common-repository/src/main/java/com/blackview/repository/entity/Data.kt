package com.blackview.repository.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class PhoneInfo(
    val city: String,
    val province: String,
    val sp: String
)

/**
 *    author : MJ
 *    time   : 2022/06/30
 *    desc   : 会员的信息
 */
data class VipMemberInfo(
    val account: String,
    val img_url: String,
    val name: String,
    val oauth: Boolean,
    val location:String

)

/**
 * author : MJ
 * time   : 2022/07/06
 * desc   : 设备信息
 */
data class DeviceData(
    val device_id: Int,
    val did: String,
    val nick_name: String,
    var selected:Boolean=false
)

/**
 * author: MJ
 * time  : 2022/07/06
 * desc  : 共享的用户列表
 */
data class ShareMember(
    val member_id:Int,
    val name:String
)

/**
 * author : MJ
 * time   : 2022/07/08
 * desc   : 装置通知设定配置
 */
data class SettingsData(
    val notifications: Notifications
)



data class ProductList(
    var product_types: List<ProductType>
)

data class ProductType(
    val name: String,
    var isSelect: Boolean = false
)


data class Products(
    val products: List<Product>
)

data class Product(
    val product_id: Int,
    val product_name: String,
    val serial: String,
    val webs: List<String>
)

data class Devices(
    val devices: List<Device>
)

@Parcelize
data class Device(
    var device_name: String,
    val did: String,
    val id: Int,
    val notifications: Notifications,
    val power: Int,
    val wifi: Int,
    val img_url:String
) : Parcelable

@Parcelize
data class Notifications(
    var door_alert: Boolean,
    var door_bell: Boolean,
    var door_open: Boolean
) : Parcelable

data class Dd(
    val device_id: Int,
    val notifications: Noti
)

data class Noti(
    val door_bell: Boolean,
    val door_open: Boolean,
    val door_alert: Boolean
)