package com.youxilua.common.cache;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;

import com.youxilua.cache.BuildConfig;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

public class CacheUtils {
	public final static String TAG = "cache";
	private CacheUtils() {
	};

	public static final int HTTP_CACHE_SIZE = 10 * 1024 * 1024; // 10MB
	public static final String HTTP_CACHE_DIR = "http";

	// 默认缓存大小
	public static final int DEFAULT_CACHE_FILE_SIZE = 10 * 512; // 512 kb

	// 默认缓存图片目录
	public static final String IMAGE_CACHE_DIR = "thumbs";

	// 8k 缓存
	public static final int IO_BUFFER_SIZE = 8 * 1024;

	/**
	 * 检查提供的路径有多少可用的空间
	 * 
	 * @param path
	 * @return
	 */
	@SuppressLint("NewApi")
	public static long getUsableSpace(File path) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			Debug("2.3->"+ path.getUsableSpace());
			return path.getUsableSpace();
		}
		Debug("2.1->"+ path.getUsableSpace());
		final StatFs stats = new StatFs(path.getPath());
		return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
	}

	/**
	 * Check if external storage is built-in or removable. 2.3 以后的手机可以区分
	 * 内部大容量存储器 和 外部 内部为不可移除这样更保证我们程序缓存的稳定性
	 * 
	 * 注意! 2.3 以前没这个特性...蛋疼..
	 * 
	 * @return True if external storage is removable (like an SD card), false
	 *         otherwise.
	 */
	@SuppressLint("NewApi")
	public static boolean isExternalStorageRemovable() {
		// 2.3 以后才支持的参数
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			return Environment.isExternalStorageRemovable();
		}
		// 返回false 强制使用外部存储器
		return false;
	}

	/**
	 * 检查是否支持外部cachedir 目录
	 * 
	 * @return
	 */
	public static boolean hasExternalCacheDir() {
		return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
	}

	/**
	 * 获得程序在sd开上的cahce目录
	 * 
	 * @param context
	 *            The context to use
	 * @return The external cache dir
	 */
	@SuppressLint("NewApi")
	public static File getExternalCacheDir(Context context) {
		// android 2.2 以后才支持的特性
		if (hasExternalCacheDir()) {
			Debug("dir->" + context.getExternalCacheDir());
			return context.getExternalCacheDir();
		}

		// Before Froyo we need to construct the external cache dir ourselves
		// 2.2以前我们需要自己构造
		final String cacheDir = "/Android/data/" + context.getPackageName()
				+ "/cache/";
		return new File(Environment.getExternalStorageDirectory().getPath()
				+ cacheDir);
	}
	
	/**
	 * Check if OS version has a http URLConnection bug. See here for more
	 * information:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 * 
	 * @return
	 */
	public static boolean hasHttpConnectionBug() {
		// return Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO;
		return Build.VERSION.SDK_INT <= Build.VERSION_CODES.ECLAIR;
	}
	
	/**
	 * Workaround for bug pre-Froyo, see here for more info:
	 * http://android-developers.blogspot.com/2011/09/androids-http-clients.html
	 * 2.1 以前的版本你需要手动设置!
	 */
	public static void disableConnectionReuseIfNecessary() {
		// HTTP connection reuse which was buggy pre-froyo
		if (hasHttpConnectionBug()) {
			System.setProperty("http.keepAlive", "false");
		}
	}
	
	/**
	 * 复制流
	 * 
	 * @param is
	 * @param os
	 */
	public static void CopyStream(InputStream in, OutputStream out) {
		final int buffer_size = 1024;
		try {
			byte[] bytes = new byte[buffer_size];
			for (;;) {
				int count = in.read(bytes, 0, buffer_size);
				if (count == -1)
					break;
				out.write(bytes, 0, count);
			}
		} catch (Exception ex) {
		}

		// 实现二
		// int b;
		// while ((b = in.read()) != -1) {
		// out.write(b);
		// }

	}
	
	 /**
     * Get the memory class of this device (approx. per-app memory limit)
     *
     * @param context
     * @return
     */
    public static int getMemoryClass(Context context) {
        return ((ActivityManager) context.getSystemService(
                Context.ACTIVITY_SERVICE)).getMemoryClass();
    }
	public static void Debug(String message){
		if(BuildConfig.DEBUG){
			Log.d(TAG, message);
		}
	}

}
