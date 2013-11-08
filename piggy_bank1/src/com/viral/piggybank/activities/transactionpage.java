package com.viral.piggybank.activities;


import java.util.Calendar;
import java.util.GregorianCalendar;

import com.viral.piggybank.R;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.common.DatabaseHandler;
import com.viral.piggybank.entities.Accounts;
import com.viral.piggybank.entities.Transactions;
import com.viral.piggybank.utilities.Utility;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class transactionpage extends Activity {

	RadioButton income, expense;
	Spinner categorySpinner;
	EditText transactionDate;
	EditText t_amount, t_add_info;
	TextView Accnm;
	String accountName, category, add_info;
	double amount;
	int type;
	Button ok;
	long date;
	Button reset;
	String datePicked = null;
	private DatabaseHandler datasource;
	LinearLayout baseLayout;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transactionpage);
		// Intent intent = getIntent();
		// variable initialization
		datasource = new DatabaseHandler(this);

		SharedPreferences preference = getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		accountName = preference.getString(AppConstants.ACCOUNT, null);
		baseLayout = (LinearLayout) findViewById(R.id.transactionpage_base_layout);
		Utility.setBackGround(baseLayout, preference);

		// account = intent.getStringExtra("account_name");
		if (accountName == null)
			accountName = "default";
		transactionDate = (EditText) findViewById(R.id.t_date);
		t_amount = (EditText) findViewById(R.id.t_amount);
		t_add_info = (EditText) findViewById(R.id.t_add_info);
		Accnm = (TextView) findViewById(R.id.accnm);
		income = (RadioButton) findViewById(R.id.income);
		expense = (RadioButton) findViewById(R.id.expense);
		categorySpinner = (Spinner) findViewById(R.id.category);
		Accnm.setText(accountName.toString());
		ok = (Button) findViewById(R.id.ok);
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				saveTransaction(v);
			}
		});
		transactionDate.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus == true)
				{
					DialogFragment timePicker = new DatePickerDialogue();
					timePicker.show(getFragmentManager(), "datepicker");
				}
			}
		});
		transactionDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{

			}
		});

	}

	public void saveTransaction(View view)
	{

		try
		{

			amount = Double.parseDouble(t_amount.getText().toString());
			add_info = t_add_info.getText().toString();

			category = String.valueOf(categorySpinner.getSelectedItem());
			if (income.isChecked())
				type = AppConstants.INCOME;
			else if (expense.isChecked())
				type = AppConstants.Expense;
			// t_add_info.setText(category);

			Transactions transaction = new Transactions();
			transaction.setAccount_name(accountName);
			transaction.setT_add_info(add_info);
			transaction.setT_amount(amount);
			transaction.setT_category(category);
			transaction.setT_date(date);
			transaction.setT_type(type);
			transaction.setIsActive(AppConstants.ACTIVESTATUS);
			Accounts account = datasource.getAccount(accountName);
			datasource.createTransation(transaction);
			datasource.updateAccount(getApplicationContext(), account,
					transaction);
			datasource.close();
			Toast.makeText(getApplicationContext(), "transaction added",
					Toast.LENGTH_SHORT).show();
			finish();
		} catch (Exception e)
		{
			Toast.makeText(getBaseContext(), "Amount is Required",
					Toast.LENGTH_LONG).show();
		}

	}

	@SuppressLint("ValidFragment")
	public class DatePickerDialogue extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {
		//private static final String TAG = "datepicker";

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// Use the current date as the default date in the picker
			final Calendar c = Calendar.getInstance();
			int year = c.get(Calendar.YEAR);
			int month = c.get(Calendar.MONTH);
			int day = c.get(Calendar.DAY_OF_MONTH);

			// Create a new instance of DatePickerDialog and return it
			return new DatePickerDialog(getActivity(), this, year, month, day);
		}

		@Override
		public void onDateSet(android.widget.DatePicker view, int year,
				int monthOfYear, int dayOfMonth)
		{
			Calendar calendar = new GregorianCalendar(year, monthOfYear,
					dayOfMonth);
			date = calendar.getTimeInMillis();
			transactionDate.setText(DateFormat.format("dd-MMM-yyyy", date));

		}

	}
}
