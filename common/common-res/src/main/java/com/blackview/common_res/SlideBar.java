package com.blackview.common_res;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * @author zengyi
 * create at 2022/7/6 17:32
 */
public class SlideBar extends RelativeLayout {
	private OnUnlockListener listenerLeft = null;
	private OnUnlockListener listenerRight = null;

	private ImageView img_thumb = null;

	private int thumbWidth = 0;
	boolean sliding = false;
	private int sliderPosition = 0;
	int initialSliderPosition = 0;
	float initialSlidingX = 0;

	public SlideBar(Context context) {
		super(context);
		init(context, null);

	}

	public SlideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context, attrs);
	}

	public SlideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context, attrs);
	}

	public void setOnUnlockListenerLeft(OnUnlockListener listener) {
		this.listenerLeft = listener;
	}

	public void setOnUnlockListenerRight(OnUnlockListener listener) {
		this.listenerRight = listener;
	}

	public void reset() {
		final LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
		ValueAnimator animator = ValueAnimator.ofInt(params.leftMargin, (getMeasuredWidth() - thumbWidth) / 2);
		animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator) {
				params.leftMargin = (Integer) valueAnimator.getAnimatedValue();
				img_thumb.requestLayout();
			}
		});
		animator.setDuration(300);
		animator.start();
	}

	private void init(Context context, AttributeSet attrs) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.layout_slide_bar, this, true);
		img_thumb = findViewById(R.id.img_thumb);

		ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
		if (viewTreeObserver.isAlive()) {
			viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					SlideBar.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
					thumbWidth = img_thumb.getWidth() + dpToPx(20);
					sliderPosition = (SlideBar.this.getWidth() - thumbWidth) / 2;
					LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
					params.setMargins((SlideBar.this.getWidth() - thumbWidth) / 2, 0, 0, 0);
					img_thumb.setLayoutParams(params);
				}
			});
		}


	}


	@Override
	@SuppressLint("ClickableViewAccessibility")
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (event.getX() > sliderPosition && event.getX() < (sliderPosition + thumbWidth)) {
				Log.d("event", "event " + event.getX());
				sliding = true;
				initialSlidingX = event.getX();
				initialSliderPosition = sliderPosition;
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP || event.getAction() == MotionEvent.ACTION_OUTSIDE) {

			if (sliderPosition >= (getMeasuredWidth() - thumbWidth)) {
				if (listenerRight != null) listenerRight.onUnlock();
			} else if (sliderPosition <= getMeasuredWidth() / 2) {
				if (listenerLeft != null) listenerLeft.onUnlock();
			} else {
				sliding = false;
				sliderPosition = (getMeasuredWidth() - thumbWidth) / 2;
			}
			reset();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE && sliding) {
			sliderPosition = (int) (initialSliderPosition + (event.getX() - initialSlidingX));
			if (sliderPosition <= 0) sliderPosition = 0;

			if (sliderPosition >= (getMeasuredWidth() - thumbWidth)) {
				sliderPosition = getMeasuredWidth() - thumbWidth;
			}
			setMarginLeft(sliderPosition);
		}
		return true;
	}

	private void setMarginLeft(int margin) {
		if (img_thumb == null) return;
		LayoutParams params = (LayoutParams) img_thumb.getLayoutParams();
		params.setMargins(margin, 0, 0, 0);
		img_thumb.setLayoutParams(params);
	}

	private int dpToPx(int dp) {
		float density = getResources().getDisplayMetrics().density;
		return Math.round((float) dp * density);
	}

	public interface OnUnlockListener {
		void onUnlock();
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		//父层ViewGroup不要拦截点击事件 
		getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);
	}
}