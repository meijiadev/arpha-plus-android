package com.push;

import android.content.SharedPreferences;
import android.text.TextUtils;
import com.huawei.hms.push.HmsMessageService;
import com.huawei.hms.push.RemoteMessage;
import mykj.stg.aipn.lgUtil.LogUtil;

import java.util.Arrays;

public class HMSMessageService extends HmsMessageService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        LogUtil.v("onMessageReceived: " + remoteMessage.getData());

        LogUtil.v("getCollapseKey: " + remoteMessage.getCollapseKey()
                + "\n getData: " + remoteMessage.getData()
                + "\n getFrom: " + remoteMessage.getFrom()
                + "\n getTo: " + remoteMessage.getTo()
                + "\n getMessageId: " + remoteMessage.getMessageId()
                + "\n getSendTime: " + remoteMessage.getSentTime()
                + "\n getDataMap: " + remoteMessage.getDataOfMap()
                + "\n getMessageType: " + remoteMessage.getMessageType()
                + "\n getTtl: " + remoteMessage.getTtl()
                + "\n getToken: " + remoteMessage.getToken());

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        if (notification != null) {
            LogUtil.v("\n getImageUrl: " + notification.getImageUrl()
                    + "\n getTitle: " + notification.getTitle()
                    + "\n getTitleLocalizationKey: " + notification.getTitleLocalizationKey()
                    + "\n getTitleLocalizationArgs: " + Arrays.toString(notification.getTitleLocalizationArgs())
                    + "\n getBody: " + notification.getBody()
                    + "\n getBodyLocalizationKey: " + notification.getBodyLocalizationKey()
                    + "\n getBodyLocalizationArgs: " + Arrays.toString(notification.getBodyLocalizationArgs())
                    + "\n getIcon: " + notification.getIcon()
                    + "\n getSound: " + notification.getSound()
                    + "\n getTag: " + notification.getTag()
                    + "\n getColor: " + notification.getColor()
                    + "\n getClickAction: " + notification.getClickAction()
                    + "\n getChannelId: " + notification.getChannelId()
                    + "\n getLink: " + notification.getLink()
                    + "\n getNotifyId: " + notification.getNotifyId());
        }
    }

    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        LogUtil.v("onNewToken: " + s);

        if (!TextUtils.isEmpty(s)) {
            SharedPreferences share = getSharedPreferences(PushCenter.LOCAL_HISTORY_NAME, 0);
            String key = PushCenter.HMS_TOKEN_KEY;
            String localToken = share.getString(key, "");

            if (TextUtils.isEmpty(localToken)) {
                SharedPreferences.Editor edit = share.edit();
                edit.putString(key, s);
                if (!edit.commit()) {
                    LogUtil.e("Failed to commit setting changes.");
                }
                LogUtil.v("onNewToken save token to local: " + s);
            } else if (!s.equals(localToken)) {
                LogUtil.e("HMS Token has changed!\n" + localToken + " to \n" + s);
                SharedPreferences.Editor edit = share.edit();
                edit.putString(key, s);
                if (!edit.commit()) {
                    LogUtil.e("Failed to commit setting changes.");
                }
            }
        }
    }

    @Override
    public void onTokenError(Exception e) {
        super.onTokenError(e);
        LogUtil.e("onTokenError: " + e.getMessage());
    }
}
