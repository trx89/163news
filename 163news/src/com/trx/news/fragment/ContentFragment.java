package com.trx.news.fragment;

import java.util.ArrayList;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.trx.news.R;
import com.trx.news.base.BaseFragment;
import com.trx.news.base.BasePager;
import com.trx.news.pager.GovPager;
import com.trx.news.pager.HomePager;
import com.trx.news.pager.NewsPager;
import com.trx.news.pager.SettingPager;
import com.trx.news.pager.SmartPager;
import com.trx.news.view.NoScrollViewPager;

public class ContentFragment extends BaseFragment{

	@ViewInject(R.id.vp_content) 
	private NoScrollViewPager vpContent;
	
	@ViewInject(R.id.rg_bottom)
	private RadioGroup rgBottom;
	
	
	public ArrayList<BasePager> mPagers;
	
	@Override
	public View initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(mActivity, R.layout.fragment_content, null);
		ViewUtils.inject(this, view);
				
		return view;
	}
	
	public void initData(){
		mPagers = new ArrayList<BasePager>();
		
		mPagers.add(new HomePager(mActivity));
		mPagers.add(new NewsPager(mActivity));
		mPagers.add(new SmartPager(mActivity));
		mPagers.add(new GovPager(mActivity));
		mPagers.add(new SettingPager(mActivity));
		
		vpContent.setAdapter(new MyAdapter());
		
		rgBottom.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				switch (checkedId) {
				case R.id.btn_home:
					vpContent.setCurrentItem(0, false);
					mPagers.get(0).initData();
					break;
				case R.id.btn_news:
					vpContent.setCurrentItem(1, false);
					mPagers.get(1).initData();
					break;
				case R.id.btn_smart:
					vpContent.setCurrentItem(2, false);
					mPagers.get(2).initData();
					break;
				case R.id.btn_gov:
					vpContent.setCurrentItem(3, false);
					mPagers.get(3).initData();
					break;
				case R.id.btn_setting:
					vpContent.setCurrentItem(4, false);
					mPagers.get(4).initData();
					break;
				default:
					break;
				}
			}
		});
	}
	
	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mPagers.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return object == view;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			BasePager pager = mPagers.get(position);
			
			View view = pager.mRootView;
			
			container.addView(view);

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub

			container.removeView((View) object);
		}
	}

}
