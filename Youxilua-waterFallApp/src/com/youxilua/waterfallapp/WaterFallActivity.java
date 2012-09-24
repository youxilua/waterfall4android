package com.youxilua.waterfallapp;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.widget.LinearLayout;

import com.youxilua.waterfall.WaterFallOption;
import com.youxilua.waterfall.WaterFallView;
import com.youxilua.waterfall.WaterFallView.OnScrollListener;
import com.youxilua.waterfall.item.FlowView;
import com.youxilua.waterfall.item.FlowViewHandler;
import com.youxilua.waterfall.mock.ImageMock;

public class WaterFallActivity extends Activity implements OnScrollListener {

	private WaterFallView waterfall_scroll;
	private LinearLayout waterfall_container;
	private Display display;
	private AssetManager asset_manager;
	private List<String> image_filenames;
	private final String image_path = "images";
	private Handler handler;
	private int item_width;
	private int column_count = 3;// 显示列数

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.waterfall_display);
		display = getWindowManager().getDefaultDisplay();
		item_width = display.getWidth() / column_count;// 根据屏幕大小计算每列大小
		asset_manager = getAssets();

		initLayout();
	}

	private void initLayout() {
		
		//1 初始化waterfall 
		waterfall_scroll = (WaterFallView) findViewById(R.id.waterfall_scroll);
		//2 初始化显示容器
		waterfall_container = (LinearLayout) findViewById(R.id.waterfall_container);
		//3,设置滚动监听
		waterfall_scroll.setOnScrollListener(this);
		//4,实例一个设置
		WaterFallOption fallOption = new WaterFallOption(waterfall_container,
				item_width, column_count);
		//5,提交更改,实现android瀑布流
		waterfall_scroll.commitWaterFall(fallOption, waterfall_scroll);
		
		
		//1,瀑布流的item 处理设置
		handler = new FlowViewHandler(waterfall_scroll);

		// 加载所有图片路径
		
		// 第一次加载
		AddItemToContainer(waterfall_scroll.current_page,
				waterfall_scroll.pageCount);
	}

	private void AddItemToContainer(int pageindex, int pagecount) {

		int currentIndex = pageindex * pagecount;
		int imagecount = waterfall_scroll.pictureTotalCount;// image_filenames.size();
		
		for (int i = currentIndex; i < pagecount * (pageindex + 1)
				&& i < imagecount; i++) {
			waterfall_scroll.loaded_count++;
			Random rand = new Random();
			int r = rand.nextInt(ImageMock.imageThumbUrls.length);
			AddImage(ImageMock.imageThumbUrls[r],
					(int) Math.ceil(waterfall_scroll.loaded_count / (double) column_count),
					waterfall_scroll.loaded_count);
		}

	}

	private void AddImage(String filename, int rowIndex, int id) {

		FlowView item = new FlowView(this);
		item.setPadding(1, 1, 1, 1);
		item.setRowIndex(rowIndex);
		item.setId(id);
		item.setViewHandler(handler);
		item.setFileName(filename);
		item.setItemWidth(item_width);
		item.LoadImage();

	}

	// waterfall 监听

	@Override
	public void onBottom() {
		AddItemToContainer(++(waterfall_scroll.current_page), waterfall_scroll.pageCount);
	}

	@Override
	public void onTop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onScroll() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAutoScroll(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub

	}

}
