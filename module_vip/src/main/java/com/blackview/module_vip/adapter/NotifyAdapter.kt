package com.blackview.module_vip.adapter

import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.blackview.module_vip.R
import com.blackview.module_vip.dialog.MessageSettingDialog
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


/**
 *    author : MJ
 *    time   : 2022/07/08
 *    desc   : 设备通知设置
 */
class NotifyAdapter :
    BaseQuickAdapter<MessageSettingDialog.SettingItem, BaseViewHolder> {

    constructor():super(R.layout.item_notify_device){
        addChildClickViewIds(R.id.ivSwitch)
    }

    override fun convert(holder: BaseViewHolder, item: MessageSettingDialog.SettingItem) {
        holder.getView<AppCompatTextView>(R.id.tvSettingName).text = item.msg
        holder.getView<AppCompatImageView>(R.id.ivSwitch)
            .setImageResource(
                if (item.open) com.blackview.common_res.R.drawable.switch_ckecked_1
                else com.blackview.common_res.R.drawable.switch_unckecked_1
            )
    }

}