package com.blackview.module_device.add

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.app.Activity
import android.content.Context.WIFI_SERVICE
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.blackview.base.App
import com.blackview.base.base.BaseMVFragment
import com.blackview.contant.WIFI_SSID
import com.blackview.module_device.SelectWifiActivity
import com.blackview.module_device.databinding.FragmentAddTwoBinding
import com.blackview.module_device.wifitool.ZxingUtils
import com.blackview.module_device.wifitool.tookit.IWifiConnectListener
import com.blackview.module_device.wifitool.tookit.WifiManagerProxy
import com.blackview.util.L
import com.google.gson.Gson
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
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
 * Created by home on 2022/6/28.
 */
@RuntimePermissions
class AddFragment2 : BaseMVFragment<FragmentAddTwoBinding, AddModel>() {


    var isShowInputLayout = true
    private var scanResult: ScanResult? = null
    var ssid = ""
    var pwd = ""

    override fun createViewModel(fragment: Fragment): AddModel {
        return ViewModelProvider(this).get(AddModel::class.java)
    }

    fun newInstance(): AddFragment2 {
        val args = Bundle()
        val fragment = AddFragment2()
        fragment.arguments = args
        return fragment
    }

    fun initShowView() {
        isShowInputLayout = true
        binding.layoutAddHotdotQrcode.isVisible = true
        binding.layoutInputNumber.isVisible = false
    }

    override fun initView() {
        super.initView()
        initShowView()
        selectWifi1WithPermissionCheck()
    }

    override fun initListener() {
        super.initListener()
        binding.tvAddSelectHotQrcode.setOnClickListener {
            (activity as AddAty).setTitle2(getString(com.blackview.common_res.R.string.add_device22))
            binding.layoutAddHotdotQrcode.isVisible = false
            binding.layoutInputNumber.isVisible = true
            isShowInputLayout = false

            setWifiName()

        }

        binding.tvAddSelectNext.setOnClickListener {
            ssid = binding.tvAddSelectWifi.text.toString().trim()
            pwd = binding.tvLoginPwd.text.toString().trim()
            if (ssid.isEmpty()) {
                viewModel.showToast(getString(com.blackview.common_res.R.string.select_wifi))
            } else if (ssid.contains("5g")||ssid.contains("5G")) {
                viewModel.showToast(getString(com.blackview.common_res.R.string.add_device22))
            } else if (pwd.isEmpty()) {
                viewModel.showToast(getString(com.blackview.common_res.R.string.input_pwd))
            } else {
                viewModel.getMemberToken()
                //WifiManagerProxy.get().init(App.instance)
                //WifiManagerProxy.get().connect(text, pwd, object : IWifiConnectListener {
                //    override fun onConnectStart() {
                //        L.i("TAG", "onConnectStart: ")
                //    }
                //
                //    override fun onConnectSuccess() {
                //        L.i("TAG", "onConnectSuccess: ")
                //        ssid = scanResult!!.SSID
                //        this@AddFragment2.pwd = pwd
                //        viewModel.getMemberToken()
                //    }
                //
                //    override fun onConnectFail(errorMsg: String) {
                //        L.i("TAG", "onConnectFail: $errorMsg")
                //        viewModel.showToast(errorMsg)
                //    }
                //})
            }

        }

        binding.ivAddDeviceQrCode.setOnClickListener {
            (activity as AddAty).showFragment3()
        }
        binding.tvAddSelectWifi.setOnClickListener {
            selectWifiWithPermissionCheck()
        }
        binding.ivAddChangeIcon.setOnClickListener {
            selectWifiWithPermissionCheck()
        }
        binding.cbLoginPwd.setOnCheckedChangeListener { _, b ->
            if (b) {
                binding.tvLoginPwd.transformationMethod = HideReturnsTransformationMethod.getInstance()
            } else {
                binding.tvLoginPwd.transformationMethod = PasswordTransformationMethod.getInstance()
            }
        }

        binding.tvAddSelectNext22.setOnClickListener {
            (activity as AddAty).showFragment3()
        }

    }

    @NeedsPermission(ACCESS_FINE_LOCATION)
    fun selectWifi() {
        //activity?.gotoAct<SelectWifiActivity>()
        startActivityLauncher.launch(Intent(requireContext(), SelectWifiActivity::class.java))
    }

    @NeedsPermission(ACCESS_FINE_LOCATION)
    fun selectWifi1() {
        setWifiName()
    }

    private fun setWifiName() {
        val wifiManager: WifiManager? = activity?.applicationContext?.getSystemService(WIFI_SERVICE) as WifiManager?
        val wifiInfo: WifiInfo? = wifiManager?.connectionInfo
        if (wifiInfo?.ssid?.isNotEmpty() == true) {
            binding.tvAddSelectWifi.text = wifiInfo.ssid
        }
    }

    //如果用户未授予权限则调用的方法
    @OnPermissionDenied(ACCESS_FINE_LOCATION)
    fun onLocationDenied() {
        viewModel.showToast(getString(com.blackview.common_res.R.string.wifi_permission_tips1))
    }

    //如果用户选择让设备“不再询问”权限时调用的方法
    @OnNeverAskAgain(ACCESS_FINE_LOCATION)
    fun onLocationNeverAskAgain() {
        viewModel.showToast(getString(com.blackview.common_res.R.string.wifi_permission_tips2))
    }

    private val startActivityLauncher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                scanResult = it.data?.getParcelableExtra(WIFI_SSID)
                binding.tvAddSelectWifi.text = scanResult?.SSID
                binding.tvLoginPwd.setText("")
            }
        }

    override fun initViewObservable() {
        super.initViewObservable()
        viewModel.memberTokenEvent.observe(this) {
            (activity as AddAty).setTitle2(getString(com.blackview.common_res.R.string.add_device23))
            binding.layoutAddHotdotQrcode.isVisible = false
            binding.layoutInputNumber.isVisible = false
            isShowInputLayout = true
            val bean = Qrcode(ssid.replace("\"",""), pwd, it)
            val qrcode = Gson().toJson(bean)
            L.e(qrcode)
            binding.ivAddDeviceQrCode.setImageBitmap(ZxingUtils.createQRCode(qrcode))
            binding.layoutQrcodeImg.isVisible = true
            (activity as AddAty).member_token = it
        }
    }

    data class Qrcode(
        val s: String,
        val p: String,
        val t: String
    )
}