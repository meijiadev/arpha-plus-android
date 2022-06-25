package com.push;

import android.content.Context;

import com.xiaomi.mipush.sdk.*;
import mykj.stg.aipn.lgUtil.LogUtil;

public class MIPushMessageReceiver extends PushMessageReceiver {

    /* 透传消息 */
    @Override
    public void onReceivePassThroughMessage(Context context, MiPushMessage message) {
        LogUtil.v("onReceivePassThroughMessage message: " + message);

        LogUtil.v("content: " + message.getContent() + ", payload: " + message.getExtra().get("payload"));
    }

    /* 点击了 通知栏消息 */
    @Override
    public void onNotificationMessageClicked(Context context, MiPushMessage message) {
        LogUtil.v("onNotificationMessageClicked message: " + message);

        LogUtil.v("content: " + message.getContent() + ", payload: " + message.getExtra().get("payload"));
    }

    /* 通知栏消息 */
    @Override
    public void onNotificationMessageArrived(Context context, MiPushMessage message) {
        LogUtil.v("onNotificationMessageArrived message: " + message);

        LogUtil.v("content: " + message.getContent() + ", payload: " + message.getExtra().get("payload"));
    }
}
