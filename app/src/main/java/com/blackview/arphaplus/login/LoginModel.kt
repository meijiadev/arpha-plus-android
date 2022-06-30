package com.blackview.arphaplus.login

import android.content.res.Resources
import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.blackview.arphaplus.App
import com.blackview.base.base.BaseViewModel
import com.blackview.base.base.SingleLiveEvent
import com.blackview.base.http.requestNoCheck
import com.blackview.base.request.Region
import com.blackview.repository.loginService
import com.blackview.util.L
import com.blackview.util.SpUtil
import org.json.JSONObject

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
    var forgetValidateEvent = SingleLiveEvent<String>()
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

    fun login(country_code: String, account: String, password: String, deviceToken: String) {
        val params = ArrayMap<Any, Any>()
        params["country_code"] = country_code
        params["account"] = account
        params["password"] = password
        params["manufacturer"] = "Arpha"
        params["AG"] = "APNS"
        params["device_token"] = deviceToken
        requestNoCheck({ loginService.login(params) }, {
            //{"code":20000,"message":"success","data":{"token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiJ9.eyJhdWQiOiIzIiwianRpIjoiNTRkZjc1MjE3YmNjOGY3ZmU1NGIxNzczM2FjODc0NzdkOWE0ZmRkZDJmODYwZThiZWU2ODViZWM1MzZiYjU5NGZlNDk2ODMyMDM2ZTU5NTEiLCJpYXQiOjE2NTY0OTk5MTAuMTg3Mzk3LCJuYmYiOjE2NTY0OTk5MTAuMTg3NDAzLCJleHAiOjE2NTY1MDcxMTAuMTc3NjYzLCJzdWIiOiIyOCIsInNjb3BlcyI6W119.Ph1t6mT1alNze1WFxZbdhRaPadJpBBeoOaYrF_gZMSk2nvIfUde-6ZryqlCsII6WrBmSwPqy7pPmtqv-x1Ikwm4cRlSOMcN_ZNf672YnJFq0WXm2utdq-n302zCgl533nOnDnEjIC_Rytio2RsScj9kAGHgn6BdXEDjstoNzhnSLc4t3Qv05EsWfc4sJwtT0o1EAA2-vZKzgguf9UME0uKwGY6PgF1lp3f4ROw1t-iL09NAXzwJULh_c-4ekRLLD5RU75HAZgOyOnH5pRPAQBcAY2KUVE7HgKvLcFmLa_Bsg86BRfYpkAv9e2-A8PhRPhLAvGS7xM_FPDHKMCq-hw-pVOqiJpgsMI4mofgOHNYZDlMO1CONOG1335BePAnVik_Q6YyotQnwfCb56hJQo-Vn73VfdeDm_TIsq24E2Rj80-00hGIdtaKrzdcqmruRCW53YtqWwnoJRZI8peSuinhO33i28Yq4OHvpPLYwgZzPCigaEdj1rdmvafzRYBlwm-0L0B6NvGYKJhiWTX7Nx5hWwaS7HQG3wroJ_V-kZkOkUOnLKl7wVLXV-lrc-zWC76UIeVVb2GsRQw-ZZwuLP9cDjz2BTj1Lj3NOu9YeO8QjiMf_dSW5Fv6fu5uurfAXaCJo7BRtgj5Gt7xC8DaSqaln3SBIVkqBnm97o6zf1g4g"}}
            val token = JSONObject(it.string()).optJSONObject("data").optString("token")
            SpUtil.encode("token", token)
            L.e("token:$token")
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
        requestNoCheck({ loginService.register(params) }, {
            registerEvent.post()
        })
    }

    /**注册--验证码*/
    fun validCode(code: String) {
        requestNoCheck({
            loginService.validCode(ArrayMap<Any, Any>().apply {
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
        requestNoCheck({ loginService.forgetCheck(params) }, {
            forgetCheckEvent.post()
        })
    }

    /**忘记密码-验证验证码*/
    fun forgetValidate(code: String) {
        requestNoCheck({
            loginService.forgetValidate(ArrayMap<Any, Any>().apply {
                this["validate_code"] = code
            })
        }, {
            //{"code":20000,"message":"success","data":{"reset_token":"59e19706d51d39f66711c2653cd7eb1291c94d9b55eb14bda74ce4dc636d015a"}}
            val reset_token = JSONObject(it.string()).optJSONObject("data").optString("reset_token")
            L.e("reset_token:$reset_token")
            forgetValidateEvent.postValue(reset_token)
        })
    }

    /**重设密码*/
    fun resetPwd(password1: String, password2: String, reset_token: String) {
        val params = ArrayMap<Any, Any>()
        params["password"] = password1
        params["password_confirmation"] = password2
        params["reset_token"] = reset_token
        requestNoCheck({ loginService.resetPwd(params) }, {
            resetPwdEvent.post()
        })
    }


}