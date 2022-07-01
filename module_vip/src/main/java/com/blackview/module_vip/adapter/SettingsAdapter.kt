package com.blackview.module_vip.adapter

import androidx.appcompat.widget.AppCompatTextView
import com.blackview.module_vip.R
import com.blackview.module_vip.VipFragment
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


/**
 *    author : MJ
 *    time   : 2022/07/01
 *    desc   : 设置列表适配器
 */
class SettingsAdapter :BaseQuickAdapter<VipFragment.SettingItem,BaseViewHolder>(R.layout.item_settings) {

    override fun convert(holder: BaseViewHolder, item: VipFragment.SettingItem) {
        holder.getView<AppCompatTextView>(R.id.tvLeftName).let {
            it?.text=item.itemName
            it?.setCompoundDrawablesWithIntrinsicBounds(item.iconRes,0,0,0)
        }
        holder.getView<AppCompatTextView>(R.id.tvRightMsg).text=item.rightMsg
    }

}