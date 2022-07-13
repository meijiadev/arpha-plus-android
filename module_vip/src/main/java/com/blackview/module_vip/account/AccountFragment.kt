package com.blackview.module_vip.account

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.base.BaseMVVMFragment
import com.blackview.module_vip.BR
import com.blackview.module_vip.R
import com.blackview.module_vip.databinding.FragmentAccountBinding
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   :
 *    desc   :
 */
class AccountFragment :BaseMVVMFragment<FragmentAccountBinding,AccountModel>() {
    override fun initLayoutId(savedInstanceState: Bundle?): Int = R.layout.fragment_account

    override fun createViewModel(fragment: Fragment): AccountModel {
       return ViewModelProvider(this).get(AccountModel::class.java)
    }

    override fun initVariableId(): Int =BR.accountModel

    override fun initView() {
        super.initView()
    }



    override fun initData() {
        super.initData()
        binding.click=ProxyClick()
    }


    inner class ProxyClick{

        fun onModifyNick(){
            Logger.i("去修改昵称")
        }

        fun onHeadImage(){
            Logger.i("去修改头像")
        }

        fun onPassword(){
            Logger.i("去修改密码")
        }

        fun onDeleteAccount(){
            Logger.i("删除账号")
        }
    }

}