package com.push;

import android.content.Context;
import android.content.SharedPreferences;
import com.vivo.push.model.UPSNotificationMessage;
import com.vivo.push.sdk.OpenClientPushMessageReceiver;
import mykj.stg.aipn.lgUtil.LogUtil;

public class VIVOPushMessageReceiver extends OpenClientPushMessageReceiver {

    @Override
    public void onNotificationMessageClicked(Context context, UPSNotificationMessage upsNotificationMessage) {
        LogUtil.v("onNotificationMessageClicked()...");
        LogUtil.v("Message: " + upsNotificationMessage);
    }

    @Override
    public void onReceiveRegId(Context context, String s) {

        LogUtil.v("onReceiveRegId(), s: " + s);
        SharedPreferences shared = context.getSharedPreferences(PushCenter.LOCAL_HISTORY_NAME, 0);
        SharedPreferences.Editor edit = shared.edit();
        edit.putString(PushCenter.VIVO_TOKEN_KEY, s);
        if (!edit.commit()) {
            LogUtil.v("Failed to commit setting changes.");
        }
    }
}
