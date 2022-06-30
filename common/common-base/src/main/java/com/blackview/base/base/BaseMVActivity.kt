package com.blackview.base.base

import android.app.Activity
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blackview.base.R
import com.blackview.base.titlebar.IPageHead
import com.blackview.base.titlebar.PageHead
import com.blankj.utilcode.util.ToastUtils
import java.lang.reflect.ParameterizedType

abstract class BaseMVActivity<V : ViewBinding, VM : BaseViewModel> : AppCompatActivity(),
    IBaseView, IPageHead {

    protected lateinit var binding: V
    protected lateinit var viewModel: VM

    protected var progressDialog: ProgressDialog? = null

    private lateinit var contentLayout: LinearLayout
    private lateinit var pageHead: PageHead

    abstract fun getViewBinding(): V

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding()
        viewModel = ViewModelProvider(this).get(getViewModelClass())
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
        val lp = ViewGroup.LayoutParams(-1, -1)
        contentLayout.addView(view, lp)
    }

    private fun getViewModelClass(): Class<VM> {
        val type = (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[1]
        return type as Class<VM>
    }

    fun hideTitleBar() {
        contentLayout.findViewById<RelativeLayout>(R.id.layoutTitleBar)?.isVisible = false
    }

    fun showTitleBar() {
        contentLayout.findViewById<RelativeLayout>(R.id.layoutTitleBar)?.isVisible = true
    }

    private fun onViewCreated() {

        initView()
        initData()
        initListener()
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
            if (it.contains("unauthenticated")){
                finish()
            }
            toastShort(it)
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

    override fun initListener() {
        
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

    fun getResString(res: Int): String {
        return resources.getString(res)
    }

    private fun toastShort(msg: String) {
        ToastUtils.make().apply {
            setGravity(Gravity.CENTER, 0, 0)
            setBgColor(ContextCompat.getColor(this@BaseMVActivity, com.blackview.common_res.R.color.black))
            setTextColor(ContextCompat.getColor(this@BaseMVActivity, com.blackview.common_res.R.color.white))
            show(msg)
        }
    }

    var isEnableHideSoftInputFromWindow = false

    /**
     * 触摸空白区域自动隐藏键盘
     *
     * @param ev
     * @return
     */
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        if (ev.action == MotionEvent.ACTION_DOWN && isEnableHideSoftInputFromWindow) {
            currentFocus?.apply {
                if (isShouldHideKeyboard(this, ev)) {
                    val imm = getSystemService(INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
                }
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    // 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
    private fun isShouldHideKeyboard(v: View, event: MotionEvent): Boolean {
        if (v is EditText) {
            val l = intArrayOf(0, 0)
            v.getLocationInWindow(l)
            val left = l[0]
            val top = l[1]
            val bottom = top + v.getHeight()
            val right = left + v.getWidth()
            return !(event.x > left && event.x < right && event.y > top && event.y < bottom)
        }
        return false
    }
}