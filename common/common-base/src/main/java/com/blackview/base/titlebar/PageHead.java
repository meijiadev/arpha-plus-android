package com.blackview.base.titlebar;

import android.app.Activity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.blackview.base.R;
import com.blackview.util.L;

public class PageHead implements View.OnClickListener {

	private static final String TAG = "PageHead";
	private final Activity mActivity;

	private ImageView mIvBack;
	private TextView mTvTitle;
	private TextView mTvRight;

	public interface OnPageHeadClickListener {
		void onRightClick();
	}

	private OnPageHeadClickListener mOnPageHeadClickListener;

	public PageHead(Activity activity, OnPageHeadClickListener listener) {
		mActivity = activity;
		mOnPageHeadClickListener = listener;

		mIvBack = activity.findViewById(R.id.ivBack);
		mIvBack.setOnClickListener(this);
		mTvTitle = activity.findViewById(R.id.tvTitle);
		mTvRight = activity.findViewById(R.id.tvRight);

	}

	public PageHead(Activity activity) {
		mActivity = activity;

		mIvBack = activity.findViewById(R.id.ivBack);
		mIvBack.setOnClickListener(this);
		mTvTitle = activity.findViewById(R.id.tvTitle);
		mTvRight = activity.findViewById(R.id.tvRight);

	}

	/**
	 * 设置标题名称
	 */
	public void setTitleText(String value) {
		if (!TextUtils.isEmpty(value)) {
			mTvTitle.setText(value);
		} else {
			L.logV(TAG, "the value is empty!");
		}
	}

	public void setTitleText(int value) {
		mTvTitle.setText(value);
	}

	public void hideIvBack(boolean isShow) {
		if (isShow) {
			mIvBack.setVisibility(View.GONE);
		} else {
			mIvBack.setVisibility(View.VISIBLE);
		}
	}


	public interface IBack {
		void onPressBack();
	}

	private IBack mBackListener = null;

	public void setBackClick(IBack listener) {
		mBackListener = listener;
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.ivBack) {
			if (mBackListener != null) {
				mBackListener.onPressBack();
			} else {
				if (mActivity != null) {
					mActivity.finish();
				}
			}
		} else if (id == R.id.tvRight) {
			if (mOnPageHeadClickListener != null) {
				mOnPageHeadClickListener.onRightClick();
			}
		}
	}
}
