package com.trx.news.pager;

import com.trx.news.R;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.trx.news.base.BasePager;

public class SmartPager extends BasePager{

	public SmartPager(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
	}
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("智慧服务");

		TextView tvContent = new TextView(mActivity);
		tvContent.setText("智慧服务");
		flShow.addView(tvContent);
	}

}
