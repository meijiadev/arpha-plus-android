package com.blackview.repository.entity

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


data class ProductList(
    var product_types: List<ProductType>
)

data class ProductType(
    val name: String,
    var isSelect:Boolean=false
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