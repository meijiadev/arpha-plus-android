package com.AiPN;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

import com.cs2.sps.SPS_API;
import com.push.PushCenter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Arrays;

import androidx.annotation.NonNull;
import mykj.stg.aipn.lgUtil.LogUtil;

@SuppressLint({"StaticFieldLeak", "DefaultLocale"})
public class AiPNSDK {

    /***************************************************/

    private static final AiPNSDK g_AiPNSDK = new AiPNSDK();
    private static Context m_context = null;
    private boolean isInitSPSOK = false;

    public static String g_DeviceToken;
    public AiPNDeviceModel defaultModel;
    public PushCenter gPushCenter;
    /***************************************************/

    public static final String g_dName = "AI测试推送摄像机";
    public static final String g_APPName = "STG";
    public static final String g_spsAuth = "CS2_AiPN__SPSKEY";
    /***************************************************/

    public boolean isEnablePush = false; // 全局推送开关状态
    public String lastResultString = null; // 上次请求，返回的信息
    /***************************************************/

    public static AiPNSDK getInstance() { return g_AiPNSDK; }

    @SuppressLint("CommitPrefEdits")
    public void configAiPNSDK(Context context) {

        m_context = context;

        LogUtil.v("show phone message: " + Build.MANUFACTURER);
        //lgUtil.lgShowDevInFo(m_context);

        defaultModel = new AiPNDeviceModel("PPCS-014138-MPSMM", "cs2aipndemo", 10000);

        gPushCenter = PushCenter.getInstance();
        gPushCenter.configPushCenter(context);

        //3、打印 SPS 库的版本信息
        String spsVer = SPS_API.CS2_SPS_GetAPIVersion(null);
        LogUtil.d("SPS Version is: " + spsVer);
        LogUtil.d("AuthWord = " + g_spsAuth);

        initSPSSDK();
    }

    public int checkDeviceToken() {

        if (!gPushCenter.mAGName.equals("DPS")) gPushCenter.stopDPSService();

        g_DeviceToken = gPushCenter.getDeviceToken();

        LogUtil.d("checkDeviceToken(" + gPushCenter.mAGName + ") get: " + g_DeviceToken);

        return TextUtils.isEmpty(g_DeviceToken) ? -1 : 0;
    }

    /************************* DID *************************/

    public String checkDID(@NonNull String did) {

        LogUtil.d("check DID: " + did);
        if (TextUtils.isEmpty(did) || did.length() < 11 || did.length() > 20) {
            lastResultString = "DID length error!";
            LogUtil.d(lastResultString);
            return null;
        }

        String[] strings = did.split("-");
        String prefix;
        String serial;
        String chkCode;
        if (strings.length == 3) {
            prefix = strings[0];
            serial = strings[1];
            chkCode = strings[2];
        } else {
            did = did.replace("-", "");
            chkCode = did.substring(did.length() - 5);
            serial = did.substring(did.length() - 11, did.length() - 5);
            prefix = did.substring(0, did.length() - 11);
        }
        LogUtil.d("prefix: " + prefix + ", serial: " + serial + ", chkCode: " +chkCode);

        if (chkCode.length() != 5) {
            lastResultString = "Something wrong with DID!";
            LogUtil.d(lastResultString);
            return null;
        }

        try {
            Integer.valueOf(serial);
        } catch (NumberFormatException e) {
            lastResultString = "Something wrong with DID!";
            LogUtil.d(lastResultString);
            return null;
        }

        if (prefix.length() < 3 || prefix.length() > 7) {
            lastResultString = "Something wrong with DID!";
            LogUtil.d(lastResultString);
            return null;
        }

        return String.format("%s-%06d-%s", prefix, Integer.valueOf(serial), chkCode).toUpperCase();
    }

    /************************** AiPN SPS ***************************/
    public void initSPSSDK() {

        synchronized (this) {
            if (isInitSPSOK) {
                return ;
            }

            SPS_API.CS2_SPS_Initialize();
            LogUtil.d("CS2_SPS_Initialize succeed...");

            isInitSPSOK = true;
        }
    }

    public void deInitSPS() {

        synchronized (this) {

            if (isInitSPSOK) {
                SPS_API.CS2_SPS_DeInitialize();
                LogUtil.d("CS2_SPS_DeInitialize() done.");

                isInitSPSOK = false;
            }
        }
    }

