package com.blackview.module_device.add

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.blackview.base.base.BaseMVActivity
import com.blackview.module_device.DeviceModel
import com.blackview.module_device.R
import com.blackview.module_device.databinding.ActivityAddBinding
import com.blackview.module_device.databinding.FragmentDeviceBinding
import com.caldremch.widget.round.RoundTextView

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
    private val fragment3 by lazy { AddFragment3().newInstance() }
    private val fragment4 by lazy { AddFragment4().newInstance() }
    var member_token=""

    override fun getViewBinding(): ActivityAddBinding {
        return ActivityAddBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        isEnableHideSoftInputFromWindow=true
        getPageHead(this).apply {
            setBackClick {
                if (fragment2.isAdded) {
                    if (!fragment2.isShowInputLayout) {
                        fragment2.initShowView()
                    } else {
                        finish()
                    }
                } else {
                    finish()
                }
            }
        }.setTitleText(com.blackview.common_res.R.string.add_device)
        showFragment1()
    }

    override fun initData() {
        super.initData()
    }

    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(outState: Bundle) {
        //super.onSaveInstanceState(outState)
    }

    fun showFragment1() {
        switchSteps(1)
        switchContent(fragment1)
    }

    fun showFragment2() {
        switchSteps(2)
        binding.tvAddSelectName.text = getString(com.blackview.common_res.R.string.add_device2)
        switchContent(fragment2)
        if (fragment2.isAdded) {
            fragment2.initShowView()
        }
    }

    fun showFragment3() {
        switchSteps(3)
        binding.tvAddSelectName.text = getString(com.blackview.common_res.R.string.add_device3)
        switchContent(fragment3)
    }

    fun showFragment4() {
        switchSteps(4)
        binding.tvAddSelectName.text = getString(com.blackview.common_res.R.string.add_device4)
        switchContent(fragment4)
    }

    fun setTitle2(title: String) {
        binding.tvAddSelectName.text = title
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
    
    fun switchSteps(int: Int){
        when(int){
            1->{
                binding.tvSteps1.isVisible=true
                binding.tvSteps2.isVisible=true
                binding.tvSteps3.isVisible=true
                binding.tvSteps4.isVisible=true
                binding.tvSteps11.isVisible=false
                binding.tvSteps22.isVisible=false
                binding.tvSteps33.isVisible=false
                binding.tvSteps44.isVisible=false
            }
            2->{
                binding.tvSteps1.isVisible=false
                binding.tvSteps2.isVisible=false
                binding.tvSteps3.isVisible=true
                binding.tvSteps4.isVisible=true
                binding.tvSteps11.isVisible=true
                binding.tvSteps22.isVisible=true
                binding.tvSteps33.isVisible=false
                binding.tvSteps44.isVisible=false
            }
            3->{
                binding.tvSteps1.isVisible=false
                binding.tvSteps2.isVisible=true
                binding.tvSteps3.isVisible=false
                binding.tvSteps4.isVisible=true
                binding.tvSteps11.isVisible=true
                binding.tvSteps22.isVisible=false
                binding.tvSteps33.isVisible=true
                binding.tvSteps44.isVisible=false
                
            }
            4->{
                binding.tvSteps1.isVisible=false
                binding.tvSteps2.isVisible=true
                binding.tvSteps3.isVisible=true
                binding.tvSteps4.isVisible=false
                binding.tvSteps11.isVisible=true
                binding.tvSteps22.isVisible=false
                binding.tvSteps33.isVisible=false
                binding.tvSteps44.isVisible=true
            }
        }
    }

}