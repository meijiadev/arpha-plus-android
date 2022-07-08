package com.blackview.module_vip.dialog

import android.content.Context
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.module_vip.R
import com.blackview.module_vip.adapter.NotifyAdapter
import com.blackview.repository.entity.Notifications
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.lxj.xpopup.core.CenterPopupView


/**
 *    author : MJ
 *    time   : 2022/07/08
 *    desc   : 通知设定的弹窗
 */
class MessageSettingDialog(context: Context) : CenterPopupView(context), OnItemChildClickListener {
    private val tvTitle: AppCompatTextView by lazy { findViewById(R.id.tvTitle) }

    private val tvAllSelect: AppCompatTextView by lazy { findViewById(R.id.tvAllSelect) }
    private val btCancel: AppCompatButton by lazy { findViewById(R.id.btCancel) }

    private val btConfirm: AppCompatButton by lazy { findViewById(R.id.btConfirm) }

    private val recyclerSettings: RecyclerView by lazy { findViewById(R.id.recyclerSettings) }

    private val settings = mutableListOf<SettingItem>()

    private var devName: String? = null

    private var notifyAdapter: NotifyAdapter? = null


    private var onCancelAction: (() -> Unit)? = null

    private var onConfirmAction: (() -> Unit)? = null

    private var notify: Notifications? = null


    override fun getImplLayoutId(): Int = R.layout.dialog_message_setting


    override fun init() {
        super.init()
        notifyAdapter = NotifyAdapter()
        recyclerSettings.layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerSettings.adapter = notifyAdapter
        notifyAdapter?.data = settings
        notifyAdapter?.setOnItemChildClickListener(this)

        btCancel.setOnClickListener {
            onCancelAction?.invoke()
            dismiss()
        }

        btConfirm.setOnClickListener {
            onConfirmAction?.invoke()
            dismiss()
        }

        tvAllSelect.setOnClickListener {
            for (set in settings) {
                set.open = true
            }
            notifyAdapter?.notifyDataSetChanged()
        }

        tvTitle.text = devName
    }

    fun onCancel(action: () -> Unit): MessageSettingDialog = apply {
        onCancelAction = action
    }

    fun onConfirm(action: () -> Unit): MessageSettingDialog = apply {
        onConfirmAction = action
    }

    fun setNotify(notify: Notifications): MessageSettingDialog = apply {
        this.notify = notify
        settings.add(
            SettingItem(
                context.getString(com.blackview.common_res.R.string.door_bell),
                notify.door_bell
            )
        )
        settings.add(
            SettingItem(
                context.getString(com.blackview.common_res.R.string.door_open),
                notify.door_open
            )
        )
        settings.add(
            SettingItem(
                context.getString(com.blackview.common_res.R.string.door_alert),
                notify.door_alert
            )
        )
    }

    fun setDeviceName(name: String): MessageSettingDialog = apply {
        devName = name
    }


    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        val switch = settings[position].open
        settings[position].open = !switch
        notifyAdapter?.notifyDataSetChanged()
    }

    fun getDevNotify(): Notifications? {
        return notify
    }


    data class SettingItem(val msg: String, var open: Boolean)
}