package com.blackview.arphaplus

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.blackview.arphaplus.databinding.ActivityMainBinding
import com.blackview.base.base.BaseMVActivity
import com.blackview.contant.main
import com.blackview.module_device.DeviceFragment

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * Created by home on 2022/6/25.
 */
@Route(path = main)
class MainActivity : BaseMVActivity<ActivityMainBinding, MainModel>() {

    private val deviceFragment by lazy { DeviceFragment() }

    override fun getViewBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initView() {
        hideTitleBar()
        binding.mainHome.isChecked = true
        switchContent(deviceFragment)
    }

    override fun initListener() {
        super.initListener()
        binding.tabs.setOnCheckedChangeListener { p0, p1 ->
            when (p1) {
                R.id.main_home -> {
                    switchContent(deviceFragment)
                }
                R.id.main_notice -> {}
                R.id.main_me -> {}
            }
        }
    }


    /**
     * 暂存Fragment
     */
    private var mFragment = Fragment()

    /**
     * 选择Fragment存放
     */
    fun switchContent(to: Fragment?) {
        if (to != null && mFragment !== to) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setCustomAnimations(
                com.blackview.common_res.R.anim.action_rigth_enter,
                com.blackview.common_res.R.anim.action_left_exit
            )
            // 先判断是否被add过
            if (!to.isAdded) {
                // 隐藏当前的fragment，add下一个到Activity中
                transaction.hide(mFragment).add(R.id.main_tab, to)
                    .commitAllowingStateLoss()
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(mFragment).show(to).commitAllowingStateLoss()
            }
            mFragment = to
        }
    }
}