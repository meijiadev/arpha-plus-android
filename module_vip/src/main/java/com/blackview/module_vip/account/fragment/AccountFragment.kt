package com.blackview.module_vip.account.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.RoundedCornersTransformation
import com.blackview.base.base.BaseMVVMFragment
import com.blackview.module_vip.BR
import com.blackview.module_vip.R
import com.blackview.module_vip.account.model.AccountModel
import com.blackview.module_vip.databinding.FragmentAccountBinding
import com.blackview.module_vip.main.AccountInfo
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   : 2022/07/13
 *    desc   : 账号管理首页
 */
class AccountFragment : BaseMVVMFragment<FragmentAccountBinding, AccountModel>() {

    /**
     * 作用域为activity的model对象
     */
    private var accountModel: AccountModel? = null

    override fun initLayoutId(savedInstanceState: Bundle?): Int = R.layout.fragment_account

    override fun createViewModel(fragment: Fragment): AccountModel {
        return ViewModelProvider(this).get(AccountModel::class.java)
    }

    override fun initVariableId(): Int = BR.accountModel

    override fun initView() {
        super.initView()
        binding.tvNickName.text = AccountInfo.nickName
        binding.ivHeadImage.load(
            AccountInfo.headImage ?: com.blackview.common_res.R.drawable.empty_head
        ){
            transformations(
                RoundedCornersTransformation(20f)
            )
        }

    }


    override fun initData() {
        super.initData()
        binding.click = ProxyClick()
        accountModel = getActivityViewModel(AccountModel::class.java)
        accountModel?.titleAction(getString(com.blackview.common_res.R.string.vip_account))

    }


    inner class ProxyClick {

        fun onModifyNick() {
            Logger.i("去修改昵称")
            findNavController().navigate(R.id.action_accountFragment_to_modifyNickFragment)
        }

        fun onHeadImage() {
            Logger.i("去修改头像")
            findNavController().navigate(R.id.action_accountFragment_to_headFragment)
        }

        fun onPassword() {
            Logger.i("去修改密码")
        }

        fun onDeleteAccount() {
            Logger.i("删除账号")
        }
    }

}