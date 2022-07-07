package com.blackview.module_vip.adapter

import androidx.appcompat.widget.AppCompatTextView
import com.blackview.module_vip.R
import com.blackview.repository.entity.ShareMember
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder


/**
 *    author : MJ
 *    time   : 2022/07/06
 *    desc   : 朋友列表适配器
 */
class MembersAdapter :BaseQuickAdapter<ShareMember,BaseViewHolder>{

    constructor(layoutRes:Int=R.layout.item_share_member):super(layoutRes){
        addChildClickViewIds(R.id.ivDelete)
    }


    override fun convert(holder: BaseViewHolder, item: ShareMember) {
        holder.getView<AppCompatTextView>(R.id.tvMemberName).text=item.name
    }

}