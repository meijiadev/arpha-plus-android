package com.blackview.module_device

import android.content.DialogInterface
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blackview.base.base.BaseMVFragment
import com.blackview.module_device.adapter.DevicesAdapter
import com.blackview.module_device.add.AddAty
import com.blackview.module_device.databinding.FragmentDeviceBinding
import com.blackview.module_device.dialog.DeviceBottomDialog
import com.blackview.repository.entity.Device
import com.blackview.util.gotoAct
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
 * Created by home on 2022/6/28.
 */
class DeviceFragment : BaseMVFragment<FragmentDeviceBinding, DeviceModel>() {

    private val devicesAdapter by lazy { DevicesAdapter() }
    private var dialog: DeviceBottomDialog? = null

    override fun createViewModel(fragment: Fragment): DeviceModel {
        return ViewModelProvider(this).get(DeviceModel::class.java)
    }

    override fun initView() {
        super.initView()
        binding.ivDeviceAdd.setOnClickListener {
            activity?.gotoAct<AddAty>()
        }
        binding.deviceRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(object : RecyclerView.ItemDecoration() {
                override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
                    super.getItemOffsets(outRect, view, parent, state)
                    outRect.top = SizeUtils.dp2px(20f)
                }
            })
            adapter = devicesAdapter
        }
        viewModel.devices()
    }

    override fun initListener() {
        super.initListener()

        devicesAdapter.setOnItemChildClickListener { adapter, view, position ->
            val device = adapter.getItem(position) as Device
            if (dialog == null) {
                dialog = DeviceBottomDialog()
            }
            dialog?.setDismissListener(object : DeviceBottomDialog.DismissListener {
                override fun onDismiss() {
                    viewModel.devices()
                }
            })
            dialog?.show(requireActivity().supportFragmentManager, "")

        }

        devicesAdapter.setOnItemClickListener { adapter, view, position ->
            val device = adapter.getItem(position) as Device
            activity?.gotoAct<DeviceInfoAty>(Bundle().apply {
                putParcelable("deviceTitle", device)
            })
        }

    }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.liveDevices.observe(this) {
            if (it.isNotEmpty()) {
                devicesAdapter.setList(it)
            } else {
                devicesAdapter.setEmptyView(R.layout.layout_device_empty)
            }
        }
    }


}