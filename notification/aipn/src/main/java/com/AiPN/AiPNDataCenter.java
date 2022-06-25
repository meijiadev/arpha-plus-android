package com.AiPN;

import android.content.Context;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import mykj.stg.aipn.lgUtil.LogUtil;

public class AiPNDataCenter {

	private static final AiPNDataCenter g_AiPNDataSDK = new AiPNDataCenter();
	private Context m_context = null;

	private static int curCheckStep = -1; // 记录当前检测步骤

	public checkNetworkAndSdkCbk mCheckNetworkAndSdkCbk = null;

	public interface checkNetworkAndSdkCbk {
		void onCheckStart(int uid);

		void onCheckResult(int uid, int ret);
	}

	public AiPNSDK aipnSDK = null;
	public List<AiPNDeviceModel> deviceArr = null;

	HashMap<String, AiPNDeviceModel> mDeviceModelHashMap = new HashMap<>();

	public static AiPNDataCenter getInstance() {
		return g_AiPNDataSDK;
	}

	public void configAiPNSDK(Context context) {

		m_context = context;
		deviceArr = AiPNDeviceModel.loadLocalCameraModel(context);

		LogUtil.d("device: " + deviceArr.size());

		aipnSDK = AiPNSDK.getInstance();
		aipnSDK.configAiPNSDK(context);
		aipnSDK.initSPSSDK();

		if (deviceArr.size() > 0) {
			for (AiPNDeviceModel mode : deviceArr) {
				mDeviceModelHashMap.put(mode.DID, mode);
				if (mode.isSubOK) {
					aipnSDK.defaultModel = mode;
					break;
				}
			}
		}

	}

	public int checkSubscribe() {

		int iRet = aipnSDK.checkSubscribe();

		if (iRet == 0) {
			try {
				JSONArray jsonArr = new JSONArray(aipnSDK.lastResultString);

				for (AiPNDeviceModel tmpModel : deviceArr) {
					if (tmpModel.isSubOK) {
						boolean isSub = false;
						for (int i = 0; i < jsonArr.length(); ++i) {
							JSONObject json = jsonArr.getJSONObject(i);
							if (json.getString("DID").equals(tmpModel.DID)) {
								isSub = true;
								break;
							}
						}
						if (!isSub) {
							LogUtil.e("retry sub " + tmpModel.DID);
							aipnSDK.enableSubscribe(true, tmpModel.DID, tmpModel.subscribeKey, tmpModel.channel);
						}
					}
				}
			} catch (Exception ignored) {
			}
		} else if (iRet == -113) {
			for (AiPNDeviceModel model : deviceArr) {
				if (model.isSubOK) {
					LogUtil.e("retry sub " + model.DID);
					aipnSDK.enableSubscribe(true, model.DID, model.subscribeKey, model.channel);
				}
			}
		}

		return iRet;
	}

	public void resetDeviceSubState() {

		for (AiPNDeviceModel devModel : deviceArr) {
			devModel.isSubOK = false;
		}
	}

	/********************************设备管理**********************/
	public void addDevice(AiPNDeviceModel devModel) {

		LogUtil.d("add device: " + devModel.DID);
		if (mDeviceModelHashMap.containsKey(devModel.DID)) {
			return;
		}

		deviceArr.add(devModel);
		mDeviceModelHashMap.put(devModel.DID, devModel);

		AiPNDeviceModel.updateLocalCameraInfo(m_context, deviceArr);

	}

	public void removeDevice(AiPNDeviceModel devModel) {

		if (existDevice(devModel.DID) != null) {
			deviceArr.remove(devModel);
			mDeviceModelHashMap.remove(devModel.DID);
			AiPNDeviceModel.updateLocalCameraInfo(m_context, deviceArr);
		}
	}

	public AiPNDeviceModel existDevice(String DID) {

		if (TextUtils.isEmpty(DID)) return null;
		LogUtil.d("existDevice: " + DID);

		String convertDID = DID.replace("-", "");
		for (AiPNDeviceModel tmpModel : deviceArr) {
			String tmpDID = tmpModel.DID.replace("-", "");
			if (tmpDID.equals(convertDID)) {
				return tmpModel;
			}
		}

		return null;
	}

	public void updateDevice(String subscribeKey, int channel, AiPNDeviceModel devModel) {

		if (existDevice(devModel.DID) != null) {

			mDeviceModelHashMap.remove(devModel.DID);
			deviceArr.remove(devModel);

			devModel.subscribeKey = subscribeKey;
			devModel.channel = channel;

		}
		mDeviceModelHashMap.put(devModel.DID, devModel);
		deviceArr.add(devModel);

		AiPNDeviceModel.updateLocalCameraInfo(m_context, deviceArr);
	}

}
