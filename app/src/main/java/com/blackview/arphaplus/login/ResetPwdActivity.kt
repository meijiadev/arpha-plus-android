package com.blackview.arphaplus.login

import android.os.Bundle
import com.blackview.arphaplus.R
import com.blackview.arphaplus.databinding.ActivityCodeBinding
import com.blackview.arphaplus.databinding.ActivityResetPwdBinding
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
class ResetPwdActivity : BaseMVActivity<ActivityResetPwdBinding, LoginModel>() {

    override fun getViewBinding(): ActivityResetPwdBinding {
        return ActivityResetPwdBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        hideTitleBar()
        isEnableHideSoftInputFromWindow=true
        binding.btnResetGo.setOnClickListener {
            if (binding.tvResetPwd.text.toString().trim().isEmpty()) {
                viewModel.showToast(getResString(com.blackview.common_res.R.string.input_pwd))
            } else if (binding.tvResetPwd1.text.toString().trim().isEmpty()) {
                viewModel.showToast(getResString(com.blackview.common_res.R.string.input_pwd_again))
            } else {
                viewModel.resetPwd(
                    binding.tvResetPwd.text.toString().trim(),
                    binding.tvResetPwd1.text.toString().trim()
                )
            }
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.resetPwdEvent.observe(this) {
            gotoAct<SuccessActivity>(Bundle().apply {
                putInt("reset_pwd", 111)
            })
        }
    }

}