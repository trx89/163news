package com.trx.news.activity;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.trx.news.R;
import com.trx.news.fragment.ContentFragment;
import com.trx.news.fragment.MenuFragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewConfigurationCompat;


public class MainActivity extends SlidingFragmentActivity{
	public static final String TAG_CONTENT = "CONTENT_FRAGMENT";
	public static final String TAG_MENU = "MENU_FRAGMENT";
	public SlidingMenu menu;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
		setContentView(R.layout.activity_main);
		
		menu = getSlidingMenu();
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setMode(SlidingMenu.LEFT);
		menu.setBehindOffset(300);		
		setBehindContentView(R.layout.left_menu);
		
		initFragment();
	}
	
	public void initFragment(){
		FragmentManager fm = getSupportFragmentManager();
		FragmentTransaction transaction = fm.beginTransaction();
		transaction.replace(R.id.fl_content, new ContentFragment(), TAG_CONTENT);
		transaction.replace(R.id.fl_menu, new MenuFragment(), TAG_MENU);
		transaction.commit();
	
	}
}
