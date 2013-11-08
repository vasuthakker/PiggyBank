package com.viral.piggybank.fragments;

import java.util.ArrayList;
import java.util.List;

import com.viral.piggybank.R;
import com.viral.piggybank.activities.DeletedBillsActivity;
import com.viral.piggybank.activities.DeletedTransactionsActivity;
import com.viral.piggybank.activities.SplashActivity;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.common.DatabaseHandler;
import com.viral.piggybank.entities.Limits;
import com.viral.piggybank.helpers.LimitsHelper;
import com.viral.piggybank.helpers.TableCreationQueries;
import com.viral.piggybank.utilities.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SettingFragment extends Fragment {

	TextView dailyLimitTextView;
	TextView weeklyLimitTextView;
	TextView monthlyLimitTextView;
	TextView yearlyLimitTextView;
	Button deletedTransactionButton;
	Button deletedBillButton;
	Button setLimitButton;
	Spinner colorSpinner;
	LinearLayout baseLayout;
	SharedPreferences preference = null;
	RelativeLayout baseLayout_viewbill;
	String accountName = "";
	Button resetButton;
	Button addNewBillButton;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup containter,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_setting, containter, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		addNewBillButton = (Button) getActivity().findViewById(R.id.newbill);
		baseLayout = (LinearLayout) getActivity().findViewById(
				R.id.setting_base_layout);
		baseLayout_viewbill = (RelativeLayout) getActivity().findViewById(
				R.id.viewbill_base_layout);
		dailyLimitTextView = (TextView) getActivity().findViewById(
				R.id.dailly_limit);
		weeklyLimitTextView = (TextView) getActivity().findViewById(
				R.id.weekly_limit);
		monthlyLimitTextView = (TextView) getActivity().findViewById(
				R.id.monthly_limit);
		yearlyLimitTextView = (TextView) getActivity().findViewById(
				R.id.yearly_limit);
		deletedTransactionButton = (Button) getActivity().findViewById(
				R.id.deleted_transaction_button);
		deletedBillButton = (Button) getActivity().findViewById(
				R.id.deleted_bill_button);
		setLimitButton = (Button) getActivity().findViewById(R.id.set_limits);
		colorSpinner = (Spinner) getActivity().findViewById(R.id.colorSpinner);
		colorSpinner.setSelection(0);

		Context context = getActivity().getApplicationContext();

		preference = context.getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		/*
		 * setColorPrefernce(preference, AppConstants.BLUECOLOR);
		 * Utility.setBackGround(baseLayout, preference);
		 * Utility.setBackGround(baseLayout_viewbill, preference);
		 * Utility.setButtonColor(addNewBillButton, preference);
		 */
		accountName = preference.getString(AppConstants.ACCOUNT, "");

		setLimitButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setLimits(getActivity().getApplicationContext());
			}
		});

		deletedTransactionButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewDeletedTransactions(getActivity().getApplicationContext());
			}
		});

		deletedBillButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				viewDeletedBills(getActivity().getApplicationContext());
			}
		});

		colorSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View view,
					int position, long arg3) {
				switch (position) {
				case 0:
					setColorPrefernce(preference, AppConstants.BLUECOLOR);
					break;
				case 1:
					setColorPrefernce(preference, AppConstants.ORANGENCOLOR);
					break;
				case 2:
					setColorPrefernce(preference, AppConstants.PURPLECOLOR);
					break;
				case 3:
					setColorPrefernce(preference, AppConstants.GREENCOLOR);
					break;
				case 4:
					setColorPrefernce(preference, AppConstants.PINKCOLOR);
					break;
				case 5:
					setColorPrefernce(preference, AppConstants.YELLOWCOLOR);
					break;
				default:
					setColorPrefernce(preference, AppConstants.BLUECOLOR);
					break;
				}
				Utility.setBackGround(baseLayout, preference);
				if (baseLayout != null && addNewBillButton != null) {
					Utility.setBackGround(baseLayout_viewbill, preference);
					Utility.setButtonColor(addNewBillButton, preference);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		resetButton = (Button) getActivity().findViewById(R.id.reset_button);
		resetButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				resetDatabase(getActivity().getApplicationContext());
			}
		});
	}

	public static void setColorPrefernce(SharedPreferences preferences,
			String color) {
		SharedPreferences pref = preferences;
		Editor edit = pref.edit();
		edit.remove(AppConstants.BACKGROUNDCOLOR);
		edit.putString(AppConstants.BACKGROUNDCOLOR, color);
		edit.commit();
	}

	private void viewDeletedTransactions(Context context) {
		Intent intent = new Intent(context, DeletedTransactionsActivity.class);
		startActivity(intent);
	}

	private void viewDeletedBills(Context context) {
		Intent intent = new Intent(context, DeletedBillsActivity.class);
		startActivity(intent);
	}

	private void setLimits(Context context) {
		SharedPreferences preference = context.getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		Limits limit = new Limits();
		limit.setAccount_name(preference.getString(AppConstants.ACCOUNT, ""));

		double dailyLimit = Double.parseDouble(dailyLimitTextView.getText()
				.toString());
		double weeklyLimit = Double.parseDouble(weeklyLimitTextView.getText()
				.toString());
		double monthlyLimit = Double.parseDouble(monthlyLimitTextView.getText()
				.toString());
		double yearlyLimit = Double.parseDouble(yearlyLimitTextView.getText()
				.toString());

		String toastMessage = null;
		if (yearlyLimit > monthlyLimit) {
			if (monthlyLimit > weeklyLimit) {

				if (weeklyLimit > dailyLimit) {
					limit.setDaily_limit(dailyLimit);
					limit.setWeekly_limit(weeklyLimit);
					limit.setMonthly_limit(monthlyLimit);
					limit.setYearly_limit(yearlyLimit);
					LimitsHelper.updateLimits(context, limit);

					Editor editor = preference.edit();
					editor.remove(AppConstants.DAILYLIMIT);
					editor.remove(AppConstants.WEEKLYLIMIT);
					editor.remove(AppConstants.MONTHLYLIMIT);
					editor.remove(AppConstants.YEARLYLIMIT);
					editor.putString(AppConstants.DAILYLIMIT,
							String.valueOf(limit.getDaily_limit()));
					editor.putString(AppConstants.WEEKLYLIMIT,
							String.valueOf(limit.getWeekly_limit()));
					editor.putString(AppConstants.MONTHLYLIMIT,
							String.valueOf(limit.getMonthly_limit()));
					editor.putString(AppConstants.YEARLYLIMIT,
							String.valueOf(limit.getYearly_limit()));
					editor.commit();
					toastMessage = getString(R.string.limit_Saved);

				} else {
					toastMessage = getString(R.string.dailylimit_greter_than_weeklylimit);
				}
			} else {
				toastMessage = getString(R.string.weeklylimit_greter_than_monthlylimit);
			}
		} else {
			toastMessage = getString(R.string.monthlimit_greter_than_yearlylimit);
		}

		Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onResume() {
		super.onResume();
		String color = preference.getString(AppConstants.BACKGROUNDCOLOR,
				AppConstants.BLUECOLOR);
		if (color.equals(AppConstants.BLUECOLOR)) {
			colorSpinner.setSelection(0);
		} else if (color.equals(AppConstants.ORANGENCOLOR)) {
			colorSpinner.setSelection(1);
		} else if (color.equals(AppConstants.PURPLECOLOR)) {
			colorSpinner.setSelection(2);
		} else if (color.equals(AppConstants.GREENCOLOR)) {
			colorSpinner.setSelection(3);
		} else if (color.equals(AppConstants.PINKCOLOR)) {
			colorSpinner.setSelection(4);
		} else if (color.equals(AppConstants.YELLOWCOLOR)) {
			colorSpinner.setSelection(5);
		}
		Limits limit = LimitsHelper.fetchLimit(
				getActivity().getApplicationContext(), accountName).get(0);
		dailyLimitTextView.setText(String.valueOf(limit.getDaily_limit()));
		weeklyLimitTextView.setText(String.valueOf(limit.getWeekly_limit()));
		monthlyLimitTextView.setText(String.valueOf(limit.getMonthly_limit()));
		yearlyLimitTextView.setText(String.valueOf(limit.getYearly_limit()));

	}

	private void resetDatabase(Context context) {
		List<String> truncateList = new ArrayList<String>();
		truncateList.add("DROP TABLE IF EXISTS ACCOUNTS");
		truncateList.add("DROP TABLE IF EXISTS TRANSACTIONS");
		truncateList.add("DROP TABLE IF EXISTS BILLS");
		truncateList.add("DROP TABLE IF EXISTS LIMITS");

		DatabaseHandler db = new DatabaseHandler(context);
		try {

			for (int i = 0; i < truncateList.size(); i++) {
				String truncateTableQuery = truncateList.get(i);
				db.executeQeury(truncateTableQuery);
			}
			SharedPreferences preference = context.getSharedPreferences(
					AppConstants.PIGGYBANKSAHREDPREFERNECE,
					Context.MODE_PRIVATE);
			Editor editor = preference.edit();
			editor.clear();
			editor.commit();

			List<String> queries = TableCreationQueries.getQueries();
			for (String query : queries) {
				Log.v("Setting", "Creating table again->" + query);
				db.executeQeury(query);
			}
			Intent intent = new Intent(context, SplashActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
