package com.blackview.module_device.adapter

import com.blackview.module_device.DeviceInfoAty
import com.blackview.module_device.R
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 *
 * Created by home on 2022/7/12.
 */
class DeviceInfoAdapter : BaseQuickAdapter<DeviceInfoAty.DeviceEntity, BaseViewHolder>(R.layout.item_device_info) {
    override fun convert(holder: BaseViewHolder, item: DeviceInfoAty.DeviceEntity) {
        holder.setImageResource(R.id.iv_device_info_img, item.res)
        holder.setText(R.id.tv_device_info_text, item.text)
    }
}