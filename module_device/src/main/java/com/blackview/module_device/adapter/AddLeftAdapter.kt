package com.blackview.module_device.adapter

import com.blackview.module_device.R
import com.blackview.repository.entity.ProductType
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
 * Created by home on 2022/6/28.
 */
class AddLeftAdapter : BaseQuickAdapter<ProductType, BaseViewHolder>(R.layout.item_add_left) {

    override fun convert(holder: BaseViewHolder, item: ProductType) {
        if (item.isSelect) {
            holder.setGone(R.id.tv_left_title, false)
            holder.setGone(R.id.tv_left_title1, true)
        } else {
            holder.setGone(R.id.tv_left_title, true)
            holder.setGone(R.id.tv_left_title1, false)
        }
        holder.setText(R.id.tv_left_title, item.name)
        holder.setText(R.id.tv_left_title1, item.name)
    }
}