package com.blackview.module_device.add

import android.graphics.Rect
import android.os.Bundle
import android.view.View
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
import com.blackview.util.gotoAct
import com.blankj.utilcode.util.SizeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener

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
class AddFragment1 : BaseMVFragment<FragmentAddOneBinding, AddModel>() {

    private val leftAdapter by lazy { AddLeftAdapter() }
    private val rightAdapter by lazy { AddRightAdapter() }

    override fun createViewModel(fragment: Fragment): AddModel {
        return ViewModelProvider(this).get(AddModel::class.java)
    }

    fun newInstance(): AddFragment1 {
        val args = Bundle()
        val fragment = AddFragment1()
        fragment.arguments = args
        return fragment
    }


    override fun initView() {
        super.initView()
        binding.recyclerLeft.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            adapter = leftAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.bottom = SizeUtils.dp2px(20f)
                }
            })
        }
        leftAdapter.addData(listOf("dd", "dd", "dd", "dd"))

        binding.recyclerRight.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(activity, 2)
            adapter = rightAdapter
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.bottom = SizeUtils.dp2px(20f)
                }
            })
        }
        rightAdapter.addData(listOf("aa", "aa", "aa", "aa"))
    }

    override fun initParam() {
        super.initParam()
        rightAdapter.setOnItemClickListener { adapter, view, position ->
            (activity as AddAty).showFragment2()
        }
    }
} 