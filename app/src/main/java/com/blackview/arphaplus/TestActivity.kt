package com.blackview.arphaplus


import com.blackview.arphaplus.databinding.ActivityTestBinding
import com.blackview.base.base.BaseMVVMActivity
import com.blackview.contant.device
import com.blackview.contant.notice
import com.blackview.contant.vip
import com.blackview.repository.base.observeState
import com.blackview.repository.session.AccountSessionManager
import com.blackview.util.L
import com.blackview.util.gotoAct

class TestActivity : BaseMVVMActivity<ActivityTestBinding, MainModel>() {


    override fun layoutId(): Int {
        return R.layout.activity_test
    }

    override fun initVariableId(): Int {
        return BR.mainModel
    }


    override fun initView() {
        super.initView()
        
        getPageHead(this).apply { setTitleText("title bar") }
        
        L.e(AccountSessionManager.accountSession.accountId)
        binding.btnClick.setOnClickListener {
            //viewModel.getData()
            viewModel.phoneString.value = "13590404481"
        }

        binding.tvHelloWorld.setOnClickListener {
            gotoAct<DemoActivity>()
            //viewModel.phoneString.value = "13929786724"
        }

        binding.btnDevice.setOnClickListener {
            gotoAct(device)
        }
        binding.btnNotice.setOnClickListener {
            gotoAct(notice) {
                withInt("dd", 123)
                withString("zz", "hell world")
            }
        }

        binding.btnVip.setOnClickListener {
            gotoAct(vip)
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