package com.AiPN;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import mykj.stg.aipn.R;
import mykj.stg.aipn.lgUtil.LogUtil;

import static android.content.Context.MODE_PRIVATE;

public class AiPNDeviceModel {

    public String DID;
    public String subscribeKey;
    public int channel;

    public boolean isValidDID;

    public boolean isSubOK;//订阅是否成功

    public String spsInfo;
    public long getSPSInfoTime;
    public static final String AIPN_DEVICE_LIST_KEY = "AiPNDevListKey";
    public static final String AIPN_SPS_INFO_LIST_KEY = "AiPNSPSInfoListKey";

    public AiPNDeviceModel(String DID, String subscribeKey, int channel) {

        this.DID = DID;
        this.subscribeKey = subscribeKey;
        this.channel = channel;
        this.isValidDID = true;
    }

    public AiPNDeviceModel(JSONObject json) {

        this.DID = json.optString("DID");
        this.subscribeKey = json.optString("subscribeKey");
        this.channel = json.optInt("channel");
        this.isValidDID = json.optBoolean("isValidDID", true);
        this.isSubOK = json.optBoolean("isSubOK", false);
    }

    public JSONObject toJSON() throws JSONException {

        JSONObject json = new JSONObject();
        json.put("DID", DID);
        json.put("subscribeKey", subscribeKey);
        json.put("channel", channel);
        json.put("isSubOK", isSubOK);
        json.put("isValidDID", isValidDID);

        return json;
    }

    public String getSubState(Context ctx) {
        return (isSubOK ? ctx.getString(R.string.ActDevList_SubEd) : ctx.getString(R.string.ActDevList_SubNil));
    }

    public String identify() {

        return identify(DID, channel);
    }

    public static String identify(String DID, int channel) {

        return DID + ":" + channel;
    }

    public void updateSpsInfo(Context context, String info, long time) {

        this.spsInfo = info;
        this.getSPSInfoTime = time;
        HashMap<String, JSONObject> map = getLocalSPSInfo(context);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("spsInfo", info);
            jsonObject.put("getSPSInfoTime", time);
            map.put(DID, jsonObject);
            setLocalSPSInfo(context, map);
        } catch (JSONException e) {
            LogUtil.e("JSONException: " + e.getMessage());
        }

    }

    public void getSPSInfoFromLocal(Context context) {

        HashMap<String, JSONObject> map = getLocalSPSInfo(context);
        JSONObject jsonObject = map.get(DID);
        if (jsonObject != null) {
            spsInfo = jsonObject.optString("spsInfo", "");
            getSPSInfoTime = jsonObject.optLong("getSPSInfoTime", 0);
        }
    }

    private HashMap<String, JSONObject> getLocalSPSInfo(Context context) {

        SharedPreferences share = context.getSharedPreferences(AIPN_SPS_INFO_LIST_KEY, MODE_PRIVATE);
        String string = share.getString(AIPN_SPS_INFO_LIST_KEY, "");
        HashMap<String, JSONObject> map = new HashMap<>();
        try {
            JSONObject jsonObject = new JSONObject(string);
            Iterator<String> iterator = jsonObject.keys();
            while (iterator.hasNext()) {
                String next = iterator.next();
                map.put(next, jsonObject.optJSONObject(next));
            }
        } catch (JSONException e) {
            return map;
        }

        return map;
    }

    private void setLocalSPSInfo(Context context, HashMap<String, JSONObject> map) {

        SharedPreferences share = context.getSharedPreferences(AIPN_SPS_INFO_LIST_KEY, MODE_PRIVATE);
        JSONObject jsonObject = new JSONObject();
        try {
            for (String key : map.keySet()) {
                jsonObject.put(key, map.get(key));
            }
        } catch (JSONException e) {
            LogUtil.e("JSONException: " + e.getMessage());
        }
        share.edit().putString(AIPN_SPS_INFO_LIST_KEY, jsonObject.toString()).commit();
    }

    public static void updateLocalCameraInfo(Context context, List<AiPNDeviceModel> deviceModelList) {

        JSONArray jsonArray = new JSONArray();

        try {
            for (AiPNDeviceModel devModel : deviceModelList) {
                jsonArray.put(devModel.toJSON());
            }
        }
        catch (Exception e) {
            Log.d("AiPN", e.toString());
        }

        SharedPreferences share = context.getSharedPreferences("AiPNSTG", MODE_PRIVATE);
        share.edit().putString(AIPN_DEVICE_LIST_KEY, jsonArray.toString()).commit();
    }

    public static List<AiPNDeviceModel> loadLocalCameraModel(Context ctx) {

        SharedPreferences share = ctx.getSharedPreferences("AiPNSTG", MODE_PRIVATE);
        List<AiPNDeviceModel> deviceModels = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(share.getString(AIPN_DEVICE_LIST_KEY, "[]"));

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                deviceModels.add(new AiPNDeviceModel(obj));
            }
        }
        catch (Exception e) {
            Log.d("AiPN", e.toString());
        }

        return deviceModels;
    }
}
