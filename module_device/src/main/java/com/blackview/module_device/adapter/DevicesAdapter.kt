package com.blackview.module_device.adapter

import android.widget.ImageView
import coil.load
import com.blackview.module_device.R
import com.blackview.repository.entity.Device
import com.blackview.repository.showImgRound
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
 * Created by home on 2022/7/4.
 */
class DevicesAdapter : BaseQuickAdapter<Device, BaseViewHolder>(R.layout.item_devices) {

    init {
        addChildClickViewIds(R.id.tv_device_more_info)
    }

    override fun convert(holder: BaseViewHolder, item: Device) {
        holder.setText(R.id.tv_device_title, item.device_name)
        if (item.wifi == 0) {
            holder.setText(R.id.tv_device_wifi_status, com.blackview.common_res.R.string.no_connected)
        } else {
            holder.setText(R.id.tv_device_wifi_status, com.blackview.common_res.R.string.had_connected)
        }
        if (item.power == -1) {
            holder.setText(R.id.tv_device_battery_status, com.blackview.common_res.R.string.unknown)
        } else {
            holder.setText(R.id.tv_device_battery_status, "" + item.power)
        }
        val s = StringBuilder()
        if (item.notifications.door_alert) {
            s.append("告警通知 ")
        }
        if (item.notifications.door_bell) {
            s.append("门铃通知 ")
        }
        if (item.notifications.door_open) {
            s.append("开门通知 ")
        }
        holder.setText(R.id.iv_device_status1, s.toString())

        holder.getView<ImageView>(R.id.iv_device_img).showImgRound(item.img_url)

    }
}