package com.trx.news.fragment;

import java.util.ArrayList;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.trx.news.R;
import com.trx.news.activity.MainActivity;
import com.trx.news.base.BaseFragment;
import com.trx.news.base.BasePager;
import com.trx.news.domain.NewsMenuData;
import com.trx.news.domain.NewsMenuData.NewsMenuDetailData;
import com.trx.news.pager.GovPager;
import com.trx.news.pager.HomePager;
import com.trx.news.pager.NewsPager;
import com.trx.news.pager.SettingPager;
import com.trx.news.pager.SmartPager;
import com.trx.news.utils.LogUtils;

public class MenuFragment extends BaseFragment {

	@ViewInject(R.id.lv_menu)
	private ListView lvMenu;

	private NewsMenuData mData;

	private MyAdapter mAdapter;
	private int mCurrentPos = 0;

	@Override
	public View initView() {
		// TODO Auto-generated method stub
		View view = View.inflate(mActivity, R.layout.fragment_menu, null);

		ViewUtils.inject(this, view);

		return view;
	}

	// 给leftmenu设置数据
	public void setData(NewsMenuData data) {
		mData = data;

		if (null == mAdapter) {
			mAdapter = new MyAdapter();
			lvMenu.setAdapter(mAdapter);
		} else {
			mAdapter.notifyDataSetChanged();
		}

		lvMenu.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				
				//刷新页面,更新文字颜色
				mCurrentPos = position;
				mAdapter.notifyDataSetChanged();
				
				//切换页面
				setNewsMenuDetailPager(position);
				
				//关闭侧边栏
				toggle();
			}

		});
		
		mCurrentPos = 0;
	}

	private void setNewsMenuDetailPager(int position){
		MainActivity mainActivity = (MainActivity)mActivity;
		FragmentManager fm = mainActivity.getSupportFragmentManager();
		
		ContentFragment contentFragment = (ContentFragment) fm
				.findFragmentByTag(mainActivity.TAG_CONTENT);
		
		NewsPager pager = (NewsPager) contentFragment.mPagers.get(1);
		pager.setCurrentMenuDetailPager(position);
		
	}
	
	private void toggle(){
		MainActivity mainActivity = (MainActivity)mActivity;
		mainActivity.menu.toggle();
	}
	
	class MyAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mData.data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mData.data.get(position).title;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view = View.inflate(mActivity, R.layout.list_item_menu, null);

			String text = (String) getItem(position);
			TextView tvMenu = (TextView) view.findViewById(R.id.tv_menu);
			tvMenu.setText(text);

			if (mCurrentPos == position) {
				tvMenu.setEnabled(true);
			}else{
				tvMenu.setEnabled(false);
			}
			
			return view;
		}

	}

}
