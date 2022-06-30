package com.blackview.module_vip

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.base.BaseMVVMFragment
import com.blackview.module_vip.databinding.FragmentVipBinding
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners



/**
 *    author : MJ
 *    time   : 2022/06/30
 *    desc   : 会员中心
 */
class VipFragment : BaseMVVMFragment<FragmentVipBinding, VipViewModel>() {

    override fun initLayoutId(savedInstanceState: Bundle?): Int = R.layout.fragment_vip

    override fun createViewModel(fragment: Fragment): VipViewModel {
        return ViewModelProvider(this).get(VipViewModel::class.java)
    }

    override fun initVariableId(): Int = BR.vipModel

    override fun initView() {
        super.initView()
        binding.click=ProxyClick()

    }

    override fun initParam() {
        super.initParam()
        viewModel.vipMemberEvent.observe(viewLifecycleOwner){
            binding.tvVipName.text=it?.name
            binding.tvVipAccount.text= if (it?.email.isNullOrEmpty()) it?.mobile else it?.email
            Glide.with(this)
                .load(it?.img_url ?: com.blackview.common_res.R.drawable.empty_head)
                .transform(MultiTransformation(CenterCrop(), RoundedCorners(15)))
                .into(binding.ivVipHead)
        }
    }

    override fun initData() {
        super.initData()
    }


    inner class ProxyClick{
        fun onMessage(){
            ToastUtils.showShort("点击通知设定")
        }

        fun onShare(){
            ToastUtils.showShort("点击装置共享")
        }


    }



}