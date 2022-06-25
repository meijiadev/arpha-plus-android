package com.push;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.text.TextUtils;

import com.dps.ppcs_api.DPS_API;
import com.heytap.msp.push.HeytapPushManager;
import com.heytap.msp.push.callback.ICallBackResultService;
import com.huawei.agconnect.config.AGConnectServicesConfig;
import com.huawei.hms.aaid.HmsInstanceId;
import com.huawei.hms.common.ApiException;
import com.vivo.push.IPushActionListener;
import com.vivo.push.PushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.util.Arrays;

import mykj.stg.aipn.lgUtil.LogUtil;

import static android.content.Context.NOTIFICATION_SERVICE;

@SuppressLint("DefaultLocale")
public class PushCenter {

    @SuppressLint("StaticFieldLeak")
    private static final PushCenter gPushCenter = new PushCenter();

    /***************************************************/
    // DPS 相关密钥
    public static String mDPS_IP = "120.79.96.144";
    public static final int mDPS_Port = 32750;
    public static final String mDPS_AES128Key = "CS2_AiPN_201812s";
    /***************************************************/
    // 小米开发者相关密钥
    private static final String MI_ID = "2882303761518339898";
    private static final String MI_KEY = "5991833922898";
    /***************************************************/
    // OPPO 开发者相关密钥
    private static final String OPPO_KEY = "b0c768a22b1149c5945f97ce1cd72e3f";
    private static final String OPPO_SECRET = "1b9e53360d064d9190c2cab623b51606";
    /***************************************************/
    Context mContext;
    public String mAGName;
    public static String mDevBrand;
    public String[] mAGList;
    /***************************************************/
    public static final String LOCAL_HISTORY_NAME = "PushCenter";
    public static final String PUSH_CHANNEL_NAME = "com.blackview.arphaplus";
    public static final String HMS_TOKEN_KEY = "HMS_Token";
    public static final String MI_TOKEN_KEY = "MI_Token";
    public static final String VIVO_TOKEN_KEY = "VIVO_Token";
    public static final String OPPO_TOKEN_KEY = "OPPO_Token";
    public static final String DPS_TOKEN_KEY = "DPS_Token";
    /***************************************************/
    SharedPreferences mShare;
    String mDpsToken;
    String mMiToken;
    String mHmsToken;
    String mVivoToken;
    String mOppoToken;
    /***************************************************/

    public static PushCenter getInstance() {return gPushCenter;}

    public void configPushCenter(Context context) {

        LogUtil.v("configPushCenter() start...");

        mContext = context;
        mShare = mContext.getSharedPreferences(LOCAL_HISTORY_NAME, 0);

        createNotificationChannel();

        String phone;
        switch (Build.MANUFACTURER) {
            case "honor":
            case "HUAWEI": phone = "Huawei"; break;
            case "Xiaomi": phone = "MI";     break;
            case "OPPO":   phone = "OPPO";   break;
            case "vivo":   phone = "VIVO";   break;
            default:       phone = "DPS";    break;
        }
        LogUtil.d("phone(" + Build.MANUFACTURER + "): " + phone);

        mAGName = mShare.getString("AG_Key", "DPS");

        if (phone.equals("DPS")) {
            mAGList = new String[]{mAGName};
        } else if ("DPS".equals(mAGName)) {
            mAGList = new String[]{mAGName, phone};
        } else {
            mAGList = new String[]{mAGName, "DPS"};
        }

        switch (phone) {
            case "Huawei": hmsSetting();break;
            case "MI":     miPushSetting();break;
            case "OPPO":   oppoSetting();break;
            case "VIVO":   vivoSetting();break;
            default: break;
        }
        dpsSetting();

        LogUtil.v("configPushCenter(AG=" + mAGName + ") done.");
    }

    public String getDeviceToken() {

        String tk = getDeviceToken(mAGName);
        if (TextUtils.isEmpty(tk)) {
            mAGName = "DPS";
            return getDeviceToken("DPS");
        }
        return tk;
    }

    String getDeviceToken(String agName) {

        String key = mShare.getString("AG_Key", "");
        if (TextUtils.isEmpty(key) || !agName.equals(key)) {
            SharedPreferences.Editor edit = mShare.edit();
            edit.putString("AG_Key", mAGName);
            if (!edit.commit()) {
                LogUtil.v("Failed to commit setting changes.");
            }
        }

        LogUtil.v("get " + agName + " token.");
        switch (agName) {
            case "MI":      return getMIToken();
            case "Huawei":  return getHmsToken();
            case "OPPO":    return getOppoToken();
            case "VIVO":    return getVivoToken();
            default: dpsSetting();return mDpsToken;
        }
    }

