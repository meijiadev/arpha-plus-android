package com.blackview.module_vip

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.base.App
import com.blackview.base.base.BaseMVVMFragment
import com.blackview.module_vip.adapter.SettingsAdapter
import com.blackview.module_vip.databinding.FragmentVipBinding
import com.blackview.util.L
import com.blackview.util.gotoAct
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener


/**
 *    author : MJ
 *    time   : 2022/06/30
 *    desc   : 会员中心
 */
class VipFragment : BaseMVVMFragment<FragmentVipBinding, VipViewModel>() ,OnItemClickListener{

    /**
     * 设置项列表
     */
    private lateinit var settingsList:MutableList<SettingItem>

    private lateinit var settingsAdapter: SettingsAdapter


    override fun initLayoutId(savedInstanceState: Bundle?): Int = R.layout.fragment_vip

    override fun createViewModel(fragment: Fragment): VipViewModel {
        return ViewModelProvider(this).get(VipViewModel::class.java)
    }

    override fun initVariableId(): Int = BR.vipModel

    override fun initView() {
        super.initView()
        binding.click=ProxyClick()
        initSettingsList()
        settingsAdapter= SettingsAdapter()
        settingsAdapter.data=settingsList
        binding.rvSettings.adapter=settingsAdapter
        binding.rvSettings.layoutManager=LinearLayoutManager(context,RecyclerView.VERTICAL,false)
        // 注册recyclerView点击事件
        settingsAdapter.setOnItemClickListener(this)

    }

    override fun initParam() {
        super.initParam()
        Glide.with(this)
            .load(com.blackview.common_res.R.drawable.empty_head)
            .transform(MultiTransformation(CenterCrop(), RoundedCorners(30)))
            .into(binding.ivVipHead)
    }

    override fun initData() {
        super.initData()
        // 请求会员信息
        viewModel.getVipMemberInfo()
        viewModel.vipMemberEvent.observe(viewLifecycleOwner){
            binding.tvVipName.text=it?.name
            binding.tvVipAccount.text= if (it?.email.isNullOrEmpty()) it?.mobile else it?.email
            Glide.with(this)
                .load(it?.img_url ?: com.blackview.common_res.R.drawable.empty_head)
                .transform(MultiTransformation(CenterCrop(), RoundedCorners(15)))
                .into(binding.ivVipHead)
        }

    }


    inner class ProxyClick{
        fun onMessage(){
            ToastUtils.showShort("点击通知设定")
            val bundle=Bundle()
            bundle.putString(WHERE_TO_JUMP_FROM, JUMP_FORM_MESSAGE_SETTINGS)
            context?.gotoAct<VipDeviceActivity>(bundle)
        }

        fun onShare(){
            ToastUtils.showShort("点击装置共享")
            val bundle=Bundle()
            bundle.putString(WHERE_TO_JUMP_FROM, JUMP_FORM_DEVICE_SHARE)
            context?.gotoAct<VipDeviceActivity>(bundle)
        }


    }


    /**
     * 初始化設置列表的數據
     */
    private fun initSettingsList(){
        settingsList= mutableListOf()
        settingsList.add(SettingItem(
            com.blackview.common_res.R.drawable.icon_setting_16,
            getString(com.blackview.common_res.R.string.vip_account)
        ))
        settingsList.add(SettingItem(
            com.blackview.common_res.R.drawable.icon_language_16,
            getString(com.blackview.common_res.R.string.vip_language)
        ))
        settingsList.add(SettingItem(
            com.blackview.common_res.R.drawable.icon_change_16,
            getString(com.blackview.common_res.R.string.vip_privacy)
        ))
        settingsList.add(SettingItem(
            com.blackview.common_res.R.drawable.icon_qa_16,
            getString(com.blackview.common_res.R.string.vip_qa)
        ))
        settingsList.add(SettingItem(
            com.blackview.common_res.R.drawable.icon_renew_16,
            getString(com.blackview.common_res.R.string.vip_update)
        ))
        settingsList.add(SettingItem(
            com.blackview.common_res.R.drawable.icon_delete_16,
            getString(com.blackview.common_res.R.string.vip_clear_cache)
        ))
        settingsList.add(SettingItem(
            com.blackview.common_res.R.drawable.icon_logout_16,
            getString(com.blackview.common_res.R.string.vip_logout)
        ))

    }


    data class SettingItem (val iconRes:Int,val itemName:String,var rightMsg:String="")

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        L.i("点击：$position,${settingsList[position].itemName}")
        ToastUtils.showShort("点击：$position,${settingsList[position].itemName}")
    }


}