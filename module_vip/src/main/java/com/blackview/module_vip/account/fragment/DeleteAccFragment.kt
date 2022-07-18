package com.blackview.module_vip.account.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.account.model.AccountModel
import com.blackview.module_vip.databinding.FragmentDeleteAccountBinding
import com.blackview.util.DateUtil


/**
 *    author : MJ
 *    time   : 2022/07/18
 *    desc   : 删除账号
 */
class DeleteAccFragment : BaseMVFragment<FragmentDeleteAccountBinding, BaseViewModel>() {

    private var accountModel: AccountModel? = null

    override fun createViewModel(fragment: Fragment): BaseViewModel {
        return ViewModelProvider(this).get(BaseViewModel::class.java)
    }

    override fun initView() {
        super.initView()
        accountModel=getActivityViewModel(AccountModel::class.java)
        accountModel?.titleAction(getString(com.blackview.common_res.R.string.delete_account))
        val text=getString(com.blackview.common_res.R.string.delete_account_tips)
        binding.tvDeleteTips.text=String.format(text,DateUtil.getCurrentDateTime())
    }

    override fun initData() {
        super.initData()
    }




}