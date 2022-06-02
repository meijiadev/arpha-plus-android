package com.blackview.arphaplus

import androidx.lifecycle.map
import com.blackview.arphaplus.databinding.ActivityMainBinding
import com.blackview.base.base.BaseMVVMActivity
import com.blackview.base.request.StartResponse
import com.blackview.base.request.SuccessResponse
import com.blackview.repository.base.observeState
import com.blackview.repository.session.AccountSessionManager
import com.blackview.util.L
import com.blackview.util.gotoAct

class MainActivity : BaseMVVMActivity<ActivityMainBinding, MainModel>() {


    override fun layoutId(): Int {
        return R.layout.activity_main
    }

    override fun initVariableId(): Int {
        return BR.mainModel
    }


    override fun initView() {
        super.initView()
        L.e(AccountSessionManager.accountSession.accountId)
        binding.btnClick.setOnClickListener {
            //viewModel.getData()
            viewModel.phoneString.value="13590404481"
        }

        binding.tvHelloWorld.setOnClickListener {
            //gotoAct<DemoActivity>()
            viewModel.phoneString.value="13929786724"
        }

    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.phoneInfo.observeState(this, viewModel) {
            onSuccess {
                viewModel.string.set(it.province + it.city + it.sp)
            }
        }
    }


}