    public int updateSPSInfo(AiPNDeviceModel model) {

        if (!model.isValidDID) {
            return -104;
        }
        model.getSPSInfoFromLocal(m_context);

        long time = System.currentTimeMillis()/1000;
        if (!TextUtils.isEmpty(model.spsInfo) && time - model.getSPSInfoTime < 3600) {
            LogUtil.d(model.DID + " Get spsInfo form local history!");
            return 0;
        }

        int gRet = getSPSInfo(model.DID);
        if (gRet == 0) {
            model.updateSpsInfo(m_context, lastResultString, time);
        } else if (gRet == -9 || gRet == -104) {
            model.isValidDID = false;
        }
        return gRet;
    }

    public int getSPSInfo(String DID) {

        byte[] PostResultBuf = new byte[1280];
        Arrays.fill(PostResultBuf, (byte)0);

        /* only need sps info. */
        int iRet = SPS_API.CS2_SPS_Upload(null, g_spsAuth, DID, null, 0, null,
                null, null, 0, PostResultBuf, PostResultBuf.length);
        if (iRet != SPS_API.ERROR_SPS_NoError) { // SPS API 只有返回 0 才是成功。
            LogUtil.d("GetServerInfo CS2_SPS_Upload failed return " + iRet + " " + getSPSErrorCode(iRet));
            lastResultString = getSPSErrorCode(iRet);
            return iRet;
        }

        String strRet = new String(Arrays.copyOf(PostResultBuf, getValidLength(PostResultBuf)));
        LogUtil.d("CS2_SPS_Upload GetServerInfo string: " + strRet);

        for (String tmpStr : strRet.split("&")) {
            if (tmpStr.contains("SPS=")) {
                lastResultString = tmpStr.split("=")[1];
                LogUtil.d(DID + " Get SPSInfo: " + lastResultString);
            }
        }
        return iRet;
    }

    public int checkPushState() {
        return sendSPSCommand("CheckPush", defaultModel);
    }

    public int enablePush(boolean bEnable) {
        return sendSPSCommand(bEnable ? "EnablePush" : "DisablePush", defaultModel);
    }

    public int checkSubscribe() {
        return sendSPSCommand("ChkSubscribe", defaultModel);
    }

    public int enableSubscribe(boolean isEnable, String DID, String subscribeKey, int channel) {

        if (TextUtils.isEmpty(DID)) return SPS_API.ERROR_SPS_InvalidDID;

        return sendSPSCommand(isEnable ? "Subscribe" : "UnSubscribe", new AiPNDeviceModel(DID, subscribeKey, channel));
    }

    public void unSubscribeAll() {
         sendSPSCommand("UnSubscribeAll", defaultModel);
    }

    @SuppressLint("DefaultLocale")
    private int sendSPSCommand(final String act, AiPNDeviceModel model) {

        String command = getCommand(act, model);
        if (command == null) {
            lastResultString = "DeviceToken is empty!";
            return -1;
        }

        int iRet = updateSPSInfo(model);
        if (iRet != SPS_API.ERROR_SPS_NoError) {
            return iRet;
        }

        byte[] PostResultBuf = new byte[1280];
        Arrays.fill(PostResultBuf, (byte)0);

        LogUtil.d(String.format("will do action %s with spsInfo %s for device %s", act, model.spsInfo, model.DID));
        iRet = SPS_API.CS2_SPS_Upload(model.spsInfo, g_spsAuth, model.DID, null, model.channel, command, null, null,
                0, PostResultBuf, PostResultBuf.length);
        if (iRet != SPS_API.ERROR_SPS_NoError) {
            LogUtil.d(String.format("CS2_SPS_Upload return %d [%s]", iRet, getSPSErrorCode(iRet)));
            lastResultString = getSPSErrorCode(iRet);
            return iRet;
        }
        String strRet = new String(PostResultBuf, 0, getValidLength(PostResultBuf));
        LogUtil.d("CS2_SPS_Upload PostResultBuf " + strRet);

        return commandResult(strRet, act, model);
    }

    private int commandResult(String strRet, String actString, AiPNDeviceModel model) {

        int iRet = -99;
        if (strRet == null) return iRet;

        try {
            JSONObject json = new JSONObject(strRet);
            iRet = json.has("RET") ? json.getInt("RET") : -99;
            if (iRet == 1) { // RET=1, 表示指令返回成功。
                iRet = 0;
                switch (actString) {
                    case "CheckPush":
                        isEnablePush = json.getInt("PushStatus") == 1;
                        break;
                    case "DisablePush":
                    case "EnablePush":
                        isEnablePush = actString.equals("EnablePush");
                        break;
                    case "ChkSubscribe":
                        JSONArray array = json.getJSONArray("Subs");
                        lastResultString = array.toString();
                        break;
                    case "Subscribe":
                        defaultModel = model;
                        model.isSubOK = true;
                        break;
                }
            } else {
                lastResultString = json.has("Desc") ? json.getString("Desc") : "";
            }
        } catch (Exception e) {
            LogUtil.d(e.toString());
        }

        return iRet;
    }

