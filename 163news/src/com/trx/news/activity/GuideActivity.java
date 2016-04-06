package com.trx.news.activity;

import java.util.ArrayList;

import com.trx.news.R;
import com.trx.news.utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class GuideActivity extends Activity {

	private ViewPager vpGuide;
	private Button btnGuide;
	private LinearLayout llContainer;
	private ImageView ivGuide;
	private int[] mImgIds = new int[] { R.drawable.guide_1, R.drawable.guide_2,
			R.drawable.guide_3 };
	private int mDis;
	private ArrayList<ImageView> mViewList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_guide);

		vpGuide = (ViewPager) findViewById(R.id.vp_guide);
		btnGuide = (Button) findViewById(R.id.btn_guide);
		llContainer = (LinearLayout) findViewById(R.id.ll_container);
		ivGuide = (ImageView) findViewById(R.id.iv_guide);

		vpGuide.setAdapter(new MyAdapter());
		vpGuide.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				// TODO Auto-generated method stub
				if (mImgIds.length - 1 == position) {
					btnGuide.setVisibility(View.VISIBLE);
				}else {
					btnGuide.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				// TODO Auto-generated method stub
//				System.out.println("position:" + position + " positionOffset:"
//						+ positionOffset);
				int width = (int) (positionOffset * mDis) + position * mDis;
				RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) ivGuide
						.getLayoutParams();
				params.leftMargin = width;
				ivGuide.setLayoutParams(params);
			}

			@Override
			public void onPageScrollStateChanged(int state) {
				// TODO Auto-generated method stub

			}
		});

		mViewList = new ArrayList<ImageView>();
		for (int i = 0; i < mImgIds.length; i++) {
			ImageView iView = new ImageView(getApplicationContext());
			iView.setBackgroundResource(mImgIds[i]);
			mViewList.add(iView);

			// 增加小圆点
			ImageView view = new ImageView(getApplicationContext());
			view.setBackgroundResource(R.drawable.shape_guide_normal);
			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);

			if (i > 0) {
				params.leftMargin = 10;
				view.setLayoutParams(params);
			}
			llContainer.addView(view);
		}

		// 计算小圆点之间的距离
		ivGuide.getViewTreeObserver().addOnGlobalLayoutListener(
				new OnGlobalLayoutListener() {

					@Override
					public void onGlobalLayout() {
						mDis = llContainer.getChildAt(1).getLeft()
								- llContainer.getChildAt(0).getLeft();
					}
				});
		
		btnGuide.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PrefUtils.putBoolean("is_guide", true, getApplicationContext());
				startActivity(new Intent(getApplicationContext(), MainActivity.class));
				finish();
			}
		});

	}

	class MyAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mImgIds.length;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return object == view;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			ImageView view = mViewList.get(position);
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
