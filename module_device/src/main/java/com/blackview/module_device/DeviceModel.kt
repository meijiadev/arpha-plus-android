package com.blackview.module_device

import android.util.ArrayMap
import androidx.lifecycle.MutableLiveData
import com.blackview.base.base.BaseViewModel
import com.blackview.base.base.SingleLiveEvent
import com.blackview.base.http.request
import com.blackview.base.http.requestNoCheck
import com.blackview.repository.entity.Dd
import com.blackview.repository.entity.Device
import com.blackview.repository.entity.Noti
import com.blackview.repository.httpService
import com.blackview.repository.repository.RepositoryFactory
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.RequestBody


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
class DeviceModel : BaseViewModel() {


    var liveDevices = MutableLiveData<List<Device>>()
    var changeDeviceNameEvent = SingleLiveEvent<Void>()
    var setNoticeEvent = SingleLiveEvent<Void>()
    var deleteDeviceEvent = SingleLiveEvent<Void>()


    fun devices() {
        request({ httpService.devices() }, {
            liveDevices.postValue(it.devices)
        })
    }

    fun changeDeviceName(id: Int, name: String) {
        val params = ArrayMap<Any, Any>()
        params["device_id"] = id
        params["device_name"] = name
        requestNoCheck({ httpService.changeDeviceName(params) }, {
            changeDeviceNameEvent.post()
        })
    }

    fun updateNotify(id: Int, door_bell: Boolean, door_open: Boolean, door_alert: Boolean) {
        val d = Noti(door_bell, door_open, door_alert)
        val bean = Dd(id, d)
        requestNoCheck({ httpService.updateNotify(bean) }, {
            setNoticeEvent.post()
        })
    }

    fun deleteDevice(id: Int, pwd: String) {
        val params = ArrayMap<Any, Any>()
        params["device_id"] = id
        params["password"] = pwd
        requestNoCheck({ httpService.deleteDevices(params) }, {
            deleteDeviceEvent.post()
        })
    }

}