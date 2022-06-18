package com.blackview.arphaplus.login

import com.blackview.arphaplus.BR
import com.blackview.arphaplus.R
import com.blackview.arphaplus.databinding.ActivityLoginBinding
import com.blackview.base.base.BaseMVActivity
import com.blackview.base.base.BaseMVVMActivity
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
    
    override fun getViewBinding(): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        hideTitleBar()
        binding.tvLoginRegister.setOnClickListener { 
            gotoAct<RegisterActivity>()
        }
        binding.tvLoginForgetPwd.setOnClickListener { 
            gotoAct<ForgetActivity>()
        }
    }

    
}