    private String getCommand(final String act, AiPNDeviceModel model) {

        if (g_DeviceToken == null) {
            lastResultString = "Device token is empty!";
            return null;
        }

        JSONObject jsonCMD = new JSONObject();

        switch (act) {
            case "Subscribe":
                try {
                    jsonCMD.put("DID", model.DID);
                    jsonCMD.put("CH", model.channel);
                    jsonCMD.put("SubKey", model.subscribeKey);
                    jsonCMD.put("DevName", g_dName);

                    String lang;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        lang = m_context.getResources().getConfiguration().getLocales().get(0).toString();
                    } else {
                        lang = m_context.getResources().getConfiguration().locale.toString();
                    }
                    jsonCMD.put("Lang", lang);

                } catch (Exception ignored) { }
                break;
            case "UnSubscribe":
                try {
                    jsonCMD.put("DID", model.DID);
                    jsonCMD.put("CH", model.channel);
                } catch (Exception ignored) { }
                break;
        }
        try {
            jsonCMD.put("ACT", act);
            jsonCMD.put("Token", g_DeviceToken);
            jsonCMD.put("AG", gPushCenter.mAGName);
            jsonCMD.put("APPName", g_APPName);
        } catch (Exception ignored) { }
        try {
            LogUtil.d("CMD: " + jsonCMD.toString(4));
        } catch (Exception ignored) { }

        return jsonCMD.toString();
    }

    private String getSPSErrorCode(int error) {

        switch (error) {
            case SPS_API.ERROR_SPS_NoError: return "API is successfully executed";
            case SPS_API.ERROR_SPS_InvalidParameter: return "Invalid Parameter";
            case SPS_API.ERROR_SPS_InvalidFunCode: return "Invalid FunCode";
            case SPS_API.ERROR_SPS_TimeOut: return "TimeOut (over 5 sec) waiting for Server's response";
            case SPS_API.ERROR_SPS_AuthWordError: return "AuthWord mismatch";
            case SPS_API.ERROR_SPS_FileNotExist: return "FileName doesn't exist";
            case SPS_API.ERROR_SPS_BufferNotEnough: return "Snapshot buffer size is too small";
            case SPS_API.ERROR_SPS_UserBreak: return "User break, ie CS2_SPS_Break() is called";
            case SPS_API.ERROR_SPS_NotEnoughMemory: return "The system memory is not enough for malloc";
            case SPS_API.ERROR_SPS_InvalidDID: return "Something wrong with 'DID'";
            case SPS_API.ERROR_SPS_ServerCloseSession: return "Server has closed tcp session, please look up Server Log for what happened.";
            case SPS_API.ERROR_SPS_LocalSocketTimeout: return "Local socket read timeout (5 sec)";
            case SPS_API.ERROR_SPS_UploadIsNotRunning: return "SPS_Upload (with Parameter SnapshotBuf = NULL) is not calling";
            case SPS_API.ERROR_SPS_ExceedMaxPINFOSize: return "The PINFO must be less than 4095 Bytes";
            case SPS_API.ERROR_SPS_ExceedMaxAINFOSize: return "The AINFO must be less than 4095 Bytes";
            case SPS_API.ERROR_SPS_ExceedMaxSnapshotSize: return "The Snapshot must be less than 32 MBytes";
            case SPS_API.ERRPR_SPS_UploadTemporaryDisabled: return "The SPS_Upload function is temporarily disabled, due to too many Upload in last 10 minutes";
            case SPS_API.ERROR_SPS_ServerError: return "Server Error, Please check log of all CS2 SPS Servers, or contact CS2 FAE.";
            case SPS_API.ERROR_SPS_UnknownError: return "Unknown Error, please contact CS2 FAE.";
            default:return "Error value " + error + " not found! Maybe it's a TCP errno!";
        }
    }

    // 获取实际 byte[] 数组数据长度
    private int getValidLength(byte[] bytes) {
        int i = 0;
        if (null == bytes || 0 == bytes.length)
            return i;
        for (; i < bytes.length; i++) {
            if ('\0' == bytes[i])
                break;
        }
        return i;
    }
}
