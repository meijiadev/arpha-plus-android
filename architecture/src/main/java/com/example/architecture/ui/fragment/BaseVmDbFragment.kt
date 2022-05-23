package com.example.architecture.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.architecture.viewmodel.BaseViewModel


/**
 *    author : MJ
 *    time   : 2022/04/12
 *    desc   : 集成ViewModel和DataBinding的基类
 */
abstract class BaseVmDbFragment<VM:BaseViewModel,DB:ViewDataBinding>:BaseVmFragment<VM>() {
    //该类绑定的ViewDataBinding
    lateinit var mDatabind: DB

    private var mActivityProvider: ViewModelProvider?=null

    private var mFragmentProvider: ViewModelProvider?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabind = DataBindingUtil.inflate(inflater, layoutId(), container, false)
        mDatabind.lifecycleOwner = this
        return mDatabind.root
    }

    /**
     * 获取Activity作用域的ViewModel
     */
    protected fun <T: ViewModel> getActivityViewModel(modelClass:Class<T>):T?{
        if (mActivityProvider==null){
            mActivityProvider= mActivity?.let { ViewModelProvider(it) }
        }
        return mActivityProvider?.get(modelClass)
    }

    /**
     * 获取作用域为Fragment的ViewModel
     */
    protected fun <T: ViewModel> getFragmentViewModel(modelClass:Class<T>):T?{
        if (mFragmentProvider==null){
            mFragmentProvider= ViewModelProvider(this)
        }
        return mFragmentProvider?.get(modelClass)
    }

}