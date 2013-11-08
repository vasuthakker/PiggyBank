package com.viral.piggybank.activities;

import com.viral.piggybank.R;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.fragments.ActivitiesFragment;
import com.viral.piggybank.fragments.BillListFragment;
import com.viral.piggybank.fragments.MainFragment;
import com.viral.piggybank.fragments.SettingFragment;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;

public class HomeActivity extends FragmentActivity implements
		ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_piggy_bank);

		SharedPreferences preference = this.getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		if (preference.getString(AppConstants.ACCOUNT, null) == null
				|| preference.getString(AppConstants.ACCOUNT, null).isEmpty())
		{
			finish();
		} else
		{
			// Set up the action bar.
			final ActionBar actionBar = getActionBar();
			actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
			actionBar.setTitle("");
			actionBar.setDisplayShowTitleEnabled(false);
			actionBar.setDisplayShowHomeEnabled(false);

			// Create the adapter that will return a fragment for each of the
			// three
			// primary sections of the app.
			mSectionsPagerAdapter = new SectionsPagerAdapter(
					getSupportFragmentManager());

			// Set up the ViewPager with the sections adapter.
			mViewPager = (ViewPager) findViewById(R.id.pager);
			mViewPager.setAdapter(mSectionsPagerAdapter);

			// When swiping between different sections, select the corresponding
			// tab. We can also use ActionBar.Tab#select() to do this if we have
			// a reference to the Tab.
			mViewPager
					.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
						@Override
						public void onPageSelected(int position)
						{
							actionBar.setSelectedNavigationItem(position);
						}
					});

			// For each of the sections in the app, add a tab to the action bar.
			for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++)
			{
				// Create a tab with text corresponding to the page title
				// defined by
				// the adapter. Also specify this Activity object, which
				// implements
				// the TabListener interface, as the callback (listener) for
				// when
				// this tab is selected.
				actionBar.addTab(actionBar.newTab()
						.setText(mSectionsPagerAdapter.getPageTitle(i))
						.setTabListener(this));
			}
		}
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction)
	{
		// When the given tab is selected, switch to the corresponding page in
		// the ViewPager.
		mViewPager.setCurrentItem(tab.getPosition());

	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction)
	{
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction)
	{
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	@SuppressLint("DefaultLocale")
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return a DummySectionFragment (defined as a static inner class
			// below) with the page number as its lone argument.
			Fragment fragment = null;
			switch (position)
			{
			case 0:
				fragment = new MainFragment();
				break;
			case 1:
				fragment = new ActivitiesFragment();
				break;
			case 2:
				fragment = new BillListFragment();
				break;
			case 3:
				fragment = new SettingFragment();
				break;
			}

			/* Bundle args = new Bundle(); */
			/*
			 * args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position +
			 * 1); fragment.setArguments(args);
			 */
			return fragment;
		}

		@Override
		public int getCount()
		{
			// Show 3 total pages.
			return 4;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			switch (position)
			{
			case 0:
				return getString(R.string.summary).toUpperCase();
			case 1:
				return getString(R.string.transactions).toUpperCase();
			case 2:
				return getString(R.string.bills).toUpperCase();
			case 3:
				return getString(R.string.title_setting_fragment).toUpperCase();
			}
			return null;
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		finish();
	}

}
