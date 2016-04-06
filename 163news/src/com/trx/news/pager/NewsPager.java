package com.trx.news.pager;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.trx.news.R;

import android.R.integer;
import android.app.Activity;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.trx.news.activity.MainActivity;
import com.trx.news.base.BaseMenuDetailPager;
import com.trx.news.base.BasePager;
import com.trx.news.domain.NewsMenuData;
import com.trx.news.fragment.MenuFragment;
import com.trx.news.global.Constant;
import com.trx.news.utils.CacheUtils;
import com.trx.news.utils.LogUtils;
import com.trx.news.utils.PrefUtils;
import com.trx.news.utils.ToastUtils;

public class NewsPager extends BasePager {

	private NewsMenuData mData;
	private ArrayList<BaseMenuDetailPager> mPagers;

	public NewsPager(Activity activity) {
		// TODO Auto-generated constructor stub
		super(activity);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		tvTitle.setText("新闻中心");

		String result = CacheUtils.getCache(Constant.URL.CATEGORIES_JSON,
				mActivity);
		if (!TextUtils.isEmpty(result)) {
			processJson(result);
			LogUtils.d(this, "发现缓存");
		}

		getDataFromServer();

		btnMenu.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				toggle();
			}
		});
	}

	private void toggle() {
		MainActivity mainActivity = (MainActivity) mActivity;
		mainActivity.menu.toggle();
	}

	private void getDataFromServer() {
		HttpUtils hUtils = new HttpUtils();
		hUtils.send(HttpMethod.GET, Constant.URL.CATEGORIES_JSON,
				new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException error, String msg) {
						// TODO Auto-generated method stub
						error.printStackTrace();
						ToastUtils.showToast(mActivity, msg);
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						// TODO Auto-generated method stub
						String result = responseInfo.result;
						// System.out.println("result:"+result);

						processJson(result);

						// 将数据缓存起来，保存在SharedPreferences中
						CacheUtils.setCache(Constant.URL.CATEGORIES_JSON,
								result, mActivity);

					}

				});
	}

	private void processJson(String result) {
		Gson gson = new Gson();
		mData = gson.fromJson(result, NewsMenuData.class);
		// System.out.println(mData);

		MainActivity mainActivity = (MainActivity) mActivity;
		FragmentManager fm = mainActivity.getSupportFragmentManager();

		MenuFragment menuFragment = (MenuFragment) fm
				.findFragmentByTag(mainActivity.TAG_MENU);
		menuFragment.setData(mData);

		// 初始化页面
		mPagers = new ArrayList<BaseMenuDetailPager>();
		mPagers.add(new NewsMenuDetailPager(mActivity,
				mData.data.get(0).children));
		mPagers.add(new TopicMenuDetailPager(mActivity));
		mPagers.add(new ImageMenuDetailPager(mActivity));
		mPagers.add(new InteractMenuDetailPager(mActivity));

		setCurrentMenuDetailPager(0);
	}

	public void setCurrentMenuDetailPager(int position) {
		BaseMenuDetailPager pager = mPagers.get(position);
		pager.initData();

		flShow.removeAllViews();
		flShow.addView(pager.mRootView);

		tvTitle.setText(mData.data.get(position).title);

	}

}
