package com.blackview.module_device

import android.text.TextUtils
import com.blackview.base.App
import com.blackview.contant.ServerIp1
import com.blackview.contant.ServerIp2
import com.blackview.contant.ServerIp3
import com.blackview.contant.WakeupKey
import com.blackview.util.L
import com.p2p.P2PSDK
import com.p2p.pppp_api.PPCS_APIs
import com.shangyun.p2ptester.Model.DIDModel
import com.shangyun.p2ptester.Model.SessionModel
import com.shangyun.p2ptester.Model.WakeupQuery
import com.shangyun.p2ptester.Util.LogUtil
import com.shangyun.p2ptester.Util.P2PUtil
import org.json.JSONException
import org.json.JSONObject
import java.util.*

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
 * Created by home on 2022/7/15.
 */
class DeviceController {
    var isTesterRun = false
    var p2pSDK: P2PSDK = P2PSDK.getInstance()
    var mSession = 0
    private var mWakeupQuery = WakeupQuery()
    var isReadWriteDate = false

    init {
        p2pSDK.configP2PSDK(App.instance)
    }

    fun startModel(model: DIDModel) {
        Thread(Runnable {
            isTesterRun = true
            p2pSDK.mIsConnectionTesterRun = true
            var useByServer = P2PSDK.AVAILABLE_0x7X_Timeout
            var success_time: Long = 0
            var serverString = model.initString
            val mRepeat = if (model.mode == 4) if (P2PSDK.AVAILABLE_CHECK_0x79) 2 else 1 else model.repeat
            val bEnableLanSearch: Byte = when (model.mode) {
                0 -> 0x00
                1 -> 0x01
                2 -> 0x1E
                3 -> 0x1F
                4 -> 0x7F
                5 -> if (P2PSDK.AVAILABLE_TCP_RELAY) 0x7A.toByte() else 0x7E
                6 -> 0x5E
                7 -> if (P2PSDK.AVAILABLE_TCP_RELAY) 0x7B.toByte() else 0x7E
                8 -> if (P2PSDK.AVAILABLE_TCP_RELAY) 0x7C.toByte() else 0x5E
                else -> -99
            }
            if (useByServer && bEnableLanSearch.toInt() != 0x7F) {
                serverString = if (bEnableLanSearch > 0x79) getServerString(model.initString, 5) else model.initString
            }
            val ips: MutableList<String> = ArrayList()
            if (!TextUtils.isEmpty(model.wakeupKey)) {
                if (!TextUtils.isEmpty(model.ip1)) {
                    ips.add(model.ip1)
                }
                if (!TextUtils.isEmpty(model.ip2)) {
                    ips.add(model.ip2)
                }
                if (!TextUtils.isEmpty(model.ip3)) {
                    ips.add(model.ip3)
                }
            }
            p2pSDK.updateDIDModel(model)
            var gRet: Int = p2pSDK.initializeP2P(model.initString)
            if (gRet == PPCS_APIs.ERROR_PPCS_ALREADY_INITIALIZED) {
                useByServer = true
            } else if (gRet != PPCS_APIs.ERROR_PPCS_SUCCESSFUL) {
                isTesterRun = false
                p2pSDK.mIsConnectionTesterRun = false
                return@Runnable
            }
            LogUtil.d("start p2p network...")
            var logStr = ""
            var wakeupStr = ""
            var connect_time = 0
            while (isTesterRun && connect_time < mRepeat) {
                connect_time += 1
                if (!TextUtils.isEmpty(model.wakeupKey)) {
                    val DID = model.did.split(":").toTypedArray()[0]
                    LogUtil.d(String.format("%02d-Wakeup_Query(DID=%s)...\n", connect_time, DID))
                    val lastLogin: Int = mWakeupQuery.query(DID, model.wakeupKey, ips)
                    if (lastLogin !in 0..20) {
                        L.e("device 离线")
                        break
                    }
                    L.e("lastLogin:$lastLogin")
                    val lastLogins = IntArray(3)
                    Arrays.fill(lastLogins, -99)
                    for (i in ips.indices) {
                        lastLogins[i] = java.lang.String.valueOf(mWakeupQuery.lastLoginMap.get(ips[i])).toInt()
                    }
                    wakeupStr = if (lastLogin < 0 && lastLogin != WakeupQuery.ERROR_Wakeup_NoLogin && lastLogin != WakeupQuery.ERROR_Wakeup_UnKnown) {
                        "WakeUp_Query failed: %d, $lastLogin"
                    } else if (lastLogin == WakeupQuery.ERROR_Wakeup_UnKnown) {
                        "LastSleepLogin=(NoRespFromServer), "
                    } else if (lastLogin == WakeupQuery.ERROR_Wakeup_NoLogin) {
                        "LastSleepLogin=(NoSleepLogin), "
                    } else {
                        String.format("LastSleepLogin=%d(%d, %d, %d), ", lastLogin, lastLogins[0], lastLogins[1], lastLogins[2])
                    }
                }
                if (!isTesterRun) {
                    L.e(String.format("[%s] %s\nThe test was interrupted!\n", P2PUtil.getTimeString(), wakeupStr))
                    connect_time -= 1
                    break
                }
                logStr = String.format(
                    "PPCS_Connect%s(%s, 0x%02X, 0%s%s)...\n", if (useByServer) "ByServer" else "", model.did,
                    bEnableLanSearch, if (useByServer) ", " else "", if (useByServer) serverString else ""
                )
                LogUtil.d(String.format("%02d-%s", connect_time, logStr))
                mSession = if (useByServer) {
                    PPCS_APIs.PPCS_ConnectByServer(model.did, bEnableLanSearch, 0, serverString)
                } else {
                    PPCS_APIs.PPCS_Connect(model.did, bEnableLanSearch, 0)
                }
                if (mSession < 0) {
                    //穿透失败
                    //P2PUtil.mSleep(300)
                    continue
                }
                success_time++
            }
            logStr = String.format("%sTotal Connection times: %d, Success: %d", logStr, connect_time, success_time)
            L.e(logStr)
            P2PUtil.mSleep(300)
            p2pSDK.mIsConnectionTesterRun = false
            gRet = p2pSDK.deInitializeP2P()
            if (gRet == PPCS_APIs.ERROR_PPCS_SUCCESSFUL) {
                L.e(p2pSDK.mLastResultString)
            }
            isTesterRun = false
        }).start()
    }

    fun stopModel() {
        LogUtil.d("stopTester...")
        mWakeupQuery.stopQuery = true
        isTesterRun = false
        PPCS_APIs.PPCS_Connect_Break()
        if (isReadWriteDate) {
            PPCS_APIs.PPCS_ForceClose(mSession)
        }
    }

    private fun getServerString(initString: String?, timeout: Int): String? {
        var cmd = initString
        if (P2PSDK.AVAILABLE_0x7X_Timeout) {
            val jsonObject = JSONObject()
            return try {
                jsonObject.put("InitString", initString)
                jsonObject.put("0x7X_Timeout", timeout)
                cmd = jsonObject.toString().trim { it <= ' ' }
                cmd
            } catch (e: JSONException) {
                LogUtil.e("getInitString error: " + e.message)
                ""
            }
        }
        return cmd
    }
}