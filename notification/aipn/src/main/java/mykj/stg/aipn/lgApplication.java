package mykj.stg.aipn;

import android.app.Application;

import com.AiPN.AiPNDataCenter;


public class lgApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();

		AiPNDataCenter.getInstance().configAiPNSDK(this);


	}

}
