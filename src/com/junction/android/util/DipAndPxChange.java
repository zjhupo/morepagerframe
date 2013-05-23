package com.junction.android.util;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;

public class DipAndPxChange {
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}
	//把hdpi的图片转换为对应分辨率的图片 http://www.cnblogs.com/wangtianxj/archive/2011/03/18/1988358.html
    //低分辨率支持会出问题
    //size 对应drawable .getIntrinsicWidth() 或者 .getIntrinsicHeight()的值
    public static int imageToDip(Activity context,int size){
    	DisplayMetrics displayMetrics = new DisplayMetrics(); 
    	
    	context.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
    	int height = displayMetrics.heightPixels;
    	int width = displayMetrics.widthPixels;
    	
    	 float density = displayMetrics.density;  // 屏幕密度（0.75 / 1.0 / 1.5）
         int densityDpi = displayMetrics.densityDpi;  // 屏幕密度DPI（120 / 160 / 240）//240 ,320 ,480
         float showSize = (float)width/(densityDpi*2)*size ;
//    	 Log.i("view" , "height" +displayMetrics.heightPixels); 
//    	 Log.i("view" , "width" +displayMetrics.widthPixels);
//    	 Log.i("view" , "size change " +size+" to" + showSize);
    	 
    	 //int size =
    	 return (int)showSize ;
    }
}
