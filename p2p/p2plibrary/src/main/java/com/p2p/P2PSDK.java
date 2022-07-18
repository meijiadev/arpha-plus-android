package com.p2p;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.p2p.pppp_api.PPCS_APIs;
import com.p2p.pppp_api.st_PPCS_NetInfo;
import com.p2p.pppp_api.st_PPCS_Session;
import com.shangyun.p2ptester.Model.DIDModel;
import com.shangyun.p2ptester.Model.SessionModel;
import com.shangyun.p2ptester.Util.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

@SuppressLint("DefaultLocale")
public class P2PSDK {

	Context mContext;

	public String mInitString;
	public String mLastResultString;

	public static boolean mIsP2PAPIInit;
	public boolean mIsListenTesterRun;
	public boolean mIsConnectionTesterRun;
	public boolean mIsReadWriteTesterRun;

	private final String DID_INFO_NAME = "localDIDHistoryList";

	/************************* P2P *************************/

	static final double P2P_API_VERSION = ((PPCS_APIs.ms_verAPI >> 24) & 0xff) +
			((PPCS_APIs.ms_verAPI >> 16) & 0xff) * 0.1 +
			((PPCS_APIs.ms_verAPI >> 8) & 0xff) * 0.01;
	public static final boolean AVAILABLE_INFORMATION = P2P_API_VERSION >= 3.50;
	public static final boolean AVAILABLE_TCP_RELAY = P2P_API_VERSION >= 4.10;
	public static final boolean AVAILABLE_SET_INIT = P2P_API_VERSION >= 4.20;
	public static final boolean AVAILABLE_CHECK_0x79 = P2P_API_VERSION >= 4.30;
	public static final boolean AVAILABLE_0x7X_Timeout = P2P_API_VERSION >= 4.53;

	static final int MAX_SESSION = 128;
	static final int SESSION_ALIVE_SEC = 6;

	@SuppressLint("StaticFieldLeak")
	private static final P2PSDK gP2PSDK = new P2PSDK();

	public static P2PSDK getInstance() {
		return gP2PSDK;
	}

	public void configP2PSDK(Context context) {
		mContext = context;
	}

	public String getAPIInformation() {

		if (AVAILABLE_INFORMATION) {
			String info = PPCS_APIs.PPCS_GetAPIInformation();
			return String.format("PPCS API Information(%d byte): %s\n", info.length(), info);
		} else {
			return String.format("PPCS API Version(%d): %d.%d.%d.%d\n", PPCS_APIs.ms_verAPI,
					(PPCS_APIs.ms_verAPI >> 24) & 0xff, (PPCS_APIs.ms_verAPI >> 16) & 0xff,
					(PPCS_APIs.ms_verAPI >> 8) & 0xff, PPCS_APIs.ms_verAPI & 0xff);
		}
	}

	public int initializeP2P(@NonNull String initString) {

		synchronized (this) {

			if (mIsP2PAPIInit) {
				if (initString.equals(mInitString)) {
					mLastResultString = "PPCS_Initialize() already done!\n";
					return PPCS_APIs.ERROR_PPCS_SUCCESSFUL;
				} else {
					mLastResultString = "Already init with Different InitString.\n";
					return PPCS_APIs.ERROR_PPCS_ALREADY_INITIALIZED;
				}
			}

			String initStr = getP2PInitString(initString);

			long start = System.currentTimeMillis();
			int gRet = PPCS_APIs.PPCS_Initialize(initStr.getBytes());
			long stop = System.currentTimeMillis();

			if (gRet == PPCS_APIs.ERROR_PPCS_SUCCESSFUL) {
				mLastResultString = String.format("PPCS_Initialize(%s) done! time: %d ms\n", initStr, stop - start);
				mInitString = initString;
				mIsP2PAPIInit = true;
			} else if (gRet != PPCS_APIs.ERROR_PPCS_ALREADY_INITIALIZED) {
				mLastResultString = String.format("PPCS_Initialize(%s) failed time:%d ms ret=%d[%s]\n", initStr, stop - start,
						gRet, getP2PErrorMessage(gRet));
			}

			return gRet;
		}
	}

	public int deInitializeP2P() {

		synchronized (this) {
			int gRet = -99;
			if (mIsP2PAPIInit && !mIsListenTesterRun && !mIsConnectionTesterRun && !mIsReadWriteTesterRun) {
				long start = System.currentTimeMillis();
				gRet = PPCS_APIs.PPCS_DeInitialize();
				long stop = System.currentTimeMillis();

				if (gRet == PPCS_APIs.ERROR_PPCS_SUCCESSFUL) {
					mIsP2PAPIInit = false;
					mLastResultString = "PPCS_DeInitialize() done! time=" + (stop - start) + "ms.";
				} else {
					mLastResultString = String.format("PPCS_DeInitialize() failed time: %.3f ms ret=%d[%s]", (stop - start) / 1000.0, gRet, getP2PErrorMessage(gRet));
				}
				LogUtil.d(mLastResultString);
			}
			return gRet;
		}
	}

