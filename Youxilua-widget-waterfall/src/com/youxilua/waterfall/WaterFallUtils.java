package com.youxilua.waterfall;

import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.youxilua.waterfall.item.FlowView;

public class WaterFallUtils {
	private WaterFallView fallView;
	private int scrollHeight;

	public int getScrollHeight(ScrollView scroll) {
		return scroll.getMeasuredHeight();
	}

	public WaterFallUtils(WaterFallView waterFallView) {
		this.fallView = waterFallView;

	}

	public void autoReload(int l, int t, int oldl, int oldt) {

		scrollHeight = fallView.getMeasuredHeight();
		WaterFallView.Debug("scroll_height:" + scrollHeight);

		if (t > oldt) {// 向下滚动
			scrollDown(l, t, oldl, oldt);
		} else {// 向上滚动
			scrolldUp(l, t, oldl, oldt);
		}
	}

	private void scrolldUp(int l, int t, int oldl, int oldt) {
		if (t > 2 * scrollHeight) {// 超过两屏幕后
			for (int k = 0; k < fallView.columnCount; k++) {
				LinearLayout localLinearLayout = fallView.waterfall_items
						.get(k);
				if (fallView.pin_mark.get(fallView.bottomIndex[k]) > t + 3
						* scrollHeight) {
					WaterFallView.Debug("recycle,k:" + fallView.bottomIndex[k]);
					((FlowView) localLinearLayout
							.getChildAt(fallView.bottomIndex[k])).recycle();

					fallView.bottomIndex[k]--;
				}

				if (fallView.pin_mark.get(Math.max(fallView.topIndex[k] - 1,
						0)) >= t - 2 * scrollHeight) {
					((FlowView) localLinearLayout.getChildAt(Math.max(-1
							+ fallView.topIndex[k], 0))).Reload();
					fallView.topIndex[k] = Math
							.max(fallView.topIndex[k] - 1, 0);
				}
			}
		}
	}

	//
	private void scrollDown(int l, int t, int oldl, int oldt) {
		if (t > 2 * scrollHeight) {// 超过两屏幕后
			for (int k = 0; k < fallView.columnCount; k++) {
				LinearLayout localLinearLayout = fallView.waterfall_items
						.get(k);

				if (fallView.pin_mark.get(Math.min(
						fallView.bottomIndex[k] + 1, fallView.lineIndex[k])) <= t
						+ 3 * scrollHeight) {// 最底部的图片位置小于当前t+3*屏幕高度
					
					((FlowView) fallView.waterfall_items.get(k).getChildAt(
							Math.min(1 + fallView.bottomIndex[k],
									fallView.lineIndex[k]))).Reload();

					fallView.bottomIndex[k] = Math.min(
							1 + fallView.bottomIndex[k], fallView.lineIndex[k]);
					WaterFallView.Debug("relaod,k:"+fallView.bottomIndex[k]);

				}
				WaterFallView.Debug("headIndex:" + fallView.topIndex[k]
						+ "  footIndex:" + fallView.bottomIndex[k]
						+ "  headHeight:"
						+ fallView.pin_mark.get(fallView.topIndex[k]));
				if (fallView.pin_mark.get(fallView.topIndex[k]) < t - 2
						* scrollHeight) {// 未回收图片的最高位置<t-两倍屏幕高度
					WaterFallView.Debug("recycle,k:" + k + " headindex:"
							+ fallView.topIndex[k]);
					int i1 = fallView.topIndex[k];
					fallView.topIndex[k]++;
					((FlowView) localLinearLayout.getChildAt(i1)).recycle();
				}
			}

		}
	}
}
