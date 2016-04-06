package com.trx.news.pager;

import com.trx.news.R;
import android.app.Activity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.trx.news.base.BasePager;

public class GovPager extends BasePager{

	public GovPager(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("政务");

		TextView tvContent = new TextView(mActivity);
		tvContent.setText("政务");
		flShow.addView(tvContent);
	}
	
	

}