	public void networkDetect(@Nullable String initString) {
		int gRet;
		st_PPCS_NetInfo netInfo = new st_PPCS_NetInfo();
		long startTime = System.currentTimeMillis();
		if (initString == null) {
			gRet = PPCS_APIs.PPCS_NetworkDetect(netInfo, 0);
		} else {
			gRet = PPCS_APIs.PPCS_NetworkDetectByServer(netInfo, 0, initString.trim());
		}
		long stopTime = System.currentTimeMillis();

		if (gRet != PPCS_APIs.ERROR_PPCS_SUCCESSFUL) {
			mLastResultString = String.format("PPCS_NetworkDetect%s() failed! ret=%d[%s]\n", initString == null ? "" : "ByServer",
					gRet, getP2PErrorMessage(gRet));
		} else {
			mLastResultString = String.format("PPCS_NetworkDetect%s() done! time: %d ms\n%s", initString == null ? "" : "ByServer"
					, stopTime - startTime, getNetworkInfo(netInfo));
		}
	}

	public SessionModel checkSession(int session) {

		st_PPCS_Session sInfo = new st_PPCS_Session();
		long start = System.currentTimeMillis();
		int gRet = PPCS_APIs.PPCS_Check(session, sInfo);
		long stop = System.currentTimeMillis();
		if (gRet != PPCS_APIs.ERROR_PPCS_SUCCESSFUL) {
			mLastResultString = String.format("PPCS_Check() fail, time=%d ms, ret=%d[%s]\n", stop - start, gRet, getP2PErrorMessage(gRet));
			LogUtil.d(mLastResultString);
			return null;
		}
		return new SessionModel(session, sInfo);
	}

	String getP2PInitString(String initString) {

		if (AVAILABLE_SET_INIT) {
			JSONObject jsonObject = new JSONObject();
			try {
				jsonObject.put("InitString", initString);
				jsonObject.put("SessAliveSec", SESSION_ALIVE_SEC);
				jsonObject.put("MaxNumSess", MAX_SESSION);
				return jsonObject.toString().trim();
			} catch (JSONException e) {
				LogUtil.e("getInitString error: " + e.getMessage());
				return initString.trim();
			}
		}
		return initString.trim();
	}

	String getNetworkInfo(st_PPCS_NetInfo netInfo) {

		StringBuilder buffer = new StringBuilder();
		buffer.append("------------ NetInfo: ------------\n");
		buffer.append("Internet Reachable        : ").append((netInfo.getbFlagInternet() == 1) ? "YES" : "NO").append("\n");
		buffer.append("P2P Server IP resolved    : ").append((netInfo.getbFlagHostResolved() == 1) ? "YES" : "NO").append("\n");
		buffer.append("P2P Server Hello Ack      : ").append((netInfo.getbFlagServerHello() == 1) ? "YES" : "NO").append("\n");

		switch (netInfo.getNAT_Type()) {
			case 0:
				buffer.append("Local NAT Type            : UnKnow\n");
				break;
			case 1:
				buffer.append("Local NAT Type            : IP-Restricted Cone\n");
				break;
			case 2:
				buffer.append("Local NAT Type            : Port-Restricted Cone\n");
				break;
			case 3:
				buffer.append("Local NAT Type            : Symmetric\n");
				break;
			case 4:
				buffer.append("Local NAT Type            : Different Wan IP Detected!!\n");
				break;
		}
		buffer.append("My Wan IP  : ").append(netInfo.getMyWanIP()).append("\n");
		buffer.append("My Lan IP  : ").append(netInfo.getMyLanIP()).append("\n");
		buffer.append("-------------------------------\n");

		return buffer.toString();
	}

	public static String getP2PErrorMessage(int err) {

		switch (err) {
			case 0:
				return "ERROR_PPCS_SUCCESSFUL";
			case -1:
				return "ERROR_PPCS_NOT_INITIALIZED";
			case -2:
				return "ERROR_PPCS_ALREADY_INITIALIZED";
			case -3:
				return "ERROR_PPCS_TIME_OUT";
			case -4:
				return "ERROR_PPCS_INVALID_ID";
			case -5:
				return "ERROR_PPCS_INVALID_PARAMETER";
			case -6:
				return "ERROR_PPCS_DEVICE_NOT_ONLINE";
			case -7:
				return "ERROR_PPCS_FAIL_TO_RESOLVE_NAME";
			case -8:
				return "ERROR_PPCS_INVALID_PREFIX";
			case -9:
				return "ERROR_PPCS_ID_OUT_OF_DATE";
			case -10:
				return "ERROR_PPCS_NO_RELAY_SERVER_AVAILABLE";
			case -11:
				return "ERROR_PPCS_INVALID_SESSION_HANDLE";
			case -12:
				return "ERROR_PPCS_SESSION_CLOSED_REMOTE";
			case -13:
				return "ERROR_PPCS_SESSION_CLOSED_TIMEOUT";
			case -14:
				return "ERROR_PPCS_SESSION_CLOSED_CALLED";
			case -15:
				return "ERROR_PPCS_REMOTE_SITE_BUFFER_FULL";
			case -16:
				return "ERROR_PPCS_USER_LISTEN_BREAK";
			case -17:
				return "ERROR_PPCS_MAX_SESSION";
			case -18:
				return "ERROR_PPCS_UDP_PORT_BIND_FAILED";
			case -19:
				return "ERROR_PPCS_USER_CONNECT_BREAK";
			case -20:
				return "ERROR_PPCS_SESSION_CLOSED_INSUFFICIENT_MEMORY";
			case -21:
				return "ERROR_PPCS_INVALID_APILICENSE";
			case -22:
				return "ERROR_PPCS_FAIL_TO_CREATE_THREAD";
			case -23:
				return "ERROR_PPCS_INVALID_DSK";
			case -24:
				return "ERROR_PPCS_FAILED_TO_CONNECT_TCP_RELAY";
			case -25:
				return "ERROR_PPCS_FAIL_TO_ALLOCATE_MEMORY";
			default:
				return "Unknown error, something is wrong!";
		}
	}

