package com.youxilua.common.cache;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;

public class ImageUtils {
	/**
	 * Get the size in bytes of a bitmap.
	 * 
	 * @param bitmap
	 * @return size in bytes
	 */
	@SuppressLint("NewApi")
	public static int getBitmapSize(Bitmap bitmap) {
//		 if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1) {
//		 return bitmap.getByteCount();
//		 }
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}
