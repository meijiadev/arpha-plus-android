package com.blackview.module_device.add

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.blackview.base.base.BaseMVActivity
import com.blackview.module_device.DeviceModel
import com.blackview.module_device.R
import com.blackview.module_device.databinding.ActivityAddBinding
import com.blackview.module_device.databinding.FragmentDeviceBinding

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
 * Created by home on 2022/6/28.
 */
class AddAty : BaseMVActivity<ActivityAddBinding, AddModel>() {

    private val fragment1 by lazy { AddFragment1().newInstance() }
    private val fragment2 by lazy { AddFragment2().newInstance() }

    override fun getViewBinding(): ActivityAddBinding {
        return ActivityAddBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        getPageHead(this).setTitleText(com.blackview.common_res.R.string.add_device)
        showFragment1()
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState)
    }

    fun showFragment1() {
        switchContent(fragment1)
    }

    fun showFragment2() {
        binding.tvAddSelectName.text=getString(com.blackview.common_res.R.string.add_device2)
        switchContent(fragment2)
        if (fragment2.isAdded) {
            fragment2.initShowView()
        }
    }
    fun setTitle(){
        binding.tvAddSelectName.text=getString(com.blackview.common_res.R.string.add_device22)
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
                transaction.hide(mFragment).add(R.id.layout_add, to)
                    .commitAllowingStateLoss()
            } else {
                // 隐藏当前的fragment，显示下一个
                transaction.hide(mFragment).show(to).commitAllowingStateLoss()
            }
            mFragment = to
        }
    }
}