package com.blackview.arphaplus.login

import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.blackview.arphaplus.R
import com.blackview.arphaplus.databinding.ActivityRegisterBinding
import com.blackview.base.base.BaseMVActivity
import com.blackview.base.request.Region
import com.blackview.util.gotoAct
import com.blackview.common_res.R.string

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
 * Created by home on 2022/6/18.
 */
class RegisterActivity : BaseMVActivity<ActivityRegisterBinding, LoginModel>() {

    private var regionId = ""

    override fun getViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        isEnableHideSoftInputFromWindow = true
        hideTitleBar()
        binding.cbRegisterPwd.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.tvRegisterPwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.tvRegisterPwd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }
        binding.tvRegisterGo.setOnClickListener {
            finish()
        }
        binding.btnRegisterGo.setOnClickListener {
            if (regionId.isEmpty()) {
                viewModel.showToast(getResString(string.select_region))
            } else if (binding.tvRegisterPhone.text.isEmpty()) {
                viewModel.showToast(getResString(string.input_phone))
            } else if (binding.tvRegisterPwd.text.isEmpty()) {
                viewModel.showToast(getResString(string.input_pwd))
            } else if (!binding.cbRegisterPwdKeep.isChecked) {
                viewModel.showToast(
                    getResString(string.policy1)
                            + getResString(string.policy2)
                            + getResString(string.policy3)
                )
            } else {
                viewModel.register(
                    regionId, binding.tvRegisterPhone.text.toString().trim(),
                    binding.tvRegisterPwd.text.toString().trim()
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
                        binding.tvRegisterPhone.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                    } else {
                        binding.tvRegisterPhone.inputType = InputType.TYPE_CLASS_PHONE
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.registerEvent.observe(this) {
            gotoAct<CodeActivity>(Bundle().apply {
                putInt("jump_type", 2)
            })
            finish()
        }
    }
}