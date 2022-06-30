package com.blackview.module_device.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.base.BaseMVFragment
import com.blackview.module_device.databinding.FragmentAddFourBinding
import com.blackview.module_device.databinding.FragmentAddThreeBinding
import com.blackview.module_device.databinding.FragmentAddTwoBinding

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
 * Created by home on 2022/6/29.
 */
class AddFragment4 : BaseMVFragment<FragmentAddFourBinding, AddModel>() {

    override fun createViewModel(fragment: Fragment): AddModel {
        return ViewModelProvider(this).get(AddModel::class.java)
    }

    fun newInstance(): AddFragment4 {
        val args = Bundle()
        
        val fragment = AddFragment4()
        fragment.arguments = args
        return fragment
    }

    override fun initView() {
        super.initView()
    }
}