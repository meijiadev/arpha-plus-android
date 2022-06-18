package com.blackview.arphaplus

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentManager
import com.blackview.arphaplus.databinding.ActivityDemoBinding
import com.blackview.arphaplus.databinding.ActivityMainBinding
import com.blackview.base.base.BaseMVActivity

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
 * Created by home on 2022/5/31.
 */
class DemoActivity : BaseMVActivity<ActivityDemoBinding, MainModel>() {

    override fun getViewBinding(): ActivityDemoBinding {
        return ActivityDemoBinding.inflate(layoutInflater)
    }

   
    override fun initView() {
        super.initView()
        getPageHead(this).apply { setTitleText("hello world") }
        
        binding.btnHello1.setOnClickListener { 
            viewModel.showToast("hello world")
        }
        
    }
    

}