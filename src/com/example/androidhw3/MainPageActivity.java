package com.example.androidhw3;

import static com.orm.SugarRecord.save;

import java.util.Date;
import java.util.List;
import java.util.Random;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.androidhw3.db_entities.Cost;
import com.orm.SugarRecord;

public class MainPageActivity extends FragmentActivity {
	CalSectionsPagerAdapter calSectionsPagerAdapter;
	ViewPager mViewPager;
	public static final int MAX_DAYS = 365 * 100;
	int current_page = (int) (MAX_DAYS / 2);

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_actions, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_std1:
	        	Toast.makeText(getApplicationContext(), "90109903", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.action_std2:
	        	Toast.makeText(getApplicationContext(), "9010XXXX", Toast.LENGTH_SHORT).show();
	            return true;
	        case R.id.action_std3:
	        	Toast.makeText(getApplicationContext(), "9010YYYY", Toast.LENGTH_SHORT).show();
	        	return true;
	    }
	    return true;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// Create the adapter that will return a fragment for each of the three
		// primary sections
		// of the app.
		calSectionsPagerAdapter = new CalSectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager, attaching the adapter and setting up a listener
		// for when the
		// user swipes between sections.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(calSectionsPagerAdapter);
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
					}
				});
		// Should be changed to current date's integer
		mViewPager.setCurrentItem(current_page);
	}
	
	public static class CalSectionsPagerAdapter extends
			FragmentStatePagerAdapter {

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

	public void moveToPrevFragment(View view) {
		current_page--;
		mViewPager.setCurrentItem(current_page);
	}

	public void moveToNextFragment(View view) {
		current_page++;
		mViewPager.setCurrentItem(current_page);
	}
}
