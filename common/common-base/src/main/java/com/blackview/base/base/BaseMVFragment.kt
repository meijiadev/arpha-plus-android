package com.blackview.base.base

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import java.lang.reflect.ParameterizedType
import java.nio.file.attribute.AclEntry.newBuilder

abstract class BaseMVFragment<V : ViewBinding, VM : BaseViewModel> : Fragment(),
    IBaseView {

    private var _binding: V? = null
    protected val binding: V get() = _binding!!
    protected lateinit var viewModel: VM
    lateinit var mActivity: AppCompatActivity
    private var progressDialog: ProgressDialog? = null
    private var mActivityProvider: ViewModelProvider?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<V>
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        _binding = method.invoke(null, layoutInflater, container, false) as V
        viewModel = createViewModel(this)

        return _binding!!.root
    }

    /**
     * 创建ViewModel 需子类必须实现
     *
     * @param fragment
     * @return VM
     */
    abstract fun createViewModel(fragment: Fragment): VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        progressDialog = ProgressDialog.Builder(requireContext()).noClose().get()
        initParam()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()

        initData()

        initListener()

        initViewObservable()

        viewModel.uiChangeLiveData.showDialogEvent.observe(viewLifecycleOwner, Observer {
            if (progressDialog != null) {
                progressDialog?.show()
            } else {
                progressDialog = ProgressDialog.newBuilder(requireActivity()).noClose().get()
                progressDialog?.show()
            }
        })

        viewModel.uiChangeLiveData.dismissDialogEvent.observe(viewLifecycleOwner, Observer {
            progressDialog?.dismiss()
        })
        viewModel.uiChangeLiveData.finishEvent.observe(viewLifecycleOwner, Observer {
            activity?.onBackPressed()
        })
        viewModel.uiChangeLiveData.toastEvent.observe(viewLifecycleOwner, Observer {
            if (it.contains("unauthenticated")) {
                activity?.finish()
            }
            toastShort(it)
        })
        viewModel.uiChangeLiveData.uiMessageEvent.observe(viewLifecycleOwner, Observer {
            handleEvent(it)
        })

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }


    /**
     * 获取Activity作用域的ViewModel
     */
    protected fun <T: ViewModel> getActivityViewModel(modelClass:Class<T>):T?{
        if (mActivityProvider==null){
            mActivityProvider= ViewModelProvider(mActivity)
        }
        return mActivityProvider?.get(modelClass)
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

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun toastShort(msg: String) {
        ToastUtils.make().apply {
            setGravity(Gravity.CENTER, 0, 0)
            setBgColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.blackview.common_res.R.color.black
                )
            )
            setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    com.blackview.common_res.R.color.white
                )
            )
            show(msg)
        }
    }
}