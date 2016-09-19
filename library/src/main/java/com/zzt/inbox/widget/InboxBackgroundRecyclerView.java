package com.zzt.inbox.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Xinyue Zhao
 */
public final class InboxBackgroundRecyclerView extends RecyclerView implements InboxBackground {
	private static final String TAG = InboxBackgroundRecyclerView.class.getName();

	private boolean mTouchable = true;

	public boolean mNeedToDrawSmallShadow = false;
	public boolean mNeedToDrawShadow = false;


	private static final int MAX_MENU_OVERLAY_ALPHA = 185;
	private Drawable mTopSmallShadowDrawable;
	private Drawable mBottomSmallShadowDrawable;
	private Drawable mTopShadow = new ColorDrawable(0xff000000);
	private Drawable mBottomShadow = new ColorDrawable(0xff000000);
	private int smallShadowHeight;


	public InboxBackgroundRecyclerView(Context context) {
		this(context, null);
	}

	public InboxBackgroundRecyclerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public InboxBackgroundRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
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
		drawTopShadow(0, 0, 0);
		drawBottomShadow(0, 0, 0);
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


	private int mSelectedPosition;
	private int mSelectedOffset;
	private boolean mInboxExpanded;
	private int mStep;

	@Override
	public void scrollTo(int x, int y) {
		if (mSelectedOffset == 0) {
			return;
		}
//		Log.d(TAG, "mSelectedOffset: " + mSelectedOffset + " height: " + getHeight() + " scrollTo Y: " + y);
		float offset;
		float factor;
		if (!mInboxExpanded) {
			//Expand
			factor = y / (mSelectedOffset + 1f);
			offset = mSelectedOffset - mSelectedOffset * factor;
			mStep++;
			if (mSelectedOffset == y) {
				mInboxExpanded = true;
			}
//			Log.d(TAG, "factor: " + factor + ", step:" + mStep);
		} else {
			//Close
			factor = mSelectedOffset == 0 ?
			         mSelectedOffset :
			         (mSelectedOffset / mStep) / mSelectedOffset;
			offset = mSelectedOffset - mSelectedOffset * factor;
			mStep--;
			if (mSelectedOffset == offset) {
				mInboxExpanded = false;
				mStep = 0;
			}
//			Log.d(TAG, "factor: " + factor + ", step:" + mStep);
		}
//		Log.d(TAG, "offset: " + (int) offset + ", mInboxExpanded: " + mInboxExpanded);
		((LinearLayoutManager) getLayoutManager()).scrollToPositionWithOffset(mSelectedPosition, (int) offset);
	}


	public final void setSelectedPosition(int selectedPosition) {
		mSelectedPosition = selectedPosition;
		View v = getLayoutManager().findViewByPosition(selectedPosition);
		mSelectedOffset = v.getTop();
	}

	@NonNull
	@Override
	public ViewGroup toViewGroup() {
		return this;
	}

	@Override
	public void scrollBy(int x, int y) {
		if (mSelectedPosition > 0) {
			super.scrollBy(x, y);
		} else {
			switch (mCurrentMode) {
				case PULL_FROM_START:
					if (y < 0) {
						super.scrollBy(x, y);
					} else {
						super.scrollBy(x, -y);
					}
					break;
				default:
					super.scrollBy(x, y);
					break;
			}

		}
	}

	private InboxLayoutBase.Mode mCurrentMode;

	@Override
	public void setCurrentMode(InboxLayoutBase.Mode mode) {
		mCurrentMode = mode;
	}
}
