package com.youxilua.waterfall;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

public class WaterFallHandler extends Handler {
	private View childView;
	private WaterFallView waterFallView;

	public WaterFallHandler(View ls, WaterFallView waterFallView) {
		this.childView = ls;
		this.waterFallView = waterFallView;
	}

	@Override
	public void handleMessage(Message msg) {
		super.handleMessage(msg);
		switch (msg.what) {
		case Constants.HANDLER_WHAT:
			if (childView.getMeasuredHeight() - 20 <= waterFallView
					.getScrollY() + waterFallView.getHeight()) {
				if(BuildConfig.DEBUG){
					Log.d(WaterFallView.TAG,"onBottom"+ "childViewHeight->"+childView.getMeasuredHeight() +"waterFallView->"
							+(waterFallView
							.getScrollY() + waterFallView.getHeight()));
				}
				if (waterFallView.onScrollListener != null) {
					waterFallView.onScrollListener.onBottom();
				}

			} else if (waterFallView.getScrollY() <= 0) {
				if (waterFallView.onScrollListener != null) {
					if(BuildConfig.DEBUG){
						Log.d(WaterFallView.TAG, "onTop->" + waterFallView.getScrollY());
					}
					waterFallView.onScrollListener.onTop();
				}
			} else {
				if (waterFallView.onScrollListener != null) {
					if(BuildConfig.DEBUG){
						Log.d(WaterFallView.TAG, "onScroll");
					}
					waterFallView.onScrollListener.onScroll();
				}
			}
			break;

		default:
			break;
		}
	}

}
