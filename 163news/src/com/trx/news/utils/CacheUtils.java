package com.trx.news.utils;

import android.content.Context;

public class CacheUtils {

	public static String getCache(String url, Context context){
		
		String result = PrefUtils.getString(url, null, context);
		
		return result;
	}
	
	public static void setCache(String url, String result, Context context){
		
		PrefUtils.putString(url, result, context);
	}
}
