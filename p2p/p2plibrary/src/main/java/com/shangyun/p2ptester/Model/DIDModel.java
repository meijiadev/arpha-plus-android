package com.shangyun.p2ptester.Model;


import com.shangyun.p2ptester.Util.LogUtil;

import org.json.JSONException;
import org.json.JSONObject;

public class DIDModel {

    public static final String DID_KEY = "did";
    public static final String LICENSE_KEY = "api_license";
    public static final String CRC_KEY_KEY = "crc_key";
    public static final String INIT_STRING_KEY = "init_string";
    public static final String WAKEUP_KEY_KEY = "wakeup_key";
    public static final String SERVER_IP1_KEY = "ip1";
    public static final String SERVER_IP2_KEY = "ip2";
    public static final String SERVER_IP3_KEY = "ip3";

    public String did;
    public String license;
    public String crcKey;
    public String initString;

    public String wakeupKey;
    public String ip1;
    public String ip2;
    public String ip3;

    public int repeat;
    public int delaySec;

    public int mode;
    public int threadNum;
    public int sizeOption;
    public int direction;

    public DIDModel(String did, String initString) {
        this.did = did;
        this.initString = initString;
    }
	

    public DIDModel(JSONObject json) {
        // 使用 getString，如果 key 不存在会抛出异常。
        this.did = json.optString(DID_KEY);
        this.license = json.optString(LICENSE_KEY);
        this.crcKey = json.optString(CRC_KEY_KEY);
        this.initString = json.optString(INIT_STRING_KEY);
        this.wakeupKey = json.optString(WAKEUP_KEY_KEY);
        this.ip1 = json.optString(SERVER_IP1_KEY);
        this.ip2 = json.optString(SERVER_IP2_KEY);
        this.ip3 = json.optString(SERVER_IP3_KEY);

    }

    public static boolean isSameModel(DIDModel model1, DIDModel model2) {

        if (!model1.did.equals(model2.did)) {
            return false;
        }

        if (!model1.license.equals(model2.license)) {
            return false;
        }

        if (!model1.crcKey.equals(model2.crcKey)) {
            return false;
        }

        if (!model1.initString.equals(model2.initString)) {
            return false;
        }

        if (!model1.wakeupKey.equals(model2.wakeupKey)) {
            return false;
        }

        if (!model1.ip1.equals(model2.ip1)) {
            return false;
        }

        if (!model1.ip2.equals(model2.ip2)) {
            return false;
        }

        return model1.ip3.equals(model2.ip3);
    }

    public JSONObject toJsonObject() throws JSONException {

        JSONObject json = new JSONObject();
        json.put(DID_KEY, did);
        json.put(LICENSE_KEY, license);
        json.put(CRC_KEY_KEY, crcKey);
        json.put(INIT_STRING_KEY, initString);

        json.put(WAKEUP_KEY_KEY, wakeupKey);
        json.put(SERVER_IP1_KEY, ip1);
        json.put(SERVER_IP2_KEY, ip2);
        json.put(SERVER_IP3_KEY, ip3);
        return json;
    }

    public String toString() {

        try {
            JSONObject json = toJsonObject();
            json.put("mode", mode);
            json.put("repeat", repeat);
            json.put("delaySec", delaySec);
            json.put("threadNum", threadNum);
            json.put("sizeOption", sizeOption);
            json.put("direction", direction);
            return json.toString(4);

        } catch (JSONException e) {
            LogUtil.e("JSONException: " + e.getMessage());
            return "";
        }
    }
}
