package com.trx.news.base;

import com.trx.news.R;

import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

public abstract class BasePager {

	public Activity mActivity;
	public View mRootView;
	public TextView tvTitle;
	public ImageButton btnMenu;
	public FrameLayout flShow;
	
	public BasePager(Activity activity) {
		// TODO Auto-generated constructor stub
		mActivity = activity;
		initView();
	}
	
	public void initView() {
		// TODO Auto-generated method stub
		mRootView = View.inflate(mActivity, R.layout.base_pager, null);
		
		tvTitle = (TextView)mRootView.findViewById(R.id.tv_title);
		btnMenu = (ImageButton)mRootView.findViewById(R.id.btn_menu);
		flShow = (FrameLayout)mRootView.findViewById(R.id.fl_show);

	}
	
	public abstract void initData();
}
