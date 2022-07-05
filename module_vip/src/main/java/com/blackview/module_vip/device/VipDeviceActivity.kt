package com.blackview.module_vip.device

import android.graphics.drawable.Drawable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.blackview.base.base.BaseMVActivity
import com.blackview.module_vip.R
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
    private val myDevicesFragment: MyDevicesFragment by lazy { MyDevicesFragment.newInstance() }

    private val shareFragment: ShareFragment by lazy { ShareFragment.newInstance() }

    override fun getViewBinding(): ActivityVipDeviceBinding {
        return ActivityVipDeviceBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
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
        // 获取自身拥有的设备
        viewModel.getOwnDevices()
    }


    override fun initListener() {
        super.initListener()
        // 标题栏返回键
        getBackButton().setOnClickListener {
            onBackPressed()
        }
        binding.tvLeftTitle.setOnClickListener {
            viewModel.getOwnDevices()
        }

        binding.tvRightTitle.setOnClickListener {
            setRightBottomLine()
            viewModel.getShareDevices()
        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.ownDevicesList.observe(this) {
            it?.let {
                setLeftBottomLine()
                if (it.size == 0) {
                    //binding.llEmpty.visibility = View.VISIBLE
                }
                switchFragment(myDevicesFragment)
            }
        }

        viewModel.shareDevicesList.observe(this) {
            it?.let {
                setRightBottomLine()
                if (it.size == 0) {
                    //binding.llEmpty.visibility = View.VISIBLE
                }
                switchFragment(shareFragment)
            }
        }
    }

    /**
     * 设置左侧选中效果
     */
    private fun setLeftBottomLine() {
        binding.tvLeftTitle.post {
            val drawable: Drawable =
                resources.getDrawable(com.blackview.common_res.R.drawable.yellow_line)
            val width = binding.tvLeftTitle.width
            Logger.i("当前宽度：$width")
            drawable.setBounds(0, 0, width, 10)
            binding.tvLeftTitle.setCompoundDrawables(null, null, null, drawable)
            binding.tvRightTitle.setCompoundDrawables(null, null, null, null)
        }
    }

    /**
     * 设置右侧选中效果
     */
    private fun setRightBottomLine() {
        binding.tvRightTitle.post {
            val drawable: Drawable =
                resources.getDrawable(com.blackview.common_res.R.drawable.yellow_line)
            val width = binding.tvRightTitle.width
            drawable.setBounds(0, 0, width, 10)
            binding.tvRightTitle.setCompoundDrawables(null, null, null, drawable)
            binding.tvLeftTitle.setCompoundDrawables(null, null, null, null)
        }
    }

    /**
     * 当前的fragment
     */
    private var mFragment= Fragment()

    /**
     * 切换fragment
     */
    private fun switchFragment(target: Fragment) {
        if (target != null && target != mFragment) {
            val transaction = supportFragmentManager.beginTransaction()
            if (target is MyDevicesFragment){
                transaction.setCustomAnimations(
                    com.blackview.common_res.R.anim.action_left_enter,
                    com.blackview.common_res.R.anim.action_left_exit
                )
            }else{
                transaction.setCustomAnimations(
                    com.blackview.common_res.R.anim.action_rigth_enter,
                    com.blackview.common_res.R.anim.action_rigth_exit
                )
            }
            // 先判断该fragment 是否已经被添加到管理器
            if (!target.isAdded){
                transaction.hide(mFragment).add(R.id.fragmentContainer,target).commitAllowingStateLoss()
            }else{
                // 添加的fragment 直接显示
                transaction.hide(mFragment).show(target).commitAllowingStateLoss()
            }
            mFragment=target

        }

    }
}