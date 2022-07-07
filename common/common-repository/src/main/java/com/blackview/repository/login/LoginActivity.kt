package com.blackview.repository.login

import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.AdapterView
import com.blackview.base.R
import com.blackview.base.base.BaseMVActivity
import com.blackview.base.databinding.ActivityLoginBinding
import com.blackview.contant.PASSWORD
import com.blackview.contant.USER
import com.blackview.util.L
import com.blackview.util.SpUtil
import com.blackview.util.gotoAct
import com.google.android.material.tabs.TabLayout
import com.push.PushCenter

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
class LoginActivity : BaseMVActivity<ActivityLoginBinding, LoginModel>() {

    private var regionId = ""
    private var inputType = true
    private val pushCenter by lazy {
        PushCenter.getInstance()
    }

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        hideTitleBar()
        isEnableHideSoftInputFromWindow = true
        SpUtil.decodeString(USER)?.apply {
            binding.tvLoginPhone.setText(this)
        }
        SpUtil.decodeString(PASSWORD)?.apply {
            binding.tvLoginPwd.setText(this)
            binding.cbLoginPwdKeep.isChecked = true
        }
        binding.tvLoginRegister.setOnClickListener {
            gotoAct<RegisterActivity>()
        }
        binding.tvLoginForgetPwd.setOnClickListener {
            gotoAct<ForgetActivity>()
        }
        binding.cbLoginPwd.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.tvLoginPwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.tvLoginPwd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        binding.btnLoginGo.setOnClickListener {
            if (regionId.isEmpty()) {
                viewModel.showToast(getResString(com.blackview.common_res.R.string.select_region))
            } else if (binding.tvLoginPhone.text.isEmpty()) {
                if (inputType) {
                    viewModel.showToast(getResString(com.blackview.common_res.R.string.input_phone))
                } else {
                    viewModel.showToast(getResString(com.blackview.common_res.R.string.input_email))
                }
            } else if (binding.tvLoginPwd.text.isEmpty()) {
                viewModel.showToast(getResString(com.blackview.common_res.R.string.input_pwd))
            } else {
                pushCenter.configPushCenter(this)
                viewModel.login(
                    regionId, binding.tvLoginPhone.text.toString().trim(),
                    binding.tvLoginPwd.text.toString().trim(),
                    pushCenter.deviceToken
                )
            }
        }
    }

    override fun initData() {
        super.initData()
        val adapter = SpinnerAdapter(
            this,
            R.layout.spinner_item,
            array = viewModel.getRegions(resources = resources)
        )
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                if (p2 > 0) {
                    regionId = viewModel.array?.get(p2)?.id ?: ""
                    if (p2 == 5) {
                        inputType = false
                        binding.tvLoginPhone.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        binding.tvLoginPhone.hint = getString(com.blackview.common_res.R.string.input_email)
                    } else {
                        inputType = true
                        binding.tvLoginPhone.inputType = InputType.TYPE_CLASS_PHONE
                        binding.tvLoginPhone.hint = getString(com.blackview.common_res.R.string.input_phone)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.loginEvent.observe(this) {
            if (binding.cbLoginPwdKeep.isChecked) {
                SpUtil.encode(USER, binding.tvLoginPhone.text.toString().trim())
                SpUtil.encode(PASSWORD, binding.tvLoginPwd.text.toString().trim())
            } else {
                SpUtil.encode(USER, "")
                SpUtil.encode(PASSWORD, "")
            }
            //gotoAct<MainActivity>()
            finish()
        }
    }

}