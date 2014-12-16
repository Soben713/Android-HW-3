package com.example.androidhw3;

import static com.orm.SugarRecord.save;

import java.util.Date;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.androidhw3.db_entities.Cost;
import com.example.androidhw3.solarCalendar.CalendarTool;
import com.orm.query.Condition;
import com.orm.query.Select;

public class CalendarActivity extends FragmentActivity {

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private CalendarPagerAdapter mCalendarPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	/**
	 * reshape calendar to middle point set current item to middle
	 */
	private void reshapePagerAdapterToMiddle() {
		mCalendarPagerAdapter.reshapeCalendarToMiddle(mViewPager
				.getCurrentItem());
		mViewPager.setCurrentItem(CalendarPagerAdapter.MIDPOINT);
	}

	public void refreshFragment() {
		mViewPager.invalidate();
		mViewPager.requestLayout();
		mCalendarPagerAdapter.notifyDataSetChanged();
	}

	public void pageReselect() {
		mCalendarPagerAdapter.makeDirty();
		mViewPager.setCurrentItem(mViewPager.getCurrentItem());
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_calendar);

		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mCalendarPagerAdapter = new CalendarPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mCalendarPagerAdapter);

		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					@Override
					public void onPageSelected(int arg0) {
						System.err.println("# page " + arg0 + " is selected");
						if (mCalendarPagerAdapter.isReshapeNeeded(mViewPager
								.getCurrentItem())) {
							reshapePagerAdapterToMiddle();
						}
						CalendarActivity.CalendarFragment cf = (CalendarActivity.CalendarFragment) mCalendarPagerAdapter
								.getItem(mViewPager.getCurrentItem());
						cf.informDataPercent();

					}

					@Override
					public void onPageScrolled(int arg0, float arg1, int arg2) {
					}

					@Override
					public void onPageScrollStateChanged(int arg0) {
					}
				});

		mViewPager.setCurrentItem(CalendarPagerAdapter.MIDPOINT);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu items for use in the action bar
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.calendar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_std1:
			Toast.makeText(getApplicationContext(), "90109903",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_std2:
			Toast.makeText(getApplicationContext(), "90109993",
					Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_std3:
			Toast.makeText(getApplicationContext(), "9010YYYY",
					Toast.LENGTH_SHORT).show();
			return true;
		}
		return true;
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class CalendarPagerAdapter extends FragmentStatePagerAdapter {
		private static final int MIDPOINT = 100;
		// MIDPOINTmust be greater than MIDPOINTMARGIN + 1
		private static final int MIDPOINTMARGIN = 90;

		private CalendarTool currentCalendar;
		private CalendarFragment calendarFragmentsRef[];
		private boolean isDirty;

		public void makeDirty() {
			isDirty = true;
		}

		public CalendarPagerAdapter(FragmentManager fm) {
			super(fm);
			currentCalendar = new CalendarTool();
			calendarFragmentsRef = new CalendarFragment[MIDPOINT
					+ MIDPOINTMARGIN + 2];
			isDirty = false;
		}

		public boolean isReshapeNeeded(int position) {
			return (position >= MIDPOINT + MIDPOINTMARGIN || position <= MIDPOINT
					- MIDPOINTMARGIN);
		}

		public void reshapeCalendarToMiddle(int currentPosition) {
			currentCalendar.nextDay(currentPosition - MIDPOINT);
			for (int i = 0; i < calendarFragmentsRef.length; i++)
				calendarFragmentsRef[i] = null;
		}

		private void clearFarRefrences(int position) {
			calendarFragmentsRef[position - 5] = calendarFragmentsRef[position - 6] = null;
			calendarFragmentsRef[position + 5] = calendarFragmentsRef[position + 6] = null;
		}

		public CalendarTool getTodayCalendar(int position) {
			CalendarTool cal = new CalendarTool(currentCalendar);
			cal.nextDay(position - MIDPOINT);
			return cal;
		}

		@Override
		public Fragment getItem(int position) {
			System.err.println("!!!! CREATE ITEM POSITION : " + position);
			clearFarRefrences(position);
			if (calendarFragmentsRef[position] == null)
				calendarFragmentsRef[position] = new CalendarFragment(
						getTodayCalendar(position));
			return calendarFragmentsRef[position];
		}

		@Override
		public int getItemPosition(Object object) {
			// if(isDirty){
			// isDirty = false;
			// return POSITION_NONE;
			// }
			return super.getItemPosition(object);
		}

		@Override
		public int getCount() {
			return MIDPOINT + MIDPOINTMARGIN + 2;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return getTodayCalendar(position).getIranianDateStr();
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class CalendarFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		public static Random rand = new Random(13);
		public static String saveCalendarToolName = "save_calendar_tool_name";
		private CalendarTool calendarTool;
		private int costOfDay, incomeOfDay;
		private CalendarActivity calendarActivity;

		private void percentDataChanged() {
			informDataPercent();
			if (calendarActivity != null)
				calendarActivity.pageReselect();
			System.err.println("$$ CHANGE OF PERCENT DATA ");

		}

		private void loadDataFromDB() {
			if (calendarTool == null)
				calendarTool = new CalendarTool();
			System.out.println("load data for " + calendarTool.getDate());
			Log.d("db", "loading, hash is:" + calendarTool.getDate() + " " + calendarTool.getDate().hashCode());
			// TODO load [calendarTool.getDate(), costOfDay, incomeOfDay] from
			// DB
			int date = calendarTool.getDate().hashCode();
			Condition[] conditions = new Condition[] { new Condition("date")
					.eq(date) };
			List<Cost> costs = Select.from(Cost.class).where(conditions).list();
			
			Log.d("db", "size of costs:" + Integer.toString(costs.size()));
			Log.d("db", "date is" + Integer.toString(date) + " " + calendarTool);
			
			if (costs.size() > 0) {
				costOfDay = costs.get(0).getCost();
				incomeOfDay = costs.get(0).getIncome();
			}

			// Cost cost = null;
			// retrive cost for date = calendarTool.getDate()

			// if(cost != null){
			// costOfDay = cost.getCost();
			// incomeOfDay = cost.isIncome();
			// }
			percentDataChanged();
		}

		private void saveDataOnDB() {
			if (calendarTool == null)
				calendarTool = new CalendarTool();
			System.out.println("save data for " + calendarTool.getDate());

			Cost cost = new Cost(calendarTool.getDate().hashCode(), costOfDay, incomeOfDay);
			save(cost);
			Log.d("db", "saving:" + costOfDay + " " + incomeOfDay + " " + calendarTool);
			Log.d("db", "saving, hash is:" + calendarTool.getDate() + " " + calendarTool.getDate().hashCode());
		}

		public void setCostOfDay(int costOfDay) {
			this.costOfDay = costOfDay;
			percentDataChanged();
			saveDataOnDB();
		}

		public void setIncomeOfDay(int incomeOfDay) {
			this.incomeOfDay = incomeOfDay;
			percentDataChanged();
			saveDataOnDB();
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			calendarActivity = (CalendarActivity) activity;
		}

		public void informDataPercent() {
			// System.err.println(" $$ #ST INFORM DATA PERCENT : " + incomeOfDay
			// + " -" + costOfDay + " @" + calendarTool+ " ::" + this );

			// costOfDay += rand.nextInt(100); incomeOfDay += rand.nextInt(100);
			int percent = 0;
			if (costOfDay > incomeOfDay) {
				if (incomeOfDay == 0)
					percent = 100;
				else
					percent = (100 * incomeOfDay) / costOfDay;
				percent *= -1;
			} else {
				if (costOfDay == 0)
					percent = 0;
				else
					percent = (100 * costOfDay) / incomeOfDay;
			}

			// System.err.println(" $$ #ST INFORM DATA PERCENT : " + incomeOfDay
			// + " -" + costOfDay + " $" +calendarTool+ " ::" + this +
			// "   percent: " + percent);

			if (getView() != null) {
				// System.err.println(" @@ MY getView : " + getView());
				System.err.println(getView().findViewById(
						R.id.calendar_circular_percent));
				CircularPercentView cpv = (CircularPercentView) getView()
						.findViewById(R.id.calendar_circular_percent);
				cpv.animatePercentTo(percent);
				if (calendarActivity != null)
					calendarActivity.refreshFragment();

			}
			// else
			// System.err.println(" @@ MY ACTIVITY WAS NULL");
		}

		public CalendarFragment(CalendarTool calendarTool) {
			super();
			this.calendarTool = calendarTool;
			if (this.calendarTool == null)
				this.calendarTool = new CalendarTool();
		}

		public CalendarFragment() {
			this(null);
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_calendar,
					container, false);

			// String date_string = calendarTool.getIranianDateStr();
			// ((TextView)
			// rootView.findViewById(R.id.calendar_fragment_date_text)).setText(date_string);
			// System.err.println("create calendar page : " + date_string);

			costOfDay = incomeOfDay = 0;
			loadDataFromDB();
			// System.err.println(" $$ CAL_FRAG CONSTRUCTOR " + incomeOfDay +
			// " -" + costOfDay + " $" + calendarTool+ " ::" + this);
			return rootView;
		}

		@Override
		public void onResume() {
			super.onResume();
			((Button) getView().findViewById(
					R.id.calendar_cost_input_ring_button))
					.setOnClickListener(new onInputValueButtonClicked(
							getResources().getString(R.string.cost_of_day),
							true));
			((Button) getView().findViewById(
					R.id.calendar_income_input_ring_button))
					.setOnClickListener(new onInputValueButtonClicked(
							getResources().getString(R.string.income_of_day),
							false));
		}

		class onInputValueButtonClicked implements OnClickListener {
			private int submitedValue;
			private InputValueDialog dialog;
			private boolean isCost;

			public onInputValueButtonClicked(String messageString,
					boolean isCost) {
				submitedValue = 0;
				dialog = new InputValueDialog(messageString);
				this.isCost = isCost;
				// System.err.println("CREATE ONIVBC : " + messageString + " " +
				// isCost);
			}

			public onInputValueButtonClicked() {
				this("", false);
			}

			public class InputValueDialog extends DialogFragment {
				private String messageStr;
				private EditText editText;

				public InputValueDialog(String messageStr) {
					super();
					this.messageStr = messageStr;
				}

				public InputValueDialog() {
					this("");
				}

				@Override
				public Dialog onCreateDialog(Bundle savedInstanceState) {
					editText = new EditText(getActivity());
					editText.setInputType(InputType.TYPE_CLASS_NUMBER
							| InputType.TYPE_NUMBER_FLAG_DECIMAL);

					// Use the Builder class for convenient dialog construction
					AlertDialog.Builder builder = new AlertDialog.Builder(
							getActivity());
					builder.setTitle(messageStr)
							.setView(editText)
							.setPositiveButton(R.string.submit,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											System.out
													.println("! DIALOG SUBMITED : "
															+ messageStr);

											// System.err.println("USE ONIVBC : "
											// + messageStr + " " + isCost);
											try {
												submitedValue = Integer
														.parseInt(editText
																.getText()
																.toString());
												if (submitedValue >= 0) {
													if (isCost) {
														System.err
																.println(" ^^ COST OF DAY -> "
																		+ submitedValue);
														setCostOfDay(submitedValue);
													} else {
														System.err
																.println(" ^^ INCOME OF DAY -> "
																		+ submitedValue);
														setIncomeOfDay(submitedValue);

													}
												}
											} catch (Exception e) {
												submitedValue = 0;
											}

										}
									})
							.setNegativeButton(R.string.cancel,
									new DialogInterface.OnClickListener() {
										public void onClick(
												DialogInterface dialog, int id) {
											System.out
													.println("! DIALOG CANCELED");
										}
									});
					// Create the AlertDialog object and return it
					return builder.create();
				}
			}

			@Override
			public void onClick(View v) {
				System.err.println("button clicked");
				dialog.show(getActivity().getFragmentManager(), "dialogInput");

			}

		}
	}

}
