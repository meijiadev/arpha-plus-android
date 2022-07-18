package com.blackview.module_vip.account.fragment

import android.annotation.SuppressLint
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.contant.PASSWORD
import com.blackview.module_vip.R
import com.blackview.module_vip.account.model.AccountModel
import com.blackview.module_vip.databinding.FragmentPasswordBinding
import com.blackview.module_vip.dialog.showSuccessTips
import com.blackview.util.SpUtil
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.logging.Handler
import java.util.regex.Pattern


/**
 *    author : MJ
 *    time   : 2022/07/17
 *    desc   : 密码更改
 */
class PasswordFragment : BaseMVFragment<FragmentPasswordBinding, BaseViewModel>() {

    private var accountModel: AccountModel? = null

    /**
     * 只允许输入字母和数字
     */
    private val regex: String = "[[a-zA-Z]|\\d]*"

    /**
     * 新建密码
     */
    private var retryPsw: String? = null

    override fun createViewModel(fragment: Fragment): BaseViewModel {
        return ViewModelProvider(this).get(BaseViewModel::class.java)
    }

    @SuppressLint("ResourceAsColor")
    override fun initView() {
        super.initView()
        accountModel = getActivityViewModel(AccountModel::class.java)
        accountModel?.titleAction(getString(com.blackview.common_res.R.string.modify_password))
        // 设置不可见
        binding.tvCurrentPassword.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.etPsw.transformationMethod = PasswordTransformationMethod.getInstance()
        binding.etRetryPsw.transformationMethod = PasswordTransformationMethod.getInstance()
        SpUtil.decodeString(PASSWORD)?.let {
            binding.tvCurrentPassword.text = it
        }
    }


    override fun initData() {
        super.initData()

        accountModel?.pswUpdateEvent?.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    SpUtil.encode(PASSWORD, retryPsw)
                    showSuccessTips(getString(com.blackview.common_res.R.string.password_modify_success))
                    MainScope().launch {
                        delay(3000)
                        findNavController().popBackStack()
                    }
                }
            }
        }
    }

    @SuppressLint("ResourceAsColor")
    override fun initListener() {
        super.initListener()
        binding.ivPswVisible1.setOnClickListener {
            if (binding.tvCurrentPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.tvCurrentPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.ivPswVisible1.setImageResource(com.blackview.common_res.R.drawable.icon_eyevisible_20)
            } else {
                binding.tvCurrentPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
                binding.ivPswVisible1.setImageResource(com.blackview.common_res.R.drawable.icon_eyeinvisible_20)
            }

        }

        binding.ivPswVisible2.setOnClickListener {
            if (binding.etPsw.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.etPsw.transformationMethod = HideReturnsTransformationMethod.getInstance()
                binding.ivPswVisible2.setImageResource(com.blackview.common_res.R.drawable.icon_eyevisible_20)
            } else {
                binding.etPsw.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivPswVisible2.setImageResource(com.blackview.common_res.R.drawable.icon_eyeinvisible_20)
            }
            binding.etPsw.let {
                it.setSelection(it.text?.length ?: 0)
            }

        }

        binding.ivPswVisible3.setOnClickListener {
            if (binding.etRetryPsw.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.etRetryPsw.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.ivPswVisible3.setImageResource(com.blackview.common_res.R.drawable.icon_eyevisible_20)
            } else {
                binding.etRetryPsw.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivPswVisible3.setImageResource(com.blackview.common_res.R.drawable.icon_eyeinvisible_20)
            }
            binding.etRetryPsw.let {
                it.setSelection(it.text?.length ?: 0)
            }
        }

        binding.btSave.setOnClickListener {
            val curPsw = binding.tvCurrentPassword.text.toString().trim()
            val password = binding.etPsw.text.toString().trim()
            retryPsw = binding.etRetryPsw.text.toString().trim()
            val redColor = resources.getColorStateList(com.blackview.common_res.R.color.red_E3243B)
            val grayColor =
                resources.getColorStateList(com.blackview.common_res.R.color.gray_898A8B)
            // 判断长度是否合格
            if (password.length <= 8 || (retryPsw?.length ?: 0) <= 8) {
                binding.tvTips1.visibility = View.VISIBLE
                binding.tvTips2.setTextColor(redColor)
                return@setOnClickListener
            }
            // 判断密码中是否包含非法字符 false-包含非法字符
            val isPass = Pattern.compile(regex).matcher(password + retryPsw).matches()
            if (!isPass) {
                binding.tvTips1.visibility = View.VISIBLE
                binding.tvTips2.setTextColor(redColor)
                return@setOnClickListener
            }
            binding.tvTips1.visibility = View.GONE
            binding.tvTips2.setTextColor(grayColor)
            // 判断两次输入的密码是否一致
            if (password == retryPsw) {
                accountModel?.changePassword(curPsw, password, retryPsw)
            } else {
                ToastUtils.showShort(getString(com.blackview.common_res.R.string.password_is_diff))
            }
        }

    }


}



