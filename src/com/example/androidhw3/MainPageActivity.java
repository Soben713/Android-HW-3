package com.example.androidhw3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.viewpagerindicator.CirclePageIndicator;

public class MainPageActivity extends FragmentActivity {
	CalSectionsPagerAdapter calSectionsPagerAdapter;
	ViewPager mViewPager;
	public static final int MAX_DAYS = 365*100;
	int current_page = (int)(MAX_DAYS/2);
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    setContentView(R.layout.mainpage_activity);

		// Create the adapter that will return a fragment for each of the three primary sections
		// of the app.
		calSectionsPagerAdapter = new CalSectionsPagerAdapter(getSupportFragmentManager());

		// Set up the ViewPager, attaching the adapter and setting up a listener for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(calSectionsPagerAdapter);
		mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
			}
		});
		// Should be changed to current date's integer
		mViewPager.setCurrentItem(current_page);
	}

	public static class CalSectionsPagerAdapter extends FragmentStatePagerAdapter {

		public CalSectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int i) {
			Fragment fragment = new MainPageFragment();
			Bundle args = new Bundle();
			args.putString(MainPageFragment.DATE, Integer.toString(i));
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			return MAX_DAYS;
		}
	}
	
	public void moveToPrevFragment(View view){
		current_page--;
		mViewPager.setCurrentItem(current_page);
	}
	
	public void moveToNextFragment(View view){
		current_page++;
		mViewPager.setCurrentItem(current_page);
	}
}
