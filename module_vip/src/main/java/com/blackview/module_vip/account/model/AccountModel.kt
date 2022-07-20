package com.blackview.module_vip.account.model

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.base.SingleLiveEvent
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheckAndError
import com.blackview.repository.vipService
import com.orhanobut.logger.Logger
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


/**
 *    author : MJ
 *    time   : 2022/07/13
 *    desc   : 账号管理页model
 */
class AccountModel : BaseViewModel() {

    private var titleEvent = MutableLiveData<String>()

    fun titleAction(msg: String) {
        titleEvent.value = msg
    }

    fun titleEvent(): MutableLiveData<String> = titleEvent

    /**
     * 昵称修改是否成功
     */
    var nickChangeEvent = SingleLiveEvent<Boolean>()

    var headUpdateEvent = SingleLiveEvent<String>()

    /**
     * 密码变更结果
     */
    var pswUpdateEvent = SingleLiveEvent<Boolean>()

    var deleteAccountEvent = SingleLiveEvent<Boolean>()

    /**
     * 修改会员昵称
     */
    fun changeNickName(nick: String?) {
        val params = ArrayMap<Any, Any>()
        params["name"] = nick
        requestNoCheckAndError({
            vipService.changeName(params)
        }, {
            when (it.code) {
                20000 -> {
                    Logger.i("更新会员昵称成功")
                    nickChangeEvent.value = true
                }
                else -> {
                    nickChangeEvent.value = true
                }
            }
        }, {
            nickChangeEvent.value = true
        })
    }

    /**
     * 修改头像
     */
    fun changeHeadImage(headFile: File) {
        val imageBody = RequestBody.create("multipart/form-data".toMediaTypeOrNull(), headFile)
        val imagePart = MultipartBody.Part.createFormData("image", headFile.name, imageBody)
        request({
            vipService.changeHead(imagePart)
        }, {
            headUpdateEvent.value = it.img_url
        })
    }

    /**
     * 更改密码
     */
    fun changePassword(password: String, newPsw: String, newPswConfirm: String?) {
        val params = ArrayMap<Any, Any>()
        params["password"] = password
        params["new_password"] = newPsw
        params["new_password_confirmation"] = newPswConfirm
        requestNoCheckAndError({
            vipService.changePassword(params)
        }, {
            pswUpdateEvent.value = it.code == 20000
        })
    }

    /**
     * 发送验证码
     */
    fun sendCode() {
        requestNoCheckAndError({
            vipService.sendCode()
        }, {
            Logger.i("验证码发送：${it.code}")
        })
    }

    /**
     * 删除账号
     */
    fun deleteAccount(password: String, code: String) {
        val params = ArrayMap<Any, Any>()
        params["password"] = password
        params["validate_code"] = code
        requestNoCheckAndError({
            vipService.deleteAccount(params)
        }, {
            deleteAccountEvent.value = true
        }, {
            deleteAccountEvent.value = false
        }
        )
    }
}