    void createNotificationChannel() {

        NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_NAME, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                ////配置通知渠道的属性
                channel.setDescription("消息通知");//用户可以看到的通知渠道的描述
                ////设置通知出现时的震动（如果 android 设备支持的话）
                channel.enableVibration(true);
                //最后在 notificationManager 中创建该通知渠道
                notificationManager.createNotificationChannel(channel);
            }
        }
    }

    /************************* OPPO Push ************************/
    void oppoSetting() {

        LogUtil.v("start oppoSetting()...");
        HeytapPushManager.init(mContext, false);

        if (HeytapPushManager.isSupportPush()) {
            HeytapPushManager.register(mContext, OPPO_KEY, OPPO_SECRET, new ICallBackResultService() {
                @Override
                public void onRegister(int i, String reg) {
                    LogUtil.v("oppo onRegister: " + reg);
                    if (i == 0) {
                        mOppoToken = reg;
                        SharedPreferences.Editor edit = mShare.edit();
                        edit.putString(OPPO_TOKEN_KEY, mOppoToken);
                        if (!edit.commit()) {
                            LogUtil.v("Failed to commit setting changes.");
                        }
                    }
                }

                @Override public void onUnRegister(int i) { }
                @Override public void onSetPushTime(int i, String s) { }
                @Override public void onGetPushStatus(int i, int i1) {}
                @Override public void onGetNotificationStatus(int i, int i1) {}
            });

        } else {
            LogUtil.e("no support oppo push!!");
        }
    }

    String getOppoToken() {

        LogUtil.d("getOppoToken.");
        mOppoToken = mShare.getString(OPPO_TOKEN_KEY, "");

        int i = 0;
        while (i++ < 10 && TextUtils.isEmpty(mOppoToken)) {
            mOppoToken = HeytapPushManager.getRegisterID();
            if (!TextUtils.isEmpty(mOppoToken)) {
                LogUtil.e("i = " + i);
                SharedPreferences.Editor edit = mShare.edit();
                edit.putString(OPPO_TOKEN_KEY, mOppoToken);
                if (!edit.commit()) {
                    LogUtil.v("Failed to commit setting changes.");
                }
                break;
            }
            try { Thread.sleep(100); } catch (InterruptedException ignored) { }
        }

        return mOppoToken;
    }

    /************************* VIVO Push ************************/
    @SuppressWarnings("Convert2Lambda")
    void vivoSetting() {

        LogUtil.v("start vivoSetting()...");
        PushClient.getInstance(mContext).initialize();
        PushClient.getInstance(mContext).turnOnPush(new IPushActionListener() {
            @Override
            public void onStateChanged(int i) {
                if (i != 0) {
                    LogUtil.e("VIVO turnOnPush fail i=" + i);
                } else {
                    LogUtil.v("VIVO turnOnPush success!");
                }
            }
        });

        mVivoToken = mShare.getString(VIVO_TOKEN_KEY, "");
    }

    String getVivoToken() {

        LogUtil.d("getVivoToken.");
        mVivoToken = mShare.getString(VIVO_TOKEN_KEY, "");

        int i = 0;
        while (i++ < 10 && TextUtils.isEmpty(mVivoToken)) {
            mVivoToken = PushClient.getInstance(mContext).getRegId();
            if (!TextUtils.isEmpty(mVivoToken)) {
                LogUtil.e("i = " + i);
                SharedPreferences.Editor edit = mShare.edit();
                edit.putString(VIVO_TOKEN_KEY, mVivoToken);
                if (!edit.commit()) {
                    LogUtil.v("Failed to commit setting changes.");
                }
                break;
            }
            try { Thread.sleep(100); } catch (InterruptedException ignored) { }
        }

        return mVivoToken;
    }

    /************************* HMS Push *************************/
    void hmsSetting() {

        LogUtil.v("start hmsSetting()...");
        new Thread(() -> {
            String appId = AGConnectServicesConfig.fromContext(mContext).getString("client/app_id");
            try {
                mHmsToken = HmsInstanceId.getInstance(mContext).getToken(appId, "HMS");
                if (!TextUtils.isEmpty(mHmsToken)) {
                    SharedPreferences.Editor edit = mShare.edit();
                    edit.putString(HMS_TOKEN_KEY, mHmsToken);
                    if (!edit.commit()) {
                        LogUtil.e("Failed to commit setting changes.");
                    }
                    LogUtil.v("Get HMS token: " + mHmsToken);
                }
            } catch (ApiException e) {
                LogUtil.e("get hms token fail, " + e.getMessage() + "appId: " + appId);
            }
        }).start();
    }

    String getHmsToken() {

        LogUtil.d("getHmsToken.");
        mHmsToken = mShare.getString(HMS_TOKEN_KEY, "");

        int i = 0;
        while (i++ < 10 && TextUtils.isEmpty(mHmsToken)) {
            mHmsToken = mShare.getString(HMS_TOKEN_KEY, "");
            if (!TextUtils.isEmpty(mHmsToken)) break;
            try { Thread.sleep(100); } catch (InterruptedException ignored) { }
        }
        return mHmsToken;
    }

    /************************* MI Push *************************/
    void miPushSetting() {

        LogUtil.v("start miPushSetting()...");
        MiPushClient.registerPush(mContext, MI_ID, MI_KEY);
    }

    String getMIToken() {

        LogUtil.d("getMIToken.");
        mMiToken = mShare.getString(MI_TOKEN_KEY, "");
        int i = 0;
        while (i++ < 10 && TextUtils.isEmpty(mMiToken)) {
            mMiToken = MiPushClient.getRegId(mContext);
            if (!TextUtils.isEmpty(mMiToken)) {
                LogUtil.e("i = " + i);
                SharedPreferences.Editor edit = mShare.edit();
                edit.putString(MI_TOKEN_KEY, mMiToken);
                if (!edit.commit()) {
                    LogUtil.v("Failed to commit setting changes.");
                }
                break;
            }
            try { Thread.sleep(100); } catch (InterruptedException ignored) { }
        }
        return mMiToken;
    }

    /************************** DPS ***************************/
    void dpsSetting() {

        LogUtil.v("start dpsSetting()...");
        /* 初始化 DPS */
        initializeDPS();
        getDPSToken();

        //启动DPS服务
        if (mAGName.equals("DPS")) startDPSService();

        String dpsVer = DPS_API.DPS_GetAPIVersion(null);
        LogUtil.v("DPS Version is: " + dpsVer);
        LogUtil.v("DPS IP:Port = " + mDPS_IP + ":" + mDPS_Port + ", DPS_AES128Key = " + mDPS_AES128Key);
    }

    void startDPSService() {

        LogUtil.v("Start DPS Service now!");
        Intent mIntent = new Intent(mContext, DPSMessageService.class);
        mIntent.setAction(DPSMessageService.class.getName());
        mIntent.setPackage(mContext.getPackageName());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mContext.startForegroundService(mIntent);
        } else {
            mContext.startService(mIntent);
        }
    }

    public void stopDPSService() {

        LogUtil.v("Stop DPS Service now!");
        DPSMessageService.mIsStartAgain = false;
        Intent mIntent = new Intent(mContext, DPSMessageService.class);
        mIntent.setAction(DPSMessageService.class.getName());
        mIntent.setPackage(mContext.getPackageName());
        mContext.stopService(mIntent);

    }

    void initializeDPS() {

        long start = System.currentTimeMillis();
        int ret = DPS_API.DPS_Initialize(mDPS_IP, mDPS_Port, mDPS_AES128Key, 0);
        long stop = System.currentTimeMillis();
        if (ret == DPS_API.ERROR_DPS_AlreadyInitialized) {
            LogUtil.v("DPS_Initialize already done.");
        } else if (ret < 0) {
            LogUtil.e(String.format("DPS_Initialize fail(%d)%s, time=%dms", ret, getDPSErrorCodeInfo(ret), stop-start));
        } else {
            LogUtil.v(String.format("DPS_Initialize Success! time=%dms", (stop - start)));
        }
    }

    void getDPSToken() {

        SharedPreferences.Editor edit = mShare.edit();
        mDpsToken = mShare.getString(DPS_TOKEN_KEY, "");
        if ("".equals(mDpsToken)) {
            byte[] buf = new byte[48];
            Arrays.fill(buf, (byte)0);
            long start = System.currentTimeMillis();
            int ret = DPS_API.DPS_TokenAcquire(buf, 48);
            long stop = System.currentTimeMillis();
            if (ret < 0) {
                LogUtil.e(String.format("DPS_TokenAcquire fail(%d)%s, time=%dms", ret, getDPSErrorCodeInfo(ret), stop-start));
                return ;
            }
            mDpsToken = new String(Arrays.copyOf(buf, 32));
            edit.putString(DPS_TOKEN_KEY, mDpsToken);
            if (!edit.commit()) {
                LogUtil.v("Failed to commit setting changes.");
            }

            LogUtil.v(String.format("DPS_TokenAcquire Success! time=%dms, new token=%s", (stop-start), mDpsToken));
        }
    }

    String getDPSErrorCodeInfo(int error) {

        if (error > 0) return "UnknownError, May be a handle value or Data Size!";
        else {
            switch (error)
            {// DPS Error
                case 0:   return "NDT_ERROR_NoError";
                case -1:  return "ERROR_DPS_NotInitialized";
                case -2:  return "ERROR_DPS_AlreadyInitialized";
                case -3:  return "ERROR_DPS_TimeOut";
                case -4:  return "ERROR_DPS_FailedToResolveHostName";
                case -5:  return "ERROR_DPS_FailedToCreateSocket";
                case -6:  return "ERROR_DPS_FailedToBindPort";
                case -7:  return "ERROR_DPS_FailedToConnectServer";
                case -8:  return "ERROR_DPS_FailedToRecvData";
                case -9:  return "ERROR_DPS_NotEnoughBufferSize";
                case -10: return "ERROR_DPS_InvalidAES128Key";
                case -11: return "ERROR_DPS_InvalidToken";
                case -12: return "ERROR_DPS_OnRecvNotify";
                case -13: return "ERROR_DPS_OnAcquireToken";
                case -14: return "ERROR_DPS_NotOnRecvNotify";
                default:
                    return "Unknown, something is wrong!";
            }
        }
    }
}
