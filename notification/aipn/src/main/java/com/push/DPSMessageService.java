package com.push;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;

import com.dps.ppcs_api.DPS_API;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Base64;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import mykj.stg.aipn.NoticeTestActivity;
import mykj.stg.aipn.R;
import mykj.stg.aipn.lgUtil.LogUtil;

import static com.push.PushCenter.PUSH_CHANNEL_NAME;

public class DPSMessageService extends Service {

    private PowerManager.WakeLock wakeLock = null;
    private final int PID = android.os.Process.myPid();

    private DPSRecvThread mDPSRecvThread;
    public static boolean mIsStartAgain = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return null; }

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.v("onCreate()..");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) startForeground(PID, getNotification()); // 启动前台常驻通知栏
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.v("onStartCommand()..");
        acquireWakeLock();
        if (mDPSRecvThread == null) {
            mDPSRecvThread = new DPSRecvThread(this);
        }
        if (!mDPSRecvThread.isAlive()) {
            mDPSRecvThread.start();
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) startForeground(PID, getNotification()); // 启动前台常驻通知栏
        return START_STICKY;
    }

    @Override
    public void onDestroy() {

        LogUtil.v("onDestroy()...");
        if (mDPSRecvThread != null) {
            LogUtil.v("call DPS_DeInitialize().");
            mDPSRecvThread.stopRun = true;
            DPS_API.DPS_DeInitialize();
            mDPSRecvThread = null;
        }
        releaseWakeLock();

        if (mIsStartAgain) {
            LogUtil.e("Start DPS Message Service again!");
            Intent mIntent = new Intent();
            mIntent.setAction("com.push.DPSMessageService.START_SERVICE");
            mIntent.setPackage(getPackageName());
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                this.startForegroundService(mIntent);
            } else {
                this.startService(mIntent);
            }
        }

        super.onDestroy();
    }

    /**
     * Gets the power lock to keep the service still getting CPU when the screen is off, keep running
     */
    private void acquireWakeLock() {
        if (null == wakeLock) {
            PowerManager pm = (PowerManager) getSystemService(Context.POWER_SERVICE);
            wakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK
                    | PowerManager.ON_AFTER_RELEASE, getClass()
                    .getCanonicalName());
            if (null != wakeLock) {
                LogUtil.v("call acquireWakeLock");
                wakeLock.acquire(10*60*1000L /*10 minutes*/);
            }
        }
    }

    // Release device power lock
    private void releaseWakeLock() {
        if (null != wakeLock && wakeLock.isHeld()) {
            LogUtil.v("call releaseWakeLock");
            wakeLock.release();
            wakeLock = null;
        }
    }

    private Notification getNotification() {

        PackageManager pm = getApplication().getPackageManager();
        ApplicationInfo appInfo = getApplication().getApplicationInfo();

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, PUSH_CHANNEL_NAME);
        builder.setSmallIcon(appInfo.icon); //
        builder.setContentTitle(appInfo.loadLabel(pm).toString());
        builder.setContentText(getString(R.string.Service_Text));
        return builder.build();
    }

    public static void showNotification(Context context, String recvString, int id, int count) {

        LogUtil.v("showNotification...");
        JSONObject jsonObject;
        String title;
        String content;
        String payload;
        String url = null;
        try {
            jsonObject = new JSONObject(recvString);
            title = jsonObject.getString("title");
            content = jsonObject.getString("content");

            payload = jsonObject.getString("custom_content");
            //LogUtil.v("get payload: " + new String(Base64.getDecoder().decode(payload.getBytes())));
	        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
		        LogUtil.v("get payload: " + new String(Base64.getDecoder().decode(payload.getBytes())));
	        } else {
		        LogUtil.v("get payload: " + new String(android.util.Base64.decode(payload.getBytes(), android.util.Base64.DEFAULT)));
	        }
	        if (jsonObject.optJSONObject("SPS") != null) {
                JSONObject SPS = jsonObject.getJSONObject("SPS");
                String dlInfo = SPS.getString("DLInfo");
                String dlFileName = SPS.getString("FileName");
                String[] dlArray = dlInfo.split("P");
                url = String.format("http://%s/%s", dlArray[1], dlFileName);
                LogUtil.v("get SPSInfo:" + SPS + ", url: " + url);
            }
        } catch (JSONException e) {
            LogUtil.e(e.getMessage());
            return;
        }
		
        Intent intent = new Intent();
        intent.putExtra("userInfo", jsonObject.toString());
        intent.setClass(context, NoticeTestActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, count, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(PUSH_CHANNEL_NAME, PUSH_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PUSH_CHANNEL_NAME);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setContentTitle(title);//通知标题
        builder.setContentText(content);//通知简介
        builder.setAutoCancel(true);//点击后消失
        builder.setWhen(System.currentTimeMillis());//通知时间
        builder.setDefaults(Notification.DEFAULT_SOUND);
        builder.setOnlyAlertOnce(true);
        builder.setContentIntent(pendingIntent);

        Bitmap bit = getBitmap(url);
        if(bit != null){
            builder.setLargeIcon(bit);
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bit));
        }

        NotificationManager manager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(id);
        Notification notification = builder.build();
        if(notificationManager != null){ notificationManager.notify(id, notification); }
    }

    public static Bitmap getBitmap(String imageUri) {

        if (imageUri == null) return null;
        // 显示网络上的图片
        Bitmap bitmap;
        try {
            LogUtil.v("getBitmap start.");
            long time = System.currentTimeMillis();
            URL myFileUrl = new URL(imageUri);
            HttpURLConnection conn = (HttpURLConnection) myFileUrl.openConnection();
            conn.setDoInput(true);
            conn.setUseCaches(true);
            conn.connect();
            InputStream is = conn.getInputStream();
            bitmap = BitmapFactory.decodeStream(is);
            is.close();
            long stop = System.currentTimeMillis();
            LogUtil.v("getBitmap download finished, time:" + (stop-time) + "ms, url: " + imageUri);
        } catch (OutOfMemoryError | IOException e) {
            LogUtil.e("getBitmap download fail！" + e.getMessage());
            bitmap = null;
        }
        return bitmap;
    }

    public class DPSRecvThread extends Thread {

        private final Context mContext;
        private final String mDPS_IP = PushCenter.mDPS_IP;
        private static final String mDPS_AES128Key = PushCenter.mDPS_AES128Key;
        private static final int mDPS_Port = PushCenter.mDPS_Port;
        private String mDPS_Token = "";
        private CheckDPSAliveTime mCheckDPSAliveTime;

        public boolean stopRun;

        public DPSRecvThread(Context mContext) {
            this.mContext = mContext;
            stopRun = false;
            initializeDPS();
            getDPSToken();
        }

        @Override
        public void run() {
            super.run();

            if ("".equals(mDPS_Token)) {
                getDPSToken();
                if ("".equals(mDPS_Token)) {return;}
            }

            byte[] buf = new byte[1408];
            int[] size = new int[1];
            int timeout = 60 * 60 * 1000;  // one hour.
            while (!stopRun) {
                Arrays.fill(buf, (byte) 0);
                size[0] = 1440;

                setAlarmTimer();
                LogUtil.v("DPS_RecvNotify on token: " + mDPS_Token);
                int ret = DPS_API.DPS_RecvNotify(mDPS_Token, buf, size, timeout);
                LogUtil.v("DPS_RecvNotify done ret=" + ret);
                cancelAlarmTimer();

                if (ret < 0) {

                    if (ret == DPS_API.ERROR_DPS_NotInitialized
                            || ret == DPS_API.ERROR_DPS_FailedToRecvData
                            || ret == DPS_API.ERROR_DPS_FailedToConnectServer) {

                        DPS_API.DPS_DeInitialize();
                        initializeDPS();

                    } else if (ret == DPS_API.ERROR_DPS_InvalidToken) {
                        getDPSToken();
                    }
                    continue;
                }
                String recvString = new String(Arrays.copyOf(buf, size[0]));
                int notifyID = (int) System.currentTimeMillis();
                LogUtil.v("DPS_RecvNotify get message: " + recvString);

                new Thread(() -> showNotification(mContext, recvString, notifyID, 1)).start();
            }
            if (mCheckDPSAliveTime != null) {
                mCheckDPSAliveTime.stopCheck = true;
                mCheckDPSAliveTime.interrupt();
            }
        }

        private void cancelAlarmTimer(){
            if (mCheckDPSAliveTime != null) {
                mCheckDPSAliveTime.stopCheck = true;
                mCheckDPSAliveTime.interrupt();
                mCheckDPSAliveTime = null;
            }
        }

        public void setAlarmTimer() {
            if (mCheckDPSAliveTime == null) {
                mCheckDPSAliveTime = new CheckDPSAliveTime();
            }
            if (!mCheckDPSAliveTime.isAlive()) {
                mCheckDPSAliveTime.start();
            }
        }

        private void initializeDPS() {

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

        private void getDPSToken() {
            SharedPreferences share = getSharedPreferences(PushCenter.LOCAL_HISTORY_NAME, 0);
            mDPS_Token = share.getString(PushCenter.DPS_TOKEN_KEY, "");
            if ("".equals(mDPS_Token)) {
                byte[] buf = new byte[48];
                Arrays.fill(buf, (byte)0);
                long start = System.currentTimeMillis();
                int ret = DPS_API.DPS_TokenAcquire(buf, 48);
                long stop = System.currentTimeMillis();
                if (ret < 0) {
                    LogUtil.e(String.format("DPS_TokenAcquire fail(%d)%s, time=%dms", ret, getDPSErrorCodeInfo(ret), stop-start));
                    return;
                }
                mDPS_Token = new String(Arrays.copyOf(buf, 32));
                share.edit().putString(PushCenter.DPS_TOKEN_KEY, mDPS_Token);
                share.edit().commit();
                LogUtil.v(String.format("DPS_TokenAcquire Success! time=%dms, new token=%s", (stop-start), mDPS_Token));
            }
        }

        private class CheckDPSAliveTime extends Thread {

            public boolean stopCheck;

            @Override
            public void run() {
                super.run();
                stopCheck = false;
                int[] timeSec = new int[1];
                int item =0;
                LogUtil.d("start check DPS alive time thread! check alive time every 10 seconds.");

                while (!stopCheck) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        LogUtil.v("CheckDPSAliveTime sleep InterruptedException.");
                        break;
                    }
                    if (++item % 10 != 5) continue;
                    timeSec[0] = -1;
                    int ret = DPS_API.DPS_GetLastAliveTime(timeSec);
                    if (ret == DPS_API.ERROR_DPS_Successful) {
                        if (timeSec[0] > 40) {
                            LogUtil.e("DPS_GetLastAliveTime: " + timeSec[0] + " sec, call DPS_DeInitialize()");
                            DPS_API.DPS_DeInitialize();
                            break;
                        }
                    }
                    // LogUtil.d("DPS_GetLastAliveTime ==> " + timeSec[0] + " sec.");
                }
            }
        }

        public String getDPSErrorCodeInfo(int error) {

            if (error > 0) return "UnknownError " + error + ", May be a handle value or Data Size!";
            else {
                switch (error)
                {// DPS Error
                    case 0:   return "Successful";
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
                        return "Unknown value " + error + ", something is wrong!";
                }
            }
        }
    }
}
