package com.blackview.arphaplus.login

import android.os.Bundle
import com.blackview.arphaplus.R
import com.blackview.arphaplus.databinding.ActivityCodeBinding
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
 * Created by home on 2022/6/18.
 */
class CodeActivity : BaseMVActivity<ActivityCodeBinding, LoginModel>() {

    var type = 0

    override fun getViewBinding(): ActivityCodeBinding {
        return ActivityCodeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        isEnableHideSoftInputFromWindow=true
        hideTitleBar()
        intent?.extras?.apply {
            type = getInt("jump_type")//1忘记密码 2 注册
        }
        binding.btnCodeGo.setOnClickListener {
            if (binding.etCodeCode.text.trim().isEmpty()) {
                viewModel.showToast(getString(com.blackview.common_res.R.string.input_code))
            } else {
                if (type == 1) {
                    viewModel.forgetValidate(binding.etCodeCode.text.toString().trim())
                } else {
                    viewModel.validCode(binding.etCodeCode.text.toString().trim())
                }

            }
        }
        binding.tvForgetBack.setOnClickListener {
            finish()
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.validCodeEvent.observe(this) {
            if (type == 1) {
                gotoAct<ResetPwdActivity>()
            } else {
                gotoAct<SuccessActivity>()
            }
            finish()
        }
    }
}