package com.blackview.module_device

import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.base.base.BaseMVActivity
import com.blackview.common_res.R
import com.blackview.module_device.adapter.DeviceInfoAdapter
import com.blackview.module_device.databinding.ActivityDeviceInfoBinding
import com.blackview.module_device.databinding.LayoutHeaderBinding
import com.blackview.repository.entity.Device
import com.blankj.utilcode.util.SizeUtils

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
 * Created by home on 2022/7/6.
 */
class DeviceInfoAty : BaseMVActivity<ActivityDeviceInfoBinding, DeviceModel>() {

    var device: Device? = null
    private val adapter by lazy { DeviceInfoAdapter() }
    private lateinit var viewHeader: LayoutHeaderBinding

    override fun getViewBinding(): ActivityDeviceInfoBinding {
        return ActivityDeviceInfoBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        viewHeader = LayoutHeaderBinding.inflate(LayoutInflater.from(this))

        device = intent?.getParcelableExtra("deviceTitle")
        getPageHead(this).setTitleText(device?.device_name)


        binding.deviceInfoRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = GridLayoutManager(this@DeviceInfoAty, 2)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    val itemPosition = parent.getChildAdapterPosition(view)
                    if (itemPosition == RecyclerView.NO_POSITION) {
                        return
                    }
                    val itemCount = state.itemCount
                    if (itemCount > 0 && itemPosition == itemCount - 1) {
                        outRect.set(0, 0, SizeUtils.dp2px(10f), SizeUtils.dp2px(30f))
                    } else if (itemPosition % 2 != 0) {
                        outRect.set(0, 0, SizeUtils.dp2px(5f), 0)
                    } else {
                        outRect.set(SizeUtils.dp2px(5f), 0, 0, 0)
                    }
                }
            })
            adapter = this@DeviceInfoAty.adapter

        }
        adapter.setNewInstance(getResList())

        adapter.addHeaderView(viewHeader.root)

        viewHeader.slideBar.setOnUnlockListenerLeft {
            viewModel.showToast("left")
        }
        viewHeader.slideBar.setOnUnlockListenerRight {
            viewModel.showToast("right")
        }
        adapter.setOnItemClickListener { adapter, view, position ->
            val bean = this.adapter.getItem(position)
            viewModel.showToast(bean.text)
        }


    }

    private fun getResList(): ArrayList<DeviceEntity> {
        val b1 = DeviceEntity(R.drawable.icon_info_1, getString(R.string.button_tips0))
        val b2 = DeviceEntity(R.drawable.icon_info_2, getString(R.string.button_tips1))
        val b3 = DeviceEntity(R.drawable.icon_info_3, getString(R.string.button_tips2))
        val b4 = DeviceEntity(R.drawable.icon_info_4, getString(R.string.button_tips3))
        val b5 = DeviceEntity(R.drawable.icon_info_5, getString(R.string.button_tips4))
        val b6 = DeviceEntity(R.drawable.icon_info_6, getString(R.string.button_tips5))
        val b7 = DeviceEntity(R.drawable.icon_info_7, getString(R.string.button_tips6))
        return arrayListOf(b1, b2, b3, b4, b5, b6, b7)
    }

    data class DeviceEntity(
        val res: Int,
        val text: String
    )

}