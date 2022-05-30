package com.blackview.arphaplus

import androidx.lifecycle.Observer
import com.blackview.arphaplus.databinding.ActivityMainBinding
import com.blackview.base.base.BaseMVVMActivity

class MainActivity : BaseMVVMActivity<ActivityMainBinding, MainModel>() {


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.mainModel
    }


    override fun initView() {
        super.initView()
        binding.btnClick.setOnClickListener { 
            viewModel.getData()
            
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.heartbeat.observe(this, Observer {
            viewModel.string.set("sdfsdf")
        })
    }

}