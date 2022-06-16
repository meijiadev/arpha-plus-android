package com.blackview.base.base

import android.app.ActionBar
import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.children
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.R
import com.blackview.base.titlebar.IPageHead
import com.blackview.base.titlebar.PageHead
import com.blackview.util.L
import com.blankj.utilcode.util.SnackbarUtils.addView
import com.blankj.utilcode.util.ToastUtils
import java.lang.reflect.ParameterizedType

abstract class BaseMVVMActivity<V : ViewDataBinding, VM : BaseViewModel> : AppCompatActivity(),
    IBaseView, IPageHead {

    protected lateinit var binding: V
    protected lateinit var viewModel: VM
    var viewModelId = 0

    protected var progressDialog: ProgressDialog? = null

    private lateinit var contentLayout: LinearLayout
    private lateinit var pageHead: PageHead

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId())
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(getViewModelClass())
        //XML的viewModel的绑定
        viewModelId = initVariableId()
        binding.setVariable(viewModelId, viewModel)
        initContentView()
        setContentView(binding.root)
        onViewCreated()
        progressDialog = ProgressDialog.Builder(this).noClose().get()
        initParam()
    }

    open fun initContentView() {
        val viewGroup: ViewGroup = findViewById(android.R.id.content)
        viewGroup.removeAllViews()
        contentLayout = LinearLayout(this)
        contentLayout.orientation = LinearLayout.VERTICAL
        contentLayout.setBackgroundResource(android.R.color.white)
        viewGroup.addView(contentLayout)
        LayoutInflater.from(this).inflate(R.layout.layout_title, contentLayout, true)
    }

    override fun setContentView(view: View) {
        contentLayout.addView(view)
    }

    abstract fun layoutId(): Int

    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
        return type as Class<VM>
    }

    /**
     * 初始化ViewModel的id
     *
     * @return BR的id
     */
    abstract fun initVariableId(): Int

    private fun onViewCreated() {

        initView()

        initData()

        initViewObservable()

        viewModel.uiChangeLiveData.showDialogEvent.observe(this, Observer {
            if (progressDialog != null) {
                progressDialog?.show()
            } else {
                progressDialog = ProgressDialog.newBuilder(this).noClose().get()
                progressDialog?.show()
            }
        })

        viewModel.uiChangeLiveData.dismissDialogEvent.observe(this, Observer {
            progressDialog?.dismiss()
        })
        viewModel.uiChangeLiveData.toastEvent.observe(this, Observer {
            ToastUtils.showShort(it)
        })
        viewModel.uiChangeLiveData.uiMessageEvent.observe(this, Observer {
            handleEvent(it)
        })

    }

    open fun handleEvent(message: UIMessage) {

    }

    override fun initParam() {
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun initViewObservable() {
        viewModel.observeLiveData(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        progressDialog?.cancel()
    }

    override fun getPageHead(
        activity: Activity?,
        listener: PageHead.OnPageHeadClickListener?
    ): PageHead {
        pageHead = PageHead(activity, listener)
        return pageHead
    }

    override fun getPageHead(activity: Activity?): PageHead {
        pageHead = PageHead(activity)
        return pageHead
    }
    
}