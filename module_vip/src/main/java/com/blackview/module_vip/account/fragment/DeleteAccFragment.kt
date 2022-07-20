package com.blackview.module_vip.account.fragment

import android.annotation.SuppressLint
import android.os.CountDownTimer
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.base.BaseMVFragment
import com.blackview.base.base.BaseViewModel
import com.blackview.module_vip.account.model.AccountModel
import com.blackview.module_vip.databinding.FragmentDeleteAccountBinding
import com.blackview.module_vip.dialog.TipsDialog
import com.blackview.module_vip.dialog.showSuccessTips
import com.blackview.repository.login.LoginActivity
import com.blackview.util.DateUtil
import com.blackview.util.gotoAct
import com.lxj.xpopup.XPopup
import com.lxj.xpopup.enums.PopupAnimation
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


/**
 *    author : MJ
 *    time   : 2022/07/18
 *    desc   : 删除账号
 */
class DeleteAccFragment : BaseMVFragment<FragmentDeleteAccountBinding, BaseViewModel>() {

    private var accountModel: AccountModel? = null

    private var countDownTimber: CountDownTimer? = null

    override fun createViewModel(fragment: Fragment): BaseViewModel {
        return ViewModelProvider(this).get(BaseViewModel::class.java)
    }

    override fun initView() {
        super.initView()
        accountModel = getActivityViewModel(AccountModel::class.java)
        accountModel?.titleAction(getString(com.blackview.common_res.R.string.delete_account))
        // 设置时间
        val text = getString(com.blackview.common_res.R.string.delete_account_tips)
        binding.tvDeleteTips.text = String.format(text, DateUtil.getCurrentDateTime())
        binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
    }

    override fun initData() {
        super.initData()
    }

    override fun initListener() {
        super.initListener()
        // 下一步按钮
        binding.btNextStep.setOnClickListener {
            binding.llFirstStep.visibility = View.GONE
            binding.rlSecond.visibility = View.VISIBLE
        }

        // 密码是否可见的按钮
        binding.ivPswVisible.setOnClickListener {
            if (binding.etPassword.transformationMethod == PasswordTransformationMethod.getInstance()) {
                binding.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
                binding.ivPswVisible.setImageResource(com.blackview.common_res.R.drawable.icon_eyevisible_20)
            } else {
                binding.etPassword.transformationMethod = PasswordTransformationMethod.getInstance()
                binding.ivPswVisible.setImageResource(com.blackview.common_res.R.drawable.icon_eyeinvisible_20)
            }
        }

        // 发送验证码
        binding.tvSendCode.setOnClickListener {
            accountModel?.sendCode()
            timber()
        }

        // 删除按钮
        binding.btDelete.setOnClickListener {
            val password = binding.etPassword.text.toString().trim()
            val code = binding.etCode.text.toString().trim()
            deleteLoading()
            accountModel?.deleteAccount(password, code)

        }
    }

    override fun initViewObservable() {
        super.initViewObservable()
        accountModel?.deleteAccountEvent?.observe(viewLifecycleOwner) {
            it?.let {
                if (it) {
                    MainScope().launch(Dispatchers.Main) {
                        tipsDialog?.dismiss()
                        showSuccessTips(
                            getString(com.blackview.common_res.R.string.text_delete_success),
                            1000
                        )
                        delay(1000)
                        mActivity.gotoAct<LoginActivity>()
                    }
                } else {
                    tipsDialog?.dismiss()
                    showSuccessTips(
                        getString(com.blackview.common_res.R.string.text_change_failed),
                        2000
                    )
                }
            }
        }
    }


    private fun timber() {
        countDownTimber = object : CountDownTimer(1000 * 60, 1000) {
            @SuppressLint("SetTextI18n")
            override fun onTick(p0: Long) {
                //  Logger.i("当前时间：$p0")
                val sec = p0 / 1000
                binding.tvSendCode.text =
                    "${sec}s${getString(com.blackview.common_res.R.string.reset_send)}"
                val color =
                    resources.getColorStateList(com.blackview.common_res.R.color.gray_A7A9AC)
                binding.tvSendCode.setTextColor(color)
            }

            override fun onFinish() {
                Logger.i("计时结束")
                binding.tvSendCode.text = getString(com.blackview.common_res.R.string.send_code)
                val color =
                    resources.getColorStateList(com.blackview.common_res.R.color.text_BC9E70)
                binding.tvSendCode.setTextColor(color)

            }
        }
        countDownTimber?.start()
    }

    private var tipsDialog: TipsDialog? = null
    private fun deleteLoading() {
        tipsDialog = TipsDialog(requireContext()).setDrawVisible(false)
            .setMessage(getString(com.blackview.common_res.R.string.current_delete_tips))
        XPopup.Builder(requireContext())
            .isViewMode(true)
            .dismissOnTouchOutside(false)
            .popupAnimation(PopupAnimation.TranslateFromBottom)
            .asCustom(tipsDialog)
            .show()
    }


    override fun onDestroy() {
        super.onDestroy()
        countDownTimber?.cancel()
        countDownTimber == null
    }


}