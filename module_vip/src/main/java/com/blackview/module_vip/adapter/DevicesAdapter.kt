package com.blackview.module_vip.adapter

import android.view.View
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.blackview.module_vip.R
import com.blackview.repository.entity.DeviceData
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


/**
 *    author : MJ
 *    time   : 2022/07/05
 *    desc   : 设备列表
 */
class DevicesAdapter : BaseQuickAdapter<DeviceData, BaseViewHolder>(R.layout.item_device_layout) {
    /**
     * 是否处于选择设备的状态
     */
    private var selectedState = false
    override fun convert(holder: BaseViewHolder, item: DeviceData) {
        holder.getView<AppCompatTextView>(R.id.tvDeviceName).text = item.nick_name
        if (selectedState) {
            holder.getView<AppCompatImageView>(R.id.ivSelect).let {
                it.visibility = View.VISIBLE
                it.setImageResource(
                    if (item.selected) com.blackview.common_res.R.drawable.radio_active
                    else com.blackview.common_res.R.drawable.radio_input
                )
            }
        } else {
            holder.getView<AppCompatImageView>(R.id.ivSelect).visibility = View.GONE
        }
        holder.getView<RelativeLayout>(R.id.item_layout)
            .setBackgroundResource(
                if (item.selected) com.blackview.common_res.R.drawable.card_selected_shape
                else com.blackview.common_res.R.drawable.vip_card_shape
            )
    }


    fun setSelectState(state: Boolean) {
        selectedState = state
    }

    fun getSelectState():Boolean{
        return selectedState
    }


}