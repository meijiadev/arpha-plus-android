package com.blackview.arphaplus

import com.blackview.arphaplus.databinding.ActivityMainBinding
import com.blackview.base.base.BaseMVVMActivity
import com.blackview.base.http.observeState
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
            viewModel.repository.livePhone.observeState(this) {
                onStart {
                    viewModel.uiChangeLiveData.toastEvent.postValue("hello")
                }
                onSuccess {
                    viewModel.uiChangeLiveData.toastEvent.postValue("success")
                    viewModel.repository.string.set(it.province)
                }
                onFailure {
                    viewModel.uiChangeLiveData.toastEvent.postValue(it.message)
                }
            }
            //viewModel.getData()

        }

        binding.tvHelloWorld.setOnClickListener {
            gotoAct<DemoActivity>()
        }


    }


}