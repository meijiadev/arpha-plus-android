package com.blackview.arphaplus.login

import android.os.Bundle
import com.blackview.arphaplus.R
import com.blackview.arphaplus.databinding.ActivityRegisterBinding
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
class RegisterActivity : BaseMVActivity<ActivityRegisterBinding, LoginModel>() {

    override fun getViewBinding(): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        getPageHead(this).apply {
            setTitleText(com.blackview.common_res.R.string.register)
        }
        binding.tvRegisterGo.setOnClickListener {
            finish()
        }
        binding.btnRegisterGo.setOnClickListener {
            gotoAct<CodeActivity>(Bundle().apply {
                putInt("jump_type",2)
            })
            finish()
        }
    }
}