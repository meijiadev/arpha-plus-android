package com.blackview.arphaplus.login

import android.os.Bundle
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
    
    var type=0
    
    override fun getViewBinding(): ActivityCodeBinding {
        return ActivityCodeBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        hideTitleBar()
        intent?.extras?.apply { 
            type=getInt("jump_type")//1忘记密码 2 注册
        }
        binding.btnCodeGo.setOnClickListener { 
            if (type==1){
                gotoAct<SuccessActivity>(Bundle().apply { 
                    putInt("reset_pwd",111)
                })
            }else{
                gotoAct<SuccessActivity>()
            }
            finish()
        }
    }
}