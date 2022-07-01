package com.blackview.module_device.wifitool.interfaces;

public interface IWifiLogListener {
    void onSuccess(String success);

    void onFail(String reason);
}
