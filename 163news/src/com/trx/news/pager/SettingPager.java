package com.trx.news.pager;

import com.trx.news.R;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.trx.news.base.BasePager;

public class SettingPager extends BasePager{

	public SettingPager(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
	}
	
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		btnMenu.setVisibility(View.GONE);
		
		tvTitle.setText("设置");

		TextView tvContent = new TextView(mActivity);
		tvContent.setText("设置");
		flShow.addView(tvContent);
	}

}
