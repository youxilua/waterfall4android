package com.youxilua.common.fetch;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Context;

import com.youxilua.common.cache.CacheUtils;
import com.youxilua.common.cache.DiskLruCache;

public class CacheFetch {
	/**
	 * 下载图片并且加入进 lru 缓存里面
	 * 
	 * @param context
	 * @param urlString
	 * @return
	 */
	public static File dowanLoadBitmap(Context context, String urlString) {
		//创建缓存图片保存目录,默认为内置
		final File cacheDir = DiskLruCache.getDiskCacheDir(context,
				CacheUtils.HTTP_CACHE_DIR);
		final DiskLruCache cache = DiskLruCache.openCache(context, cacheDir,
				CacheUtils.HTTP_CACHE_SIZE);
		final File cacheFile = new File(cache.createFilePath(urlString));
		if (cache.containsKey(urlString)) {
				CacheUtils.Debug(cacheFile.toString()+ "downloadBitmap - found in http cache - "
						+ urlString);
			return cacheFile;
		}
		
		CacheUtils.Debug( "downloadBitmap - downloading - " + urlString);
		//针对 2.1 版本 以前的版本
		CacheUtils.disableConnectionReuseIfNecessary();
		
		 HttpURLConnection urlConnection = null;
	     BufferedOutputStream out = null;
	     
	     try {
			final URL url = new URL(urlString);
			urlConnection = (HttpURLConnection) url.openConnection();
			final InputStream in = new BufferedInputStream(urlConnection.getInputStream(), CacheUtils.IO_BUFFER_SIZE);
			out = new BufferedOutputStream(new FileOutputStream(cacheFile), CacheUtils.IO_BUFFER_SIZE);
			CacheUtils.CopyStream(in, out);
			return cacheFile;
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(urlConnection != null){
				urlConnection.disconnect();
			}
			if(out != null){
				 try {
	                    out.close();
	                } catch (final IOException e) {
	                	CacheUtils.Debug("Error in downloadBitmap - " + e);
	                }
			}
		}

		return null;
	}
}
