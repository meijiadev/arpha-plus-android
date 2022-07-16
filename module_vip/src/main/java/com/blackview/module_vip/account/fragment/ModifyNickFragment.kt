package com.blackview.module_vip.account.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.account.model.AccountModel
import com.blackview.module_vip.databinding.FragmentModifyNickBinding
import com.blackview.module_vip.main.AccountInfo
import com.blankj.utilcode.util.ToastUtils


/**
 *    author : MJ
 *    time   : 2022/07/13
 *    desc   : 修改昵称
 */
class ModifyNickFragment : BaseMVFragment<FragmentModifyNickBinding, BaseViewModel>() {

    private var accountModel: AccountModel? = null

    private var nickName: String? = null

    override fun createViewModel(fragment: Fragment): BaseViewModel {
        return ViewModelProvider(this).get(BaseViewModel::class.java)
    }


    override fun initData() {
        super.initData()
        accountModel = getActivityViewModel(AccountModel::class.java)
        binding.tvCurrentNick.text = AccountInfo.nickName ?: "null"
    }

    override fun initView() {
        super.initView()
    }

    override fun initListener() {
        super.initListener()
        binding.btSave.setOnClickListener {
            nickName = binding.etNickName.text.toString()
            if (!nickName.isNullOrEmpty()){
                accountModel?.changeNickName(nickName)
            }else{
                ToastUtils.showShort(com.blackview.common_res.R.string.please_enter_nick)
            }
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        accountModel?.nickChangeEvent?.observe(viewLifecycleOwner){
            it?.let {
                if (it){
                    AccountInfo.nickName=nickName
                    findNavController().popBackStack()
                }
            }
        }
    }


}