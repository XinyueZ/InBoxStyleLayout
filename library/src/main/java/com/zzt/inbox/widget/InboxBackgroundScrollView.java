package com.zzt.inbox.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.ScrollView;

/**
 * Created by zzt on 2015/1/27.
 */
public final class InboxBackgroundScrollView extends ScrollView implements InboxBackground {

	private boolean mTouchable = true;

	private boolean mNeedToDrawSmallShadow = false;
	private boolean mNeedToDrawShadow = false;

	private static final int MAX_MENU_OVERLAY_ALPHA = 185;
	private Drawable mTopSmallShadowDrawable;
	private Drawable mBottomSmallShadowDrawable;
	private Drawable mTopShadow = new ColorDrawable(0xff000000);
	private Drawable mBottomShadow = new ColorDrawable(0xff000000);
	private int smallShadowHeight;

	public InboxBackgroundScrollView(Context context) {
		this(context, null);
	}

	public InboxBackgroundScrollView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public InboxBackgroundScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mTopSmallShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
		                                               new int[] { 0x77101010,
		                                                           0 });
		mBottomSmallShadowDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM,
		                                                  new int[] { 0x77101010,
		                                                              0 });
		smallShadowHeight = dpToPx(10);
	}

	@Override
	protected void dispatchDraw(Canvas canvas) {
		super.dispatchDraw(canvas);
		drawOverlay(canvas);
	}

	@Override
	public void drawOverlay(Canvas canvas) {
		if (mNeedToDrawShadow) {
			mTopShadow.draw(canvas);
			mBottomShadow.draw(canvas);
		}
		if (mNeedToDrawSmallShadow) {
			mTopSmallShadowDrawable.draw(canvas);
			mBottomSmallShadowDrawable.draw(canvas);
		}
	}

	@Override
	public void drawTopShadow(int top, int height, int alpha) {
		mTopShadow.setBounds(0, top, getWidth(), top + height);
		mTopShadow.setAlpha(alpha);
		if (mNeedToDrawSmallShadow) {
			mTopSmallShadowDrawable.setBounds(0, top + height - smallShadowHeight, getWidth(), top + height);
		}
		//invalidate();
	}

	@Override
	public void drawBottomShadow(int top, int bottom, int alpha) {
		mBottomShadow.setBounds(0, top, getWidth(), bottom);
		mBottomShadow.setAlpha(alpha);
		if (mNeedToDrawSmallShadow) {
			mBottomSmallShadowDrawable.setBounds(0, top, getWidth(), top + smallShadowHeight);
		}
		//invalidate();
	}

	@Override
	public void setTouchable(boolean touchable) {
		mTouchable = touchable;
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		if (!mTouchable) {
		    /*
		    * just eat the touch event
            * */
			return true;
		}
		return super.onTouchEvent(ev);
	}

	@Override
	public int getScrollRange() {
		return computeVerticalScrollRange();
	}

	@Override
	public int dpToPx(int dp) {
		//不需要context的
		return (int) (dp * Resources.getSystem()
		                            .getDisplayMetrics().density);
	}


	@Override
	public void setNeedToDrawSmallShadow(boolean needToDrawSmallShadow) {
		mNeedToDrawSmallShadow = needToDrawSmallShadow;
	}

	@Override
	public void setNeedToDrawShadow(boolean needToDrawShadow) {
		mNeedToDrawShadow = needToDrawShadow;
	}


	@NonNull
	@Override
	public ViewGroup toViewGroup() {
		return this;
	}

	@Override
	public void setCurrentMode(InboxLayoutBase.Mode mode) {
		//No impl
	}
}
