package com.trx.news.activity;

import com.trx.news.R;
import com.trx.news.utils.LogUtils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class NewsContentActivity extends Activity {

	private WebView wvNews;
	private ProgressBar pbLoad;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_news_content);
		
		String url = getIntent().getStringExtra("url");
		
		wvNews = (WebView)findViewById(R.id.wv_news);
		pbLoad = (ProgressBar)findViewById(R.id.pb_load);
		
		
		wvNews.getSettings().setJavaScriptEnabled(true);
		wvNews.loadUrl(url);
		
		wvNews.setWebViewClient(new WebViewClient(){

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				LogUtils.d(this, "webview start");
				pbLoad.setVisibility(View.VISIBLE);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				LogUtils.d(this, "webview finish");
				pbLoad.setVisibility(View.GONE);
			}

			@Override
			public void onLoadResource(WebView view, String url) {
				// TODO Auto-generated method stub
				LogUtils.d(this, "webview loading");
			}
			
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				view.loadUrl(url);
				
				return true;
			}
			
		});
		
		wvNews.setWebChromeClient(new WebChromeClient(){

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				LogUtils.d(this, "webview progress:"+newProgress);
			}
			
		});
		
	}
	
	public void back(View view){
		finish();
	}
	
	public void share(View view){
	}
}
