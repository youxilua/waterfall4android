package com.youxilua.waterfall;

import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.youxilua.waterfall.item.FlowView;

public class WaterFallView extends ScrollView {
	
	public static final String TAG = "LazyScrollView";
	//图片显示相关的图片
	public int columnCount = 3;
	public int itemWidth;
	public int pageCount = 10;
	public int loadedCount = 0;
	public ArrayList<LinearLayout> waterfall_items;
	public Handler handler;
	public WaterFallUtils waterFallUtils;
	public View view;
	public ViewGroup waterfallContainer;
	
	public int current_page = 0;// 当前页数

	public int[] topIndex;
	public int[] bottomIndex;
	public int[] lineIndex;
	public int[] column_height;// 每列的高度

	public SparseArray<String> pins;

	public int loaded_count = 0;// 已加载数量

//	public HashMap<Integer, Integer>[] pin_mark;
	
//	public SparseArray<Integer> []  pin_mark;
	public SparseIntArray pin_mark;
	
	
//	public HashMap<Integer, FlowView> iviews;
	public SparseArray<FlowView> iviews;
	
	

	public WaterFallView(Context context) {
		super(context);

	}

	public WaterFallView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public WaterFallView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

	}
	
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		super.onScrollChanged(l, t, oldl, oldt);
		onScrollListener.onAutoScroll(l, t, oldl, oldt);
		waterFallUtils.autoReload(l, t, oldl, oldt);
		
	}
	
	public void setOptions(){
		
	}
	
	private void init() {
		this.setOnTouchListener(onTouchListener);
		column_height = new int[columnCount];
		iviews = new SparseArray<FlowView>();
		pins = new SparseArray<String>();
		pin_mark = new SparseIntArray(columnCount);
		this.lineIndex = new int[columnCount];
		this.bottomIndex = new int[columnCount];
		this.topIndex = new int[columnCount];
		for (int i = 0; i < columnCount; i++) {
			lineIndex[i] = -1;
			bottomIndex[i] = -1;
		}
		//初始化话waterfall_items 用于加载图片
		waterfall_items = new ArrayList<LinearLayout>();
		for(int i = 0; i < columnCount; i++){
			LinearLayout itemLayout = new LinearLayout(getContext());
			LinearLayout.LayoutParams itemParam = new LinearLayout.LayoutParams(itemWidth, LayoutParams.WRAP_CONTENT);
			itemLayout.setPadding(2, 2, 2, 2);
			itemLayout.setOrientation(LinearLayout.VERTICAL);
			itemLayout.setLayoutParams(itemParam);
			waterfall_items.add(itemLayout);
			//用于加载单列的显示
			waterfallContainer.addView(itemLayout);
		}
	}
	/**
	 * 获得参考的View，主要是为了获得它的MeasuredHeight，然后和滚动条的ScrollY+getHeight作比较。
	 */
	public void commitWaterFall(WaterFallOption options,WaterFallView currentFallView){
		this.columnCount = options.column_count;
		this.itemWidth = options.itemWidth;
		this.waterfallContainer = options.waterFallContainer;
		this.pageCount = options.pageCount;
		waterFallUtils = new WaterFallUtils(currentFallView);
		this.view = getChildAt(0);
		if (view != null) {
			handler = new WaterFallHandler(view, this);
			init();
		}
		
	}
	OnTouchListener onTouchListener = new OnTouchListener() {

		@Override
		public boolean onTouch(View v, MotionEvent event) {

			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				Log.d(TAG,"ACTION_DOWN"+"Y->"+ event.getY()+"X->"+event.getX());
				break;
			case MotionEvent.ACTION_UP:
				if (view != null && onScrollListener != null) {
					handler.sendMessageDelayed(handler.obtainMessage(
							Constants.HANDLER_WHAT), Constants.MESSAGE_DELAY);
				}
				break;
			case MotionEvent.ACTION_MOVE:
				//Log.d(TAG,"ACTION_MOVE"+"Y->"+ event.getY()+"X->"+event.getX());
				break;
			default:
				break;
			}
			return false;
		}

	};
	/**
	 * 定义接口
	 * 
	 * @author admin
	 * 
	 */
	public interface OnScrollListener {
		void onBottom();

		void onTop();

		void onScroll();

		void onAutoScroll(int l, int t, int oldl, int oldt);
	}

	protected OnScrollListener onScrollListener;

	public void setOnScrollListener(OnScrollListener onScrollListener) {
		this.onScrollListener = onScrollListener;
	}
	
	public static void Debug(String message){
		if(BuildConfig.DEBUG){
			Log.d(TAG, message);
		}
	}
}
