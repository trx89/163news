package com.trx.news.pager;

import java.util.ArrayList;

import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.trx.news.R;
import com.trx.news.activity.MainActivity;
import com.trx.news.activity.NewsContentActivity;
import com.trx.news.base.BaseMenuDetailPager;
import com.trx.news.base.BasePager;
import com.trx.news.domain.NewsMenuData;
import com.trx.news.domain.NewsTabData;
import com.trx.news.domain.NewsTabData.NewsData;
import com.trx.news.domain.NewsTabData.NewsNormal;
import com.trx.news.domain.NewsTabData.NewsTop;
import com.trx.news.fragment.MenuFragment;
import com.trx.news.global.Constant;
import com.trx.news.utils.CacheUtils;
import com.trx.news.utils.DestinyUtils;
import com.trx.news.utils.LogUtils;
import com.trx.news.utils.PrefUtils;
import com.trx.news.utils.ToastUtils;
import com.trx.news.view.CustomListView;
import com.trx.news.view.CustomListView.RefreshListener;
import com.viewpagerindicator.CirclePageIndicator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.ListView;

public class NewsTabDetaiPager {

	public Activity mActivity;
	public View mRootView;
	public String mUrl;
	public NewsTabData mData;

	private ArrayList<NewsTop> mTopList;
	private ArrayList<NewsNormal> mNormalList;
	private BitmapUtils mBitmapUtils;

	private ViewPager vpNewsTab;
	private CustomListView lvNewsTab;
	private CirclePageIndicator indicator;
	private TopNewsAdapter mTopAdapter;
	private TextView tvTopNews;
	private NewsAdapter mNewsAdapter;
	private String mMoreUrl;
	
	private Handler mHandler;

	public NewsTabDetaiPager(Activity activity, String url) {
		// TODO Auto-generated constructor stub
		mActivity = activity;

		mBitmapUtils = new BitmapUtils(mActivity);
		mBitmapUtils.configDefaultLoadingImage(R.drawable.topnews_item_default);

		mUrl = Constant.URL.SERVER_URL + url;

		initView();
	}

