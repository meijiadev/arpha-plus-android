package com.blackview.arphaplus.login

import android.content.res.Resources
import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.base.SingleLiveEvent
import com.blackview.base.http.requestNoCheck
import com.blackview.base.request.Region
import com.blackview.repository.httpService
import com.blackview.util.L

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
 * Created by home on 2022/6/17.
 */
class LoginModel : BaseViewModel() {

    var array: Array<Region>? = null
    var loginEvent = SingleLiveEvent<Void>()
    var registerEvent = SingleLiveEvent<Void>()
    var validCodeEvent = SingleLiveEvent<Void>()
    var forgetCheckEvent = SingleLiveEvent<Void>()
    var forgetValidateEvent = SingleLiveEvent<Void>()
    var resetPwdEvent = SingleLiveEvent<Void>()

    /**选择的国家或地区*/
    fun getRegions(resources: Resources): Array<Region> {
        array = arrayOf(
            Region("1", resources.getString(com.blackview.common_res.R.string.select_region)),
            Region("+86", resources.getString(com.blackview.common_res.R.string.china)),
            Region("+886", resources.getString(com.blackview.common_res.R.string.taiwan)),
            Region("+81", resources.getString(com.blackview.common_res.R.string.japan)),
            Region("+1", resources.getString(com.blackview.common_res.R.string.usa)),
            Region("N", resources.getString(com.blackview.common_res.R.string.email))
        )
        return array!!
    }

    fun login(country_code: String, account: String, password: String){
        val params = ArrayMap<Any, Any>()
        params["country_code"] = country_code
        params["account"] = account
        params["password"] = password
        params["manufacturer"] = "Arpha"
        requestNoCheck({ httpService.login(params)},{
            loginEvent.post()
        })
    }
    
    /**注册*/
    fun register(country_code: String, account: String, password: String) {
        val params = ArrayMap<Any, Any>()
        //必須 ex: +86(china), +886(taiwan), +81(japan)... 如果是email請填入N(例如美國)
        params["country_code"] = country_code
        params["account"] = account
        params["password"] = password
        params["manufacturer"] = "Arpha"
        L.e(params.toString())
        requestNoCheck({ httpService.register(params) }, {
            registerEvent.post()
        })
    }

    /**注册--验证码*/
    fun validCode(code: String) {
        requestNoCheck({
            httpService.validCode(ArrayMap<Any, Any>().apply {
                this["validate_code"] = code
            })
        }, {
            validCodeEvent.post()
        })
    }

    /**忘记密码-获取验证码*/
    fun forgetCheck(country_code: String, account: String) {
        val params = ArrayMap<Any, Any>()
        //必須 ex: +86(china), +886(taiwan), +81(japan)... 如果是email請填入N(例如美國)
        params["country_code"] = country_code
        params["account"] = account
        params["manufacturer"] = "Arpha"
        params["test"] = true
        requestNoCheck({ httpService.forgetCheck(params) }, {
            forgetCheckEvent.post()
        })
    }

    /**忘记密码-验证验证码*/
    fun forgetValidate(code: String) {
        requestNoCheck({
            httpService.forgetValidate(ArrayMap<Any, Any>().apply {
                this["validate_code"] = code
            })
        }, {
            forgetValidateEvent.post()
        })
    }

    /**重设密码*/
    fun resetPwd(password1: String, password2: String) {
        val params = ArrayMap<Any, Any>()
        params["password"] = password1
        params["password_confirmation"] = password2
        params["reset_token"] = ""
        requestNoCheck({ httpService.resetPwd(params) }, {
            resetPwdEvent.post()
        })
    }


}