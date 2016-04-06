package com.trx.news.base;

import com.trx.news.domain.NewsMenuData;

import android.app.Activity;
import android.view.View;

public abstract class BaseMenuDetailPager {

	public Activity mActivity;
	public View mRootView;
	
	public BaseMenuDetailPager(Activity activity) {
		// TODO Auto-generated constructor stub
		mActivity = activity;
		mRootView = initView();
	}
	
	public abstract View initView();
	
	public void initData(){
		
	}
}
