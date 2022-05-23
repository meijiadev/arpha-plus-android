package com.example.architecture.ui.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import com.example.architecture.action.HandlerAction
import com.example.architecture.action.ToastAction
import com.example.architecture.ext.getVmClazz
import com.example.architecture.viewmodel.BaseViewModel


/**
 *    author : MJ
 *    time   : 2022/04/12
 *    desc   :ViewModelFragment基类，自动注入ViewMdoel
 */
abstract class BaseVmFragment<VM:BaseViewModel> :Fragment(),HandlerAction ,ToastAction{
    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mViewModel: VM

    lateinit var mActivity: AppCompatActivity

    /**
     * 当前Fragment绑定的视图布局
     */
    abstract fun layoutId(): Int

    abstract fun initView()

    abstract fun initData()

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()

    /**
     * 显示加载对话框
     */
    abstract fun showLoading(message: Int)

    /**
     * 隐藏加载对话框
     */
    abstract fun hideDialog()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst=true
        mViewModel=createViewModel()
        registerUiChange()
        initView()
        initData()

    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     *注册UI事件
     */
    private fun registerUiChange() {
        mViewModel.loadingChange.getShowDialogEvent().observe(this) {
            showLoading(it)
        }
        mViewModel.loadingChange.getDisDialogEvent().observe(this) {
            hideDialog()
        }
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            // 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿
            getHandler().postDelayed( {
                lazyLoadData()
                isFirst = false
            },lazyLoadTime())
        }
    }

    /**
     * 延迟加载 防止 切换动画还没执行完毕时数据就已经加载好了，这时页面会有渲染卡顿  bug
     * 这里传入你想要延迟的时间，延迟时间可以设置比转场动画时间长一点 单位： 毫秒
     * @return Long
     */
    open fun lazyLoadTime(): Long {
        return 400
    }

    override fun onDestroy() {
        super.onDestroy()
        removeCallbacks()
    }

    /**
     * 创建viewModel
     */
    private fun createViewModel():VM{
        return ViewModelProvider(this).get(getVmClazz(this))
    }

    /**
     * 跳转 Activity 简化版
     */
    open fun startActivity(clazz: Class<out Activity>) {
        startActivity(Intent(context, clazz))
    }


}