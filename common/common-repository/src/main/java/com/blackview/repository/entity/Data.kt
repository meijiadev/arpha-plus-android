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
    val email: String,
    val img_url: String,
    val mobile: String,
    val name: String,
    val oauth: Boolean
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