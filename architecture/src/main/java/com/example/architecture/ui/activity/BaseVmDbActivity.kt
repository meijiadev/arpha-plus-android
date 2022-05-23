package com.example.architecture.ui.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.architecture.viewmodel.BaseViewModel


/**
 *    author : MJ
 *    time   : 2022/04/11
 *    desc   : 包含ViewModel和Databind ViewModelActivity基类，把ViewModel 和Databind注入进来
 */
abstract class BaseVmDbActivity<VM:BaseViewModel,DB:ViewDataBinding> :BaseVmActivity<VM>(){

    lateinit var mDatabind:DB

    private var mActivityProvider: ViewModelProvider?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    /**
     * 创建DataBinding
     */
    override fun initDataBind() {
        mDatabind=DataBindingUtil.setContentView(this,layoutId())
        mDatabind.lifecycleOwner=this
    }

    /**
     * 获取Activity作用域的ViewModel
     */
    protected fun <T: ViewModel> getActivityViewModel(modelClass:Class<T>):T?{
        if (mActivityProvider==null){
            mActivityProvider=  ViewModelProvider(this)
        }
        return mActivityProvider?.get(modelClass)
    }

}