waterfall4android
=================

**该项目不维护了,因为有个更好的瀑布流..**
现在维护的是:
[https://github.com/youxiachai/pinterest-like-adapter-view](https://github.com/youxiachai/pinterest-like-adapter-view)

android瀑布流

#android 瀑布流的实现详解,附源码#

##介绍##

参考自:[https://github.com/dodola/android_waterfall](https://github.com/dodola/android_waterfall "瀑布流"),因为原来的代码封装不好,所以,我根据源码的思路,重新写了一遍,所以有了现在这个项目:[https://github.com/youxilua/waterfall4android](https://github.com/youxilua/waterfall4android "android瀑布流")

原作者表示:
> 试过在1万张可以流畅的滑动，不出现内存溢出情况

##设计思路##
![](http://images.cnblogs.com/cnblogs_com/youxilua/201209/201209211519192946.png)

之前的作者的自定义view 只有主滑动一层,其他的设置要在相应的活动设置,个人觉得,重用起来比较麻烦,所以决定封装一层.现在定义一个默认的瀑布流只需5步,以下为源码示意,具体,看源码...

//1 初始化waterfall 

`waterfall_scroll = (WaterFallView) findViewById(R.id.waterfall_scroll);`


//2 初始化显示容器

`waterfall_container = (LinearLayout) findViewById(R.id.waterfall_container);`

//3,设置滚动监听

`waterfall_scroll.setOnScrollListener(this);`

//4,实例一个设置

`WaterFallOption fallOption = new WaterFallOption(waterfall_container,
				每列宽度, 列数);`

//5,提交更改,实现android瀑布流

`waterfall_scroll.commitWaterFall(fallOption, waterfall_scroll);`

最后不要忘了监听滚动到底部的监听

`@Override
	public void onBottom() {
		AddItemToContainer(++(waterfall_scroll.current_page), waterfall_scroll.pageCount);
	}`


##已知bug##
这里出现的bug,原来的也有...

* 滚动过快,导致部分图片无法显示

##功能加强##

* 实现支持URL的方式加载图片
