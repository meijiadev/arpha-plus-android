package com.blackview.module_vip.dialog

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.module_vip.R
import com.blackview.module_vip.adapter.MembersAdapter
import com.blackview.repository.entity.ShareMember
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.lxj.xpopup.core.CenterPopupView
import com.orhanobut.logger.Logger


/**
 *    author : MJ
 *    time   : 2022/07/05
 *    desc   : 分享设备的弹窗
 */
class ShareDeviceDialog(context: Context) : CenterPopupView(context), OnItemChildClickListener {
    /**
     * 该设置共享的人集合
     */
    private var memberList = mutableListOf<ShareMember>()

    /**
     * 共享者的memberId数组
     */
    private var memberIDs = mutableListOf<String>()

    /**
     * member的列表适配器
     */
    private var membersAdapter: MembersAdapter? = null


    private val tvTitle: AppCompatTextView by lazy { findViewById(R.id.tvTitle) }

    private val etPhone: EditText by lazy { findViewById(R.id.etPhone) }

    private val btJoin: AppCompatButton by lazy { findViewById(R.id.btJoin) }

    private val recyclerFriend: RecyclerView by lazy { findViewById(R.id.recyclerFriend) }

    private val btCancel: AppCompatButton by lazy { findViewById(R.id.btCancel) }

    private val btConfirm: AppCompatButton by lazy { findViewById(R.id.btConfirm) }


    private var onJoinAction: ((data: String) -> Unit)? = null

    private var onCancelAction: (() -> Unit)? = null

    private var onConfirmAction: ((data:Array<String>) -> Unit)? = null


    override fun getImplLayoutId(): Int = R.layout.dialog_share_device

    override fun onCreate() {
        super.onCreate()
        tvTitle.post {
            val drawable: Drawable =
                resources.getDrawable(com.blackview.common_res.R.drawable.yellow_line_l)
            val width = tvTitle.width
            drawable.setBounds(0, 0, width, 10)
            tvTitle.setCompoundDrawables(null, null, null, drawable)
        }

        btJoin.setOnClickListener {
            val phone = etPhone.text.toString().trim()
            if (!phone.isNullOrEmpty()) {
                onJoinAction?.invoke(phone)
            } else {
                ToastUtils.showShort(com.blackview.common_res.R.string.input_phone)
            }
        }

        btCancel.setOnClickListener {
            onCancelAction?.invoke()
            dismiss()
        }

        btConfirm.setOnClickListener {
            for (member in memberList){
                memberIDs.add(member.member_id.toString())
            }
            onConfirmAction?.invoke(memberIDs.toTypedArray())
            dismiss()
        }
    }

    override fun init() {
        super.init()
        Logger.i("设置的集合数量：${memberList.size}")
        membersAdapter = MembersAdapter()
        recyclerFriend.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerFriend.adapter = membersAdapter
        membersAdapter?.data = memberList
        membersAdapter?.setOnItemChildClickListener(this)
    }

    fun onJoin(action: ((data: String) -> Unit)): ShareDeviceDialog = apply {
        onJoinAction = action
    }

    fun onCancel(action: () -> Unit): ShareDeviceDialog = apply {
        onCancelAction = action
    }

    fun onConfirm(action: (data:Array<String>) -> Unit): ShareDeviceDialog = apply {
        onConfirmAction = action
    }

    /**
     * 设置装置分享者列表
     */
    fun setData(data: MutableList<ShareMember>): ShareDeviceDialog = apply {
        memberList = data
    }

    /**
     * 添加设备
     */
    fun addData(member: ShareMember) {
        memberList.add(member)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        memberList.removeAt(position)
        membersAdapter?.notifyDataSetChanged()
    }


}