	/************************* DID *************************/

	public String checkDID(@NonNull String did) {

		did = did.replace("-", "");
		if (!did.matches("^([a-zA-Z]{3,7})([0-9]{6})([a-zA-Z]{5})")) {
			mLastResultString = "DID length error!";
			LogUtil.d(mLastResultString);
			return null;
		}

		if (did.matches(":")) {
			if (!did.matches(":[a-zA-Z0-9]{8,23}$")) {
				mLastResultString = "dsk must be 8~23 character of [a~z, A~Z, 0~9].";
				LogUtil.d(mLastResultString);
				return null;
			}
			String[] ary = did.split(":");
			StringBuilder str = new StringBuilder(ary[0]);
			str.insert(str.length() - 11, "-");
			str.insert(str.length() - 5, "-");
			did = new String(str).toUpperCase();
			did = did + ":" + ary[1];
		} else {
			StringBuilder str = new StringBuilder(did);
			str.insert(str.length() - 11, "-");
			str.insert(str.length() - 5, "-");
			did = String.valueOf(str).toUpperCase();
		}
		LogUtil.d("did: " + did);

		return did;
	}

	public void updateDIDModel(DIDModel model) {

		HashMap<String, DIDModel> map = getLocalDIDHistoryMap();

		DIDModel didModel = map.get(model.did);

		if (didModel != null) {

			if (DIDModel.isSameModel(didModel, model)) {

				LogUtil.d(String.format("DID(%s) model exist!", model.did));
				return;
			}

			didModel.initString = model.initString;

			if (!TextUtils.isEmpty(model.license)) {
				didModel.license = model.license;
			}
			if (!TextUtils.isEmpty(model.crcKey)) {
				didModel.crcKey = model.crcKey;
			}
			if (!TextUtils.isEmpty(model.wakeupKey)) {
				didModel.wakeupKey = model.wakeupKey;
			}
			if (!TextUtils.isEmpty(model.ip1)) {
				didModel.ip1 = model.ip1;
			}
			if (!TextUtils.isEmpty(model.ip2)) {
				didModel.ip2 = model.ip2;
			}
			if (!TextUtils.isEmpty(model.ip3)) {
				didModel.ip3 = model.ip3;
			}

//            LogUtil.d("save did model: " + didModel);
			map.put(didModel.did, didModel);

		} else {
			map.put(model.did, model);
		}

		saveLocalDIDHistoryMap(map);
	}

	public void deleteDIDModel(String did) {

		HashMap<String, DIDModel> map = getLocalDIDHistoryMap();
		map.remove(did);
		saveLocalDIDHistoryMap(map);
	}

	private void saveLocalDIDHistoryMap(HashMap<String, DIDModel> localDIDHistoryMap) {

		JSONArray jsonArray = new JSONArray();
		try {
			for (DIDModel didInfo : localDIDHistoryMap.values()) {
				JSONObject json = didInfo.toJsonObject();
				jsonArray.put(json);
			}
		} catch (JSONException e) {
			LogUtil.e("saveHistoryDIDInfo JSONException:" + e.getMessage());
		}

		saveHistoryData(jsonArray.toString());
	}

	public HashMap<String, DIDModel> getLocalDIDHistoryMap() {

		HashMap<String, DIDModel> map = new HashMap<>();
		String data = readHistoryData();

		if (data == null || data.equals("")) return map;

		try {
			JSONArray jsonArray = new JSONArray(data);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				DIDModel model = new DIDModel(jsonObject);
				map.put(model.did, model);
			}
		} catch (JSONException e) {
			LogUtil.e("getLocalDIDHistoryList JSONException:" + e.getMessage());
		}

		return map;
	}

	private String readHistoryData() {
		SharedPreferences preferences = mContext.getSharedPreferences(DID_INFO_NAME, Context.MODE_PRIVATE);
		return preferences.getString(DID_INFO_NAME, "");
	}

	private void saveHistoryData(String data) {
		SharedPreferences.Editor editor = mContext.getSharedPreferences(DID_INFO_NAME, Context.MODE_PRIVATE).edit();
		editor.putString(DID_INFO_NAME, data);
		if (!editor.commit()) {
			LogUtil.v("Failed to commit setting changes.");
		}
	}
}