	public void initView() {
		mRootView = View.inflate(mActivity, R.layout.pager_news_tab, null);
		lvNewsTab = (CustomListView) mRootView.findViewById(R.id.lv_news);

		View header = View.inflate(mActivity, R.layout.list_head_topnews, null);
		lvNewsTab.addHeaderView(header);

		vpNewsTab = (ViewPager) header.findViewById(R.id.vp_news_tab);
		tvTopNews = (TextView) header.findViewById(R.id.tv_top_news);
		indicator = (CirclePageIndicator) header.findViewById(R.id.indicator);

		lvNewsTab.setOnRefreshListener(new RefreshListener() {
			
			@Override
			public void loadMore() {
				// TODO Auto-generated method stub
				if (null != mMoreUrl){
					getMoreDataFromServer();
				}else{
					lvNewsTab.RefreshComplete();
					ToastUtils.showToast(mActivity, "没有更多数据");
				}
			}
		});
		
		lvNewsTab.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
				NewsNormal news = mNormalList.get(position);
				//标记新闻已读		
				String tagReadStr = PrefUtils.getString("tagRead", "", mActivity);
				if (!tagReadStr.contains(news.id)){
					TextView tvText = (TextView) view.findViewById(R.id.tv_text);
					tvText.setTextColor(Color.GRAY);
					tagReadStr += news.id + ",";
					PrefUtils.putString("tagRead", tagReadStr, mActivity);
				}
				
				// TODO Auto-generated method stub
				Intent intent = new Intent(mActivity, NewsContentActivity.class);
				intent.putExtra("url", news.url);
				mActivity.startActivity(intent);
			}
			
		});
	}

	public void initData() {
		String result = CacheUtils.getCache(mUrl, mActivity);
		if (!TextUtils.isEmpty(result)) {
			LogUtils.d(this, "发现缓存");
			processJson(result, false);
		}

		getDataFromServer();
	}

	private void getDataFromServer() {
		HttpUtils hUtils = new HttpUtils();
		hUtils.send(HttpMethod.GET, mUrl, new RequestCallBack<String>() {

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
				// LogUtils.d(this, "result:"+result);

				processJson(result, false);

				// 将数据缓存起来，保存在SharedPreferences中
				CacheUtils.setCache(mUrl, result, mActivity);

			}

		});
	}

	private void processJson(String result, boolean isMore) {
		Gson gson = new Gson();
		mData = gson.fromJson(result, NewsTabData.class);
		// LogUtils.d(this, "处理json数据："+mData.data.title);

		String more = mData.data.more;
		if (!TextUtils.isEmpty(more)) {
			// 下一页的url
			mMoreUrl = Constant.URL.SERVER_URL + more;
		}else {
			mMoreUrl = null;
		}
		
		if (!isMore) {
			// 初始化头部图片
			mTopList = mData.data.topnews;

			if (null != mTopList) {
				mTopAdapter = new TopNewsAdapter();
				vpNewsTab.setAdapter(mTopAdapter);
				indicator.setViewPager(vpNewsTab);

				//图片轮播
				if (null == mHandler){
					mHandler = new Handler(){
						@Override
						public void handleMessage(Message msg) {
							// TODO Auto-generated method stub
							int currentItem = vpNewsTab.getCurrentItem();
							if (currentItem < mTopAdapter.getCount() - 1){
								currentItem++;
							}else{
								currentItem = 0;
							}
							indicator.setCurrentItem(currentItem);
							
							mHandler.sendEmptyMessageDelayed(0, 2000);
						}
					};
					
					mHandler.sendEmptyMessageDelayed(0, 2000);
				}
				
				vpNewsTab.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						switch (event.getAction()) {
						case MotionEvent.ACTION_DOWN:
							//停止轮播
							mHandler.removeCallbacksAndMessages(null);
							break;
						case MotionEvent.ACTION_UP:
							// 启动轮播
							mHandler.sendEmptyMessageDelayed(0, 2000);
							break;
							
						default:
							break;
						}
						
						return false;
					}
				});
				
				indicator.setOnPageChangeListener(new OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						// TODO Auto-generated method stub
						LogUtils.d(this, "postion:" + position);
						tvTopNews.setText(mTopList.get(position).title);
					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
						// TODO Auto-generated method stub

					}
				});

				// indicator.onPageSelected(0);
				// tvTopNews.setText(mTopList.get(0).title);
			}

			// 初始化新闻列表
			
			mNormalList = mData.data.news;
			if (null != mNormalList) {
				mNewsAdapter = new NewsAdapter();
				lvNewsTab.setAdapter(mNewsAdapter);

			}
		}else {
			//处理更多新闻数据
			ArrayList<NewsNormal> moreList = mData.data.news;
			if (null != moreList){
				mNormalList.addAll(moreList);
				mNewsAdapter.notifyDataSetChanged();
			}
		}
	}

	private void getMoreDataFromServer() {
		HttpUtils hUtils = new HttpUtils();
		hUtils.send(HttpMethod.GET, mMoreUrl, new RequestCallBack<String>() {

			@Override
			public void onFailure(HttpException error, String msg) {
				// TODO Auto-generated method stub
				lvNewsTab.RefreshComplete();
				error.printStackTrace();
				ToastUtils.showToast(mActivity, msg);
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				// TODO Auto-generated method stub
				String result = responseInfo.result;
				// LogUtils.d(this, "result:"+result);

				processJson(result, true);
				
				lvNewsTab.RefreshComplete();
			}

		});
	}

	class TopNewsAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mTopList.size();
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return object == view;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			ImageView view = new ImageView(mActivity);
			view.setScaleType(ScaleType.FIT_XY);

			mBitmapUtils.display(view, mTopList.get(position).topimage);

			container.addView(view);

			return view;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub

			container.removeView((View) object);
		}
	}

	class NewsAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mNormalList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mNormalList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View view;
			ViewHolder holder;
			if (null == convertView) {
				view = View.inflate(mActivity, R.layout.list_item_news_tab,
						null);
				holder = new ViewHolder();
				holder.ivNews = (ImageView) view.findViewById(R.id.iv_news);
				holder.tvText = (TextView) view.findViewById(R.id.tv_text);
				holder.tvDate = (TextView) view.findViewById(R.id.tv_date);
				view.setTag(holder);
			} else {
				view = convertView;
				holder = (ViewHolder) view.getTag();
			}

			mBitmapUtils.display(holder.ivNews,
					mNormalList.get(position).listimage);
			
			holder.tvText.setText(mNormalList.get(position).title);
			
			String tagReadStr = PrefUtils.getString("tagRead", "", mActivity);
			if (tagReadStr.contains(mNormalList.get(position).id)){
				holder.tvText.setTextColor(Color.GRAY);
			}
			holder.tvDate.setText(mNormalList.get(position).pubdate);

			return view;
		}
	}

	class ViewHolder {
		ImageView ivNews;
		TextView tvText;
		TextView tvDate;
	}

}
