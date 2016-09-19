package com.zzt.inbox.widget;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.view.ViewGroup;

/**
 * Created by Xinyue Zhao
 */
public interface InboxBackground {
	void drawTopShadow(int top, int height, int alpha);

	void drawBottomShadow(int top, int bottom, int alpha);

	void setTouchable(boolean touchable);

	int dpToPx(int dp);

	int getScrollRange();

	void drawOverlay(Canvas canvas);

	void setNeedToDrawSmallShadow(boolean needToDrawSmallShadow);

	void setNeedToDrawShadow(boolean needToDrawShadow);

	@NonNull
	ViewGroup toViewGroup();

	void setCurrentMode(InboxLayoutBase.Mode mode);
}
