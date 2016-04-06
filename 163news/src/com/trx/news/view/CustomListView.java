package com.trx.news.view;

import com.trx.news.R;
import com.trx.news.utils.LogUtils;
import com.trx.news.utils.ToastUtils;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AbsListView.OnScrollListener;

public class CustomListView extends ListView implements OnScrollListener{

	// 尾布局
	public View mFootView;
	private int mFootMeasureHeight;

	boolean isLoadingMore = false;
	private RefreshListener mListener;

	public CustomListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initFooterView();
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initFooterView();
		// TODO Auto-generated constructor stub
	}

	public CustomListView(Context context) {
		super(context);
		initFooterView();
		// TODO Auto-generated constructor stub
	}

	public void initFooterView() {
		mFootView = View.inflate(getContext(), R.layout.list_foot_more, null);

		addFooterView(mFootView);

		mFootView.measure(0, 0);
		mFootMeasureHeight = mFootView.getMeasuredHeight();
		mFootView.setPadding(0, -mFootMeasureHeight, 0, 0);

		this.setOnScrollListener(this);
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (SCROLL_STATE_IDLE == scrollState) {
			int lastVisiblePosition = getLastVisiblePosition();
			if (lastVisiblePosition == getCount() - 1 && !isLoadingMore) {
				isLoadingMore = true;

				// 显示尾布局
				mFootView.setPadding(0, 0, 0, 0);

				setSelection(lastVisiblePosition + 1);

				if (null != mListener) {
					mListener.loadMore();
				}
			}
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

	}

	public void RefreshComplete() {
		if (isLoadingMore) {
			isLoadingMore = false;

			// 隐藏尾布局
			mFootView.setPadding(0, -mFootMeasureHeight, 0, 0);
		}
	}

	public void setOnRefreshListener(RefreshListener listener) {
		mListener = listener;
	}

	public interface RefreshListener {
		public void loadMore();
	}

	private OnItemClickListener mItemClickListener;

	@Override
	public void setOnItemClickListener(
			android.widget.AdapterView.OnItemClickListener listener) {
		// TODO Auto-generated method stub
		mItemClickListener = listener;
		
		super.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				mItemClickListener.onItemClick(parent, view, position
						- getHeaderViewsCount(), id);
			}
		});
	}


}
