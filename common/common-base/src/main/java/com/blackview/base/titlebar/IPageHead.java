package com.blackview.base.titlebar;

import android.app.Activity;

public interface IPageHead {
	/**
	 * 
	 * @param activity
	 * @param listener
	 * @return
	 */
	PageHead getPageHead(Activity activity,
						 PageHead.OnPageHeadClickListener listener);

	PageHead getPageHead(Activity activity);
}