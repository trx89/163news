package com.trx.news.pager;

import java.util.ArrayList;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.trx.news.R;
import com.trx.news.activity.MainActivity;
import com.trx.news.base.BaseMenuDetailPager;
import com.trx.news.base.BasePager;
import com.trx.news.domain.NewsMenuData;
import com.trx.news.domain.NewsMenuData.NewsMenuTabData;
import com.trx.news.fragment.MenuFragment;
import com.viewpagerindicator.TabPageIndicator;

public class NewsMenuDetailPager extends BaseMenuDetailPager {

	@ViewInject(R.id.vp_news_menu_detail)
	private ViewPager vpShow;

	@ViewInject(R.id.indicator)
	private TabPageIndicator indicator;

	private ArrayList<NewsMenuTabData> mDataList;
	private ArrayList<NewsTabDetaiPager> mPagerList;

	private MyAdapter mAdapter;

	public NewsMenuDetailPager(Activity activity,
			ArrayList<NewsMenuTabData> dataList) {
		// TODO Auto-generated constructor stub
		super(activity);
		mDataList = dataList;
	}

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(mActivity, R.layout.pager_menu_detail_news,
				null);
		ViewUtils.inject(this, view);

		return view;
	}

	public void initData() {
		// TODO Auto-generated method stub

		// 初始化页签
		mPagerList = new ArrayList<NewsTabDetaiPager>();
		for (NewsMenuTabData data : mDataList){
			NewsTabDetaiPager pager = new NewsTabDetaiPager(mActivity, data.url);
			
			mPagerList.add(pager);
		}

		mAdapter = new MyAdapter();
		vpShow.setAdapter(mAdapter);

		// 绑定ViewPagerIndicator
		indicator.setViewPager(vpShow);

		indicator.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if (0 == position) {
					// 可以划出侧边栏
					setLeftMenuEnable(true);
				} else {
					// 禁止划出侧边栏
					setLeftMenuEnable(false);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});

	}

	private void setLeftMenuEnable(boolean enable) {
		MainActivity mainActivity = (MainActivity) mActivity;
		SlidingMenu menu = mainActivity.menu;

		if (enable) {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		} else {
			menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
		}
	}
	
	class MyAdapter extends PagerAdapter {

		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return mDataList.get(position).title;
		}
		
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPagerList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return object == view;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			NewsTabDetaiPager pager = mPagerList.get(position);
			
			View view = pager.mRootView;
			
			container.addView(view);
			
			pager.initData();

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub

			container.removeView((View) object);
		}
	}


}
