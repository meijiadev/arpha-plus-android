package com.blackview.repository.entity

data class PhoneInfo(
    val city: String,
    val province: String,
    val sp: String
)
data class LoginModel(
    val admin: Boolean?,
    val chapterTops: List<Any>?,
    val email: String?,
    val icon: String?,
    val id: Int?,
    val nickname: String?,
    val publicName: String?,
    val username: String?
)