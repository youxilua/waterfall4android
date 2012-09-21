package com.youxilua.waterfall;

import android.view.ViewGroup;

public class WaterFallOption {
	//显示列数
	public int column_count = 3;
	//每次加载的多少张图片
	public int pageCount = 20;
	 //允许加载的最多图片数
	public int pictureTotalCount = 10000;
	//用于handle 通讯的常量
	//消息发送的延迟时间
	public int message_delay = 200;
	//每列的宽度
	public int itemWidth;
	
	public ViewGroup waterFallContainer;
	
	public WaterFallOption(ViewGroup container,int itemWidth,int columnCount){
		this.waterFallContainer = container;
		this.itemWidth = itemWidth;
		this.column_count = columnCount;
	}
	
}
