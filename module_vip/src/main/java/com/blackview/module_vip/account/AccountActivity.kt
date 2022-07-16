package com.blackview.module_vip.account

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.blackview.base.base.BaseMVActivity
import com.blackview.base.base.BaseViewModel
import com.blackview.contant.VIP_ACCOUNT_MANAGER
import com.blackview.module_vip.R
import com.blackview.module_vip.account.model.AccountModel
import com.blackview.module_vip.databinding.ActivityAccountBinding



/**
 *    author : MJ
 *    time   : 2022/07/12
 *    desc   : 账号管理页面
 */
@Route(path = VIP_ACCOUNT_MANAGER)
class AccountActivity : BaseMVActivity<ActivityAccountBinding, AccountModel>() {

    private lateinit var navHostFragment:NavHostFragment

    private lateinit var navController:NavController

    override fun initView() {
        super.initView()
        setTitle(getString(com.blackview.common_res.R.string.vip_account))
        navHostFragment=supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        navController=navHostFragment.navController

    }

    override fun initListener() {
        super.initListener()
        getBackButton().setOnClickListener {
            if (R.id.accountFragment==navController.currentDestination?.id){
                finish()
            }else{
                navController.popBackStack()
            }
        }
    }


    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.titleEvent().observe(this){
            it?.let {
                setTitle(it)
            }
        }
    }

    override fun getViewBinding(): ActivityAccountBinding {
        return ActivityAccountBinding.inflate(layoutInflater)
    }

}