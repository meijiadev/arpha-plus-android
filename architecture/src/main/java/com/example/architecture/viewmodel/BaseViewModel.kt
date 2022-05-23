package com.example.architecture.viewmodel

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LifecycleRegistry
import androidx.lifecycle.ViewModel
import com.example.architecture.R
import com.example.architecture.action.ToastAction
import com.hjq.http.listener.OnHttpListener
import com.hjq.http.request.BodyRequest
import com.kunminx.architecture.ui.callback.UnPeekLiveData
import okhttp3.Call
import timber.log.Timber
import java.lang.Exception


/**
 *    author : MJ
 *    time   : 2022/02/25
 *    desc   : BaseViewModel
 */
class BaseViewModel :ViewModel(),LifecycleOwner, OnHttpListener<Any>, ToastAction {


    private  val mRegistry:LifecycleRegistry = LifecycleRegistry(this)

    init {
        mRegistry.currentState=Lifecycle.State.CREATED
    }

    override fun onCleared() {
        super.onCleared()
        mRegistry.currentState=Lifecycle.State.DESTROYED
        Timber.i("销毁${this.javaClass.simpleName}")
    }

    override fun onStart(call: Call?) {
        super.onStart(call)
        //loadingChange.showDialog()
    }


    override fun onSucceed(result: Any?) {

    }

    override fun onFail(e: Exception?) {
        Timber.i(e)
    }

    override fun onEnd(call: Call?) {
        super.onEnd(call)
        //loadingChange.dismissDialog()
    }

    override fun getLifecycle(): Lifecycle {
        return mRegistry
    }

    /**
     * 手动清除内存中的数据
     */
    open fun onHandClear(){

    }

    val loadingChange: UiLoadingChange by lazy { UiLoadingChange() }

    /**
     * 内置封装好的可通知Activity/fragment 显示隐藏加载框 因为需要跟网络请求显示隐藏loading配套才加的
     */
    inner class UiLoadingChange {
        //显示加载框
        private val showDialog by lazy { UnPeekLiveData<Int>() }
        //隐藏
        private val dismissDialog by lazy { UnPeekLiveData<Boolean>() }

        fun showDialog(){
            showDialog.postValue(R.string.common_loading)
        }

        fun dismissDialog(){
            dismissDialog.postValue(true)
        }

        fun getShowDialogEvent():UnPeekLiveData<Int>{
            return showDialog
        }

        fun getDisDialogEvent():UnPeekLiveData<Boolean>{
            return dismissDialog
        }

    }

}