package com.trx.news.activity;

import com.trx.news.R;
import com.trx.news.utils.PrefUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class SplashActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_splash);

		LinearLayout llSplash = (LinearLayout) findViewById(R.id.ll_splash);
		Animation animation = AnimationUtils.loadAnimation(
				getApplicationContext(), R.anim.anim_splash);
		llSplash.startAnimation(animation);

		animation.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				boolean isGuide = PrefUtils.getBoolean("is_guide", false,
						getApplicationContext());
				if (!isGuide){
					startActivity(new Intent(getApplicationContext(),
						GuideActivity.class));
					finish();
				}else{
					startActivity(new Intent(getApplicationContext(),
							MainActivity.class));
						finish();
				}
			}
		});
	}

}
