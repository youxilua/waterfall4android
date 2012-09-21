package com.youxilua.waterfall.item;

import android.os.Handler;
import android.os.Message;

import com.youxilua.waterfall.Constants;
import com.youxilua.waterfall.WaterFallView;

public class FlowViewHandler extends Handler {
	private WaterFallView fallView;
	public FlowViewHandler(WaterFallView sv){
		this.fallView = sv;
	}
	private int GetMinValue(int[] array) {
		int m = 0;
		int length = array.length;
		for (int i = 0; i < length; ++i) {

			if (array[i] < array[m]) {
				m = i;
			}
		}
		return m;
	}
	
	@Override
	public void handleMessage(Message msg) {

		// super.handleMessage(msg);

		switch (msg.what) {
		case Constants.HANDLER_WHAT:
			
			FlowView v = (FlowView) msg.obj;
			WaterFallView.Debug("width->"+msg.arg1);
			int h = msg.arg2;
			// Log.d("MainActivity",
			// String.format(
			// "获取实际View高度:%d,ID：%d,columnIndex:%d,rowIndex:%d,filename:%s",
			// v.getHeight(), v.getId(), v
			// .getColumnIndex(), v.getRowIndex(),
			// v.getFlowTag().getFileName()));
			String f = v.getFileName();

			// 此处计算列值
			int columnIndex = GetMinValue(fallView.column_height);

			v.setColumnIndex(columnIndex);

			fallView.column_height[columnIndex] += h;

			fallView.pins.put(v.getId(), f);
			fallView.iviews.put(v.getId(), v);
			fallView.waterfall_items.get(columnIndex).addView(v);

			fallView.lineIndex[columnIndex]++;

			fallView.pin_mark[columnIndex].put(fallView.lineIndex[columnIndex],
					fallView.column_height[columnIndex]);
			fallView.bottomIndex[columnIndex] = fallView.lineIndex[columnIndex];
			break;
		}

	}

	@Override
	public boolean sendMessageAtTime(Message msg, long uptimeMillis) {
		return super.sendMessageAtTime(msg, uptimeMillis);
	}
};


