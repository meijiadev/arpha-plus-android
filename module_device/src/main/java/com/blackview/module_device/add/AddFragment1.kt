package com.blackview.module_device.add

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.base.base.BaseMVFragment
import com.blackview.module_device.adapter.AddLeftAdapter
import com.blackview.module_device.adapter.AddRightAdapter
import com.blackview.module_device.databinding.FragmentAddOneBinding
import com.blackview.repository.entity.ProductType
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

        viewModel.productType()
        
    }


    override fun initListener() {
        super.initListener()
        leftAdapter.setOnItemClickListener { adater, view, position ->
            val datas = adater.data as List<ProductType>
            val bean = adater.data[position] as ProductType
            if (!bean.isSelect) {
                datas.forEach {
                    it.isSelect = false
                }
                bean.isSelect = true
                adater.notifyDataSetChanged()
                viewModel.products(bean.name)
            }


        }
        rightAdapter.setOnItemClickListener { adapter, view, position ->
            (activity as AddAty).showFragment2()
        }
    }


    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.liveDataProductList.observe(this) {
            leftAdapter.setList(it)
        }
        viewModel.liveDataProducts.observe(this) {
            rightAdapter.addData(it)
        }

    }
} 