package com.blackview.arphaplus.login

import android.os.Bundle
import com.blackview.arphaplus.R
import com.blackview.arphaplus.databinding.ActivityCodeBinding
import com.blackview.arphaplus.databinding.ActivityForgetPwdBinding
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
class ForgetActivity : BaseMVActivity<ActivityForgetPwdBinding, LoginModel>() {

    override fun getViewBinding(): ActivityForgetPwdBinding {
        return ActivityForgetPwdBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        hideTitleBar()
        binding.tvForgetBack.setOnClickListener {
            finish()
        }
        binding.btnForgetGo.setOnClickListener { 
            gotoAct<CodeActivity>(Bundle().apply { 
                putInt("jump_type",1)
            })
        }
    }
}