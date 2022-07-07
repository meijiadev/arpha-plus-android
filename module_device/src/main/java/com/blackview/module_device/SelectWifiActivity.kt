package com.blackview.module_device

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blackview.base.base.BaseMVActivity
import com.blackview.contant.USER_TOKEN
import com.blackview.contant.WIFI_SSID
import com.blackview.module_device.adapter.WifiAdapter
import com.blackview.module_device.databinding.ActivitySelectWifiBinding
import mykj.stg.aipn.lgUtil.LogUtil
import permissions.dispatcher.RuntimePermissions

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
 * Created by home on 2022/7/1.
 */
class SelectWifiActivity : BaseMVActivity<ActivitySelectWifiBinding, DeviceModel>() {

    private val wifiAdapter by lazy { WifiAdapter() }

    override fun getViewBinding(): ActivitySelectWifiBinding {
        return ActivitySelectWifiBinding.inflate(layoutInflater)
    }

    override fun initView() {
        super.initView()
        val mWifiManager = applicationContext.getSystemService(WIFI_SERVICE) as WifiManager
        val receiver: BroadcastReceiver = WifiScanReceiver(mWifiManager)
        registerReceiver(
            receiver,
            IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        )
        getPageHead(this).apply {
            setRightTitleText("刷新")
            setTitleText(
                getString(com.blackview.common_res.R.string.select_wifi)
            )
            setOnRightClick {
                mWifiManager.startScan()
            }
        }

        binding.wifiRecyclerView.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@SelectWifiActivity)
            adapter = wifiAdapter
        }

        mWifiManager.startScan()

    }

    override fun initListener() {
        super.initListener()
        wifiAdapter.setOnItemClickListener { adapter, view, position ->
            val bean = adapter.getItem(position) as ScanResult
            setResult(RESULT_OK, Intent().apply { putExtra(WIFI_SSID,bean) })
            finish()
        }
    }

    inner class WifiScanReceiver(var mWifiManager: WifiManager) : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            if (intent.action == WifiManager.SCAN_RESULTS_AVAILABLE_ACTION) {
                val scanResults = mWifiManager.scanResults
                val dd=scanResults.distinctBy { it.SSID }.filter { x: ScanResult? -> x?.SSID != "" }
                wifiAdapter.setList(dd)
            }
        }
    }
}