package com.example.androidhw3;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.androidhw3.R;
import com.viewpagerindicator.CirclePageIndicator;

public class SplashActivity extends FragmentActivity {
	
	public static String[] studentNumbers = {"90109903", "9010XXXX", "9010YYYY"};
	AppSectionsPagerAdapter mAppSectionsPagerAdapter;
    ViewPager mViewPager;
    boolean killed = false;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);

		ActionBar actionBar = getActionBar();
		actionBar.hide();

		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.
		mAppSectionsPagerAdapter = new AppSectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager, attaching the adapter and setting up a listener for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mAppSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				// When swiping between different app sections, select the corresponding tab.
				// We can also use ActionBar.Tab#select() to do this if we have a reference to the
				// Tab.
			}
		});

		CirclePageIndicator titleIndicator = (CirclePageIndicator)findViewById(R.id.indicator);
		titleIndicator.setViewPager(mViewPager);

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				final Intent mainIntent = new Intent(SplashActivity.this, MainPageActivity.class);
				if(!SplashActivity.this.killed){
					SplashActivity.this.startActivity(mainIntent);
					SplashActivity.this.finish();
				}
			}
		}, 3000);
	}

	public static class AppSectionsPagerAdapter extends FragmentPagerAdapter {

		public AppSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new SplashFragment();
			Bundle args = new Bundle();
			args.putString(SplashFragment.STUDENT_NUMBER, studentNumbers[i]);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return studentNumbers.length;
		}
	}
	public void startButtonClickListener(View v){
		Intent intent = new Intent(this, MainPageActivity.class);
		startActivity(intent);
		killed = true;
		finish();
	}
}
