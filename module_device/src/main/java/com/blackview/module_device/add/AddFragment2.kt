package com.blackview.module_device.add

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.base.base.BaseMVFragment
import com.blackview.module_device.DeviceModel
import com.blackview.module_device.add.adapter.AddLeftAdapter
import com.blackview.module_device.add.adapter.AddRightAdapter
import com.blackview.module_device.databinding.FragmentAddOneBinding
import com.blackview.module_device.databinding.FragmentAddTwoBinding
import com.blankj.utilcode.util.SizeUtils

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
class AddFragment2 : BaseMVFragment<FragmentAddTwoBinding, AddModel>() {

    override fun createViewModel(fragment: Fragment): AddModel {
        return ViewModelProvider(this).get(AddModel::class.java)
    }

    fun newInstance(): AddFragment2 {
        val args = Bundle()
        val fragment = AddFragment2()
        fragment.arguments = args
        return fragment
    }

    fun initShowView() {
        binding.layoutAddHotdotQrcode.isVisible = true
        binding.layoutInputNumber.isVisible = false
    }

    override fun initView() {
        super.initView()
        binding.tvAddSelectHotQrcode.setOnClickListener {
            binding.layoutAddHotdotQrcode.isVisible = false
            binding.layoutInputNumber.isVisible = true
        }
    }
}