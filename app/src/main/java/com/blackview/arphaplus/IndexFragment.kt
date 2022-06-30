package com.blackview.arphaplus

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.arphaplus.databinding.ActivityMainBinding
import com.blackview.arphaplus.databinding.ActivityTestBinding
import com.blackview.base.base.BaseMVVMFragment

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
class IndexFragment : BaseMVVMFragment<ActivityTestBinding, MainModel>() {
    override fun initLayoutId(savedInstanceState: Bundle?): Int {
        return R.layout.activity_test
    }

//    override fun createViewModel(fragment: Fragment): MainModel {
//        return ViewModelProvider(this).get(MainModel::class.java)
//    }

    override fun initVariableId(): Int {
        return BR.mainModel
    }

    override fun initView() {
        super.initView()
        binding.btnClick.setOnClickListener {
            
        }
        binding.tvHelloWorld.setOnClickListener {
            viewModel.onBackPressed()
        }

    }
}