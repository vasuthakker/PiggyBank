package sicsr.proj.piggy_bank1;

import java.util.Calendar;
import java.util.GregorianCalendar;

import proj.entities.Bill;
import proj.helpers.BillHelper;
import proj.service.ReminderService;
import proj.utility.AppConstants;
import proj.utility.Utility;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

public class BillActivity extends Activity {

	long dueDate;
	long lastDate;
	TextView billAccountName;
	TextView billBalance;
	EditText billName;
	EditText billNo;
	EditText lastDateEditText;
	EditText dueDateEditText;
	EditText billAmount;
	ToggleButton reminder;
	Button billSubmit;
	String account = null;
	String balance = null;
	long time = 0;
	LinearLayout timeLayout = null;
	Button timeSetButton;
	LinearLayout baseLayout;
	LinearLayout reminderTimeLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill);

		SharedPreferences preference = getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		account = preference.getString(AppConstants.ACCOUNT, null);
		balance = preference.getString(AppConstants.BALANCE, null);
		baseLayout = (LinearLayout) findViewById(R.id.bill_base_layout);
		Utility.setBackGround(baseLayout, preference);
		billAccountName = (TextView) findViewById(R.id.billaccnm);
		billAccountName.setText(account);
		billBalance = (TextView) findViewById(R.id.b_balance);
		billBalance.setText(balance);
		billName = (EditText) findViewById(R.id.billname);
		billNo = (EditText) findViewById(R.id.billno);
		reminderTimeLayout = (LinearLayout) findViewById(R.id.remindertimelayout);
		timeSetButton = (Button) findViewById(R.id.remindersettimebutton);
		lastDateEditText = (EditText) findViewById(R.id.lastdate);
		lastDateEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus == true)
				{
					LastDatePickerDialogue timePicker = new LastDatePickerDialogue();
					timePicker.show(getFragmentManager(), "lastdatepicker");
				}
			}
		});
		dueDateEditText = (EditText) findViewById(R.id.duedate);
		dueDateEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			@Override
			public void onFocusChange(View v, boolean hasFocus)
			{
				if (hasFocus == true)
				{
					DueDatePickerDialogue duedateTimePicker = new DueDatePickerDialogue();
					duedateTimePicker.show(getFragmentManager(),
							"duedatepicker");
				}
			}
		});
		billAmount = (EditText) findViewById(R.id.billamount);
		reminder = (ToggleButton) findViewById(R.id.reminder);
		billSubmit = (Button) findViewById(R.id.billsubmit);
		billSubmit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				submitBill();
			}
		});
		reminder.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked)
			{
				if (isChecked == true)
				{
					reminderTimeLayout.setVisibility(View.VISIBLE);
				} else
				{
					reminderTimeLayout.setVisibility(View.GONE);
				}
			}
		});
		timeSetButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				Log.v("button", "button clicked");
				TimePickerFragment timePicker = new TimePickerFragment();
				timePicker.show(getFragmentManager(), "timepicker");
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bill, menu);
		return true;
	}

	private void submitBill()
	{
		try
		{
			String bill_name = billName.getText().toString();
			long bill_no = Long.parseLong(billNo.getText().toString());
			double bill_amount = Double.parseDouble(billAmount.getText()
					.toString());
			int reminder_status = 0;
			if (reminder.getText().equals("ON"))
			{
				reminder_status = AppConstants.ACTIVESTATUS;
			} else
			{
				reminder_status = AppConstants.INACTIVESTATUS;
			}

			long reminderTime = 0l;
			Toast.makeText(getApplicationContext(), "Bill added",
					Toast.LENGTH_SHORT).show();
			if (reminder_status == AppConstants.ACTIVESTATUS)
			{
				Intent myIntent = new Intent(this, ReminderService.class);
				myIntent.putExtra(AppConstants.BILLNAME, bill_name);
				myIntent.putExtra(AppConstants.BILLAMOUNT,
						String.valueOf(bill_amount));
				PendingIntent pendingIntent = PendingIntent.getService(this,
						Context.MODE_PRIVATE, myIntent, 0);
				AlarmManager alarmManager = (AlarmManager) getSystemService("alarm");
				alarmManager.set(AlarmManager.RTC_WAKEUP, dueDate + time,
						pendingIntent);
				reminderTime = dueDate + time;
				Toast.makeText(
						getApplicationContext(),
						"alaram set to "
								+ new DateFormat().format(
										"dd(E)-MMM-yyyy h:m a",
										(dueDate + time)), Toast.LENGTH_SHORT)
						.show();
			}
			Bill bill = new Bill();
			bill.setAccount_name(account);
			bill.setBill_amount(bill_amount);
			bill.setBill_name(bill_name);
			bill.setBill_no(bill_no);
			bill.setDue_Date(dueDate);
			bill.setLast_Date(lastDate);
			bill.setIsPaid(AppConstants.BILLNOPAID);
			bill.setReminder_status(reminder_status);
			bill.setIsActive(AppConstants.ACTIVESTATUS);
			bill.setReminder_time(reminderTime);
			BillHelper.addBill(getApplicationContext(), bill);
			finish();
		} catch (Exception e)
		{
			Toast.makeText(getApplicationContext(), "invalid details",
					Toast.LENGTH_SHORT).show();
		}
	}

	public class DueDatePickerDialogue extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

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
			Calendar calender = new GregorianCalendar(year, monthOfYear,
					dayOfMonth);
			long date = calender.getTimeInMillis();
			dueDate = date;
			dueDateEditText.setText(DateFormat.format("dd-MMM-yyyy", dueDate));
		}

	}

	public class LastDatePickerDialogue extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

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
			Calendar calender = new GregorianCalendar(year, monthOfYear,
					dayOfMonth);
			long date = calender.getTimeInMillis();
			lastDate = date;
			lastDateEditText
					.setText(DateFormat.format("dd-MMM-yyyy", lastDate));
		}

	}

	class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			time = (hourOfDay * 3600 + minute * 60) * 1000;
			if (dueDate > 0)
			{
				timeSetButton.setText(new DateFormat().format(
						"dd-MMM-yyyy h:m a", dueDate + time));
			} else
			{
				Toast.makeText(getApplicationContext(),
						"please select due date fist", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
