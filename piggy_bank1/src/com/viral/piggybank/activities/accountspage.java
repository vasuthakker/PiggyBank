package com.viral.piggybank.activities;

import java.util.ArrayList;
import java.util.List;

import com.viral.piggybank.R;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.common.DatabaseHandler;
import com.viral.piggybank.entities.Accounts;
import com.viral.piggybank.entities.Limits;
import com.viral.piggybank.helpers.LimitsHelper;
import com.viral.piggybank.utilities.Utility;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class accountspage extends Activity {

	private DatabaseHandler datasource;
	ListView accountsListView;
	LinearLayout baseLayout;
	SharedPreferences preference = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_accountspage);

		preference = getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);

		datasource = new DatabaseHandler(this);
		// datasource.open();
		accountsListView = (ListView) findViewById(R.id.account_list);
		List<Accounts> accountsList = datasource.getAllAccounts();
		if (accountsList == null)
			accountsList = new ArrayList<Accounts>();

		Adapter4Accounts accountsAdapter = new Adapter4Accounts(this,
				accountsList);

		accountsListView.setAdapter(accountsAdapter);

		// listening to single list item on click
		accountsListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// selected item
				TextView accountNameTextView = (TextView) view
						.findViewById(R.id.account_page_account_name);
				TextView balanceTextView = (TextView) view
						.findViewById(R.id.account_page_account_balance);
				String account = accountNameTextView.getText().toString();
				String balance = balanceTextView.getText().toString();

				Limits limit = new Limits();
				limit.setAccount_name(account);
				limit.setDaily_limit(1000);
				limit.setWeekly_limit(7000);
				limit.setMonthly_limit(30000);
				limit.setYearly_limit(200000);
				LimitsHelper.addLimit(getApplicationContext(), limit);

				SharedPreferences preference = getSharedPreferences(
						AppConstants.PIGGYBANKSAHREDPREFERNECE, 0);

				Editor editor = preference.edit();
				editor.remove(AppConstants.ACCOUNT);
				editor.remove(AppConstants.BALANCE);
				editor.putString(AppConstants.ACCOUNT, account);
				editor.putString(AppConstants.BALANCE, balance);
				editor.putString(AppConstants.DAILYLIMIT,
						String.valueOf(limit.getDaily_limit()));
				editor.putString(AppConstants.WEEKLYLIMIT,
						String.valueOf(limit.getWeekly_limit()));
				editor.putString(AppConstants.MONTHLYLIMIT,
						String.valueOf(limit.getMonthly_limit()));
				editor.putString(AppConstants.YEARLYLIMIT,
						String.valueOf(limit.getYearly_limit()));
				editor.commit();

				Intent intent = new Intent(getApplicationContext(),
						HomeActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();

			}

		});
	}

	@Override
	public void onResume() {
		super.onResume();
		baseLayout = (LinearLayout) findViewById(R.id.accountspage_base_layout);
		Utility.setBackGround(baseLayout, preference);
	}

	public class Adapter4Accounts extends BaseAdapter {

		private Activity activity;
		private List<Accounts> data;
		private LayoutInflater inflater = null;

		public Adapter4Accounts(Activity a, List<Accounts> accountsList) {
			activity = a;
			data = accountsList;
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		}

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			return position;
		}

		public long getItemId(int position) {
			return position;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			View vi = convertView;
			if (convertView == null)
				vi = inflater.inflate(R.layout.account_row, null);

			Accounts account = data.get(position);
			TextView accountNameTextView = (TextView) vi
					.findViewById(R.id.account_page_account_name);
			TextView accountBalanceTextView = (TextView) vi
					.findViewById(R.id.account_page_account_balance);

			accountNameTextView.setText(account.getAccount_name());
			accountBalanceTextView
					.setText(String.valueOf(account.getBalance()));
			return vi;
		}

	}

	@Override
	public void onPause() {
		super.onPause();
		finish();
	}
}
