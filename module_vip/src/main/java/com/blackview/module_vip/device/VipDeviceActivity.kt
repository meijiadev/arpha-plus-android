package com.blackview.module_vip.device

import android.graphics.drawable.Drawable
import com.blackview.base.base.BaseMVActivity
import com.blackview.module_vip.databinding.ActivityVipDeviceBinding
import com.blackview.util.L
import com.blankj.utilcode.util.ToastUtils
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   : 2022/07/01
 *    desc   : 会员页面中的设备相关设置页
 */
const val WHERE_TO_JUMP_FROM = "where to jump from"
const val JUMP_FORM_DEVICE_SHARE = "device share"
const val JUMP_FORM_MESSAGE_SETTINGS = "notification settings"

class VipDeviceActivity : BaseMVActivity<ActivityVipDeviceBinding, VipDeviceModel>() {

    override fun getViewBinding(): ActivityVipDeviceBinding {
        return ActivityVipDeviceBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        getBackButton().setOnClickListener {
            finish()
        }

    }



    override fun initData() {
        super.initData()
        val msg = intent.extras?.getString(WHERE_TO_JUMP_FROM)
        ToastUtils.showLong(msg)
        when (msg) {
            JUMP_FORM_DEVICE_SHARE -> setTitle(getString(com.blackview.common_res.R.string.device_share))
            JUMP_FORM_MESSAGE_SETTINGS -> setTitle(getString(com.blackview.common_res.R.string.message_settings))
        }
        setLeftBottomLine()

        binding.tvLeftTitle.setOnClickListener {
            setLeftBottomLine()
        }

        binding.tvRightTitle.setOnClickListener {
            setRightBottomLine()
        }
        // 获取自身拥有的设备
        viewModel.getOwnDevices()
    }



    private fun setLeftBottomLine(){
        binding.tvLeftTitle.post {
            val drawable: Drawable = resources.getDrawable(com.blackview.common_res.R.drawable.yellow_line)
            val width=binding.tvLeftTitle.width
            Logger.i("当前宽度：$width")
            drawable.setBounds(0,0,width,10)
            binding.tvLeftTitle.setCompoundDrawables(null,null,null,drawable)
            binding.tvRightTitle.setCompoundDrawables(null,null,null,null)
        }
    }
    private fun setRightBottomLine(){
        binding.tvRightTitle.post{
            val drawable: Drawable = resources.getDrawable(com.blackview.common_res.R.drawable.yellow_line)
            val width=binding.tvRightTitle.width
            drawable.setBounds(0,0,width,10)
            binding.tvRightTitle.setCompoundDrawables(null,null,null,drawable)
            binding.tvLeftTitle.setCompoundDrawables(null,null,null,null)
        }
    }


}