package com.blackview.arphaplus.login

import android.content.ContextParams
import androidx.core.content.ContextCompat
import com.blackview.arphaplus.R
import com.blackview.arphaplus.databinding.ActivityCodeBinding
import com.blackview.arphaplus.databinding.ActivityRegisterSuccessBinding
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
class SuccessActivity : BaseMVActivity<ActivityRegisterSuccessBinding, LoginModel>() {

    override fun getViewBinding(): ActivityRegisterSuccessBinding {
        return ActivityRegisterSuccessBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        hideTitleBar()
        intent?.extras?.apply {
            val dd = getInt("reset_pwd", 0)
            if (dd != 0) {
                binding.textView2.setText(com.blackview.common_res.R.string.pwd_success)
            } else {
                binding.textView2.setText(com.blackview.common_res.R.string.register_success)
            }
        }
        binding.btnSuccessGo.setOnClickListener {
            gotoAct<LoginActivity>()
            finish()
        }
    }

}