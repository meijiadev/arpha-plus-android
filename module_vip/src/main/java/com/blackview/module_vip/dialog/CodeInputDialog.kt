package com.blackview.module_vip.dialog

import android.content.Context
import com.blackview.module_vip.R
import com.blackview.module_vip.account.model.AccountModel
import com.lxj.xpopup.core.CenterPopupView


/**
 *    author : MJ
 *    time   : 2022/07/20
 *    desc   : 图形验证码输入弹窗
 */
class CodeInputDialog(context: Context, accountModel: AccountModel?) : CenterPopupView(context) {

    private var accountModel: AccountModel? = null

    init {
        this.accountModel = accountModel
    }

    override fun getImplLayoutId(): Int {
        return R.layout.dialog_code_input
    }

    override fun init() {
        super.init()
        accountModel?.pictureCodeEvent?.observe(this) {

        }
        accountModel?.getPictureCode()
    }
}