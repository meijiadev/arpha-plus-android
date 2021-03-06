package com.blackview.repository.login

import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import com.blackview.base.R
import com.blackview.base.base.BaseMVActivity
import com.blackview.base.databinding.ActivityForgetPwdBinding
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
 * Created by home on 2022/6/18.
 */
class ForgetActivity : BaseMVActivity<ActivityForgetPwdBinding, LoginModel>() {

    private var regionId = ""
    private var inputType = true

    override fun getViewBinding(): ActivityForgetPwdBinding {
        return ActivityForgetPwdBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        hideTitleBar()
        isEnableHideSoftInputFromWindow = true
        binding.tvForgetBack.setOnClickListener {
            finish()
        }
        binding.btnForgetGo.setOnClickListener {
            if (regionId.isEmpty()) {
                viewModel.showToast(getResString(com.blackview.common_res.R.string.select_region))
            } else if (binding.tvForgetPhone.text.toString().trim().isEmpty()) {
                if (inputType) {
                    viewModel.showToast(getResString(com.blackview.common_res.R.string.input_phone))
                } else {
                    viewModel.showToast(getResString(com.blackview.common_res.R.string.input_email))
                }
            } else {
                viewModel.forgetCheck(regionId, binding.tvForgetPhone.text.toString().trim())
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
                        binding.tvForgetPhone.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                        binding.tvForgetPhone.hint = getString(com.blackview.common_res.R.string.input_email)
                    } else {
                        inputType = true
                        binding.tvForgetPhone.inputType = InputType.TYPE_CLASS_PHONE
                        binding.tvForgetPhone.hint = getString(com.blackview.common_res.R.string.input_phone)
                    }
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.forgetCheckEvent.observe(this) {
            gotoAct<CodeActivity>(Bundle().apply {
                putInt("jump_type", 1)
            })
        }
    }
}