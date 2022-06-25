package com.blackview.arphaplus.login

import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.AdapterView
import com.blackview.arphaplus.R
import com.blackview.arphaplus.databinding.ActivityLoginBinding
import com.blackview.base.base.BaseMVActivity
import com.blackview.util.gotoAct

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

    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        hideTitleBar()
        isEnableHideSoftInputFromWindow=true
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
                viewModel.showToast(getResString(com.blackview.common_res.R.string.input_phone))
            } else if (binding.tvLoginPwd.text.isEmpty()) {
                viewModel.showToast(getResString(com.blackview.common_res.R.string.input_pwd))
            } else {
                viewModel.login(
                    regionId, binding.tvLoginPhone.text.toString().trim(),
                    binding.tvLoginPwd.text.toString().trim()
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
                        binding.tvLoginPhone.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    } else {
                        binding.tvLoginPhone.inputType = InputType.TYPE_CLASS_PHONE
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

        }
    }

}