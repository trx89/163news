package com.trx.news.utils;

import android.content.Context;

public class DestinyUtils {
	public static float px2dp(int px, Context ctx){
		float density = ctx.getResources().getDisplayMetrics().density;
		
		return (px / density);
	}
	
	public static int dp2px(float dp, Context ctx){
		float density = ctx.getResources().getDisplayMetrics().density;
		
		return (int)(dp * density + 0.5f);
	}
}
