package mykj.stg.aipn;

import android.os.Bundle;
import android.widget.Toast;

import com.AiPN.AiPNDataCenter;
import com.AiPN.AiPNDeviceModel;
import com.AiPN.AiPNSDK;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * ━━━━━━神兽出没━━━━━━
 * 　　　┏┓　　　┏┓
 * 　　┏┛┻━━━┛┻┓
 * 　　┃　　　　　　　┃
 * 　　┃　　　━　　　┃
 * 　　┃　┳┛　┗┳　┃
 * 　　┃　　　　　　　┃
 * 　　┃　　　┻　　　┃
 * 　　┃　　　　　　　┃
 * 　　┗━┓　　　┏━┛Code is far away from bug with the animal protecting
 * 　　　　┃　　　┃    神兽保佑,代码无bug
 * 　　　　┃　　　┃
 * 　　　　┃　　　┗━━━┓
 * 　　　　┃　　　　　　　┣┓
 * 　　　　┃　　　　　　　┏┛
 * 　　　　┗┓┓┏━┳┓┏┛
 * 　　　　　┃┫┫　┃┫┫
 * 　　　　　┗┻┛　┗┻┛
 * ━━━━━━感觉萌萌哒━━━━━━
 * <p>
 * Created by home on 2022/6/23.
 */
public class NoticeTestActivity extends AppCompatActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		AiPNDeviceModel model = new AiPNDeviceModel("LDNVRCN-000006-DLFUX",
				"123456", 1000);
		findViewById(R.id.btn_add).setOnClickListener(v -> {
			AiPNSDK.getInstance().checkDeviceToken();
			AiPNDataCenter.getInstance().addDevice(model);
		});

		findViewById(R.id.btn_open).setOnClickListener(v -> {
			//开启推送
			new Thread(() -> {
				final int iRet = AiPNSDK.getInstance().enablePush(true);
				if (iRet >= 0) {
					AiPNDataCenter.getInstance().checkSubscribe();
				}
				runOnUiThread(() -> {
					if (iRet >= 0) {
						Toast.makeText(this, getString(R.string.ShowMsg_OnPush), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(this, getString(R.string.main_enable_global_push_failed), Toast.LENGTH_SHORT).show();
					}
				});
			}).start();
		});
		findViewById(R.id.btn_close).setOnClickListener(v -> {
			//关闭推送
			new Thread(() -> {
				final int iRet = AiPNSDK.getInstance().enablePush(false);
				if (iRet >= 0) {
					AiPNDataCenter.getInstance().checkSubscribe();
				}
				runOnUiThread(() -> {
					if (iRet >= 0) {
						Toast.makeText(this, getString(R.string.ShowMsg_OffPush), Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(this, getString(R.string.main_enable_global_push_failed), Toast.LENGTH_SHORT).show();
					}
				});
			}).start();
		});
		findViewById(R.id.btn_open1).setOnClickListener(v -> {
			//if (!AiPNDataCenter.getInstance().aipnSDK.isEnablePush){
			//	Toast.makeText(this,"请先开启推送",Toast.LENGTH_SHORT).show();
			//	return;
			//}
			//订阅设备推送
			new Thread(() -> {
				final int iRet = AiPNDataCenter.getInstance().aipnSDK.enableSubscribe(true, model.DID, model.subscribeKey, model.channel);
				runOnUiThread(() -> {
					if (iRet == 0) {
						model.isSubOK = true;
						String s = getString(true ? R.string.ShowMsg_SubscribOk : R.string.ShowMsg_unSubscribOk);
						Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
						AiPNDataCenter.getInstance().updateDevice(model.subscribeKey, model.channel, model);
					} else {
						if (iRet == -104) {
							model.isValidDID = false;
						}
						String msg = true ? getString(R.string.devlist_subscrib_failed) : getString(R.string.devlist_unsubscrib_failed);
						Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
					}
				});
			}).start();
		});
		findViewById(R.id.btn_close1).setOnClickListener(v -> {
			//关闭订阅
			new Thread(() -> {
				final int iRet = AiPNDataCenter.getInstance().aipnSDK.enableSubscribe(false, model.DID, model.subscribeKey, model.channel);
				runOnUiThread(() -> {
					if (iRet == 0) {
						model.isSubOK = false;
						String s = getString(false ? R.string.ShowMsg_SubscribOk : R.string.ShowMsg_unSubscribOk);
						Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
						AiPNDataCenter.getInstance().updateDevice(model.subscribeKey, model.channel, model);
					} else {
						if (iRet == -104) {
							model.isValidDID = false;
						}
						String msg = true ? getString(R.string.devlist_subscrib_failed) : getString(R.string.devlist_unsubscrib_failed);
						Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
					}
				});
			}).start();
		});


	}
}
