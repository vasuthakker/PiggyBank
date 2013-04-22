package sicsr.proj.piggy_bank1;

import java.util.Calendar;
import java.util.List;

import proj.entities.Transactions;
import proj.utility.AppConstants;
import proj.utility.Utility;
import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(11)
public class MainPage extends Fragment {
	protected static final String TAG = "mainpage";
	String message = "Account name";
	String balance = "balance";
	String account = null;
	TextView main_accnm, main_bal;
	LinearLayout baseLayout;
	public DatabaseHandler datasource;
	TextView todayIncome, weekIncome, monthIncome, yearIncome;
	TextView todayExpense, weekExpense, monthExpense, yearExpense;

	double todayincome = 0d, weekincome = 0d, yearincome = 0d,
			monthincome = 0d;
	double todayexpense = 0d, weekexpense = 0d, yearexpense = 0d,
			monthexpense = 0d;

	SharedPreferences preference = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup containter,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.activity_main_page, containter, false);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		datasource = new DatabaseHandler(getActivity());

		Intent intent = getActivity().getIntent();

		preference = getActivity().getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);

		account = preference.getString(AppConstants.ACCOUNT, "");
		balance = preference.getString(AppConstants.BALANCE, "");
		/*
		 * if (account == null) { if(preference!=null &&
		 * !(preference.getString(AppConstants.ACCOUNT,"").equals("")));
		 * 
		 * 
		 * Editor editor = preference.edit();
		 * editor.remove(AppConstants.ACCOUNT);
		 * editor.remove(AppConstants.BALANCE);
		 * editor.putString(AppConstants.ACCOUNT, account);
		 * editor.putString(AppConstants.BALANCE, balance); editor.commit();
		 * account = preference.getString(AppConstants.ACCOUNT,""); }
		 */

		Button newAccountPageOpen = (Button) getActivity().findViewById(
				R.id.newaccountbutton);
		newAccountPageOpen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				newAccount();
			}
		});
		Button viewAccountsPageOpen = (Button) getActivity().findViewById(
				R.id.viewaccountsbutton);
		viewAccountsPageOpen.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				showAccounts();
			}
		});
		message = intent.getStringExtra(Newaccount_Page.EXTRA_MESSAGE);
		main_accnm = (TextView) getActivity().findViewById(R.id.main_accnm);
		main_bal = (TextView) getActivity().findViewById(R.id.main_bal);
		main_bal.setTextColor(Color.GREEN);

		double b = Double.parseDouble(balance);
		main_accnm.setText(account);
		if (balance.charAt(0) != '-')
		{
			main_bal.setTextColor(Color.rgb(36, 147, 51));
		} else
		{
			main_bal.setTextColor(Color.RED);
		}
		main_bal.setText(balance);
		if (!(account.equals("default")))
		{
			if (b <= 0)
			{
				note(getActivity().getApplicationContext(), account
						+ " please don't spend too much");
			}
		}

		todayincome = 0d;
		weekincome = 0d;
		yearincome = 0d;
		monthincome = 0d;
		todayexpense = 0d;
		weekexpense = 0d;
		yearexpense = 0d;
		monthexpense = 0d;

		long currentTime = System.currentTimeMillis();
		Calendar calNow = Calendar.getInstance();
		calNow.setTimeInMillis(currentTime);
		List<Transactions> transactions = datasource.getAllActivities(
				preference.getString(AppConstants.ACCOUNT, account),
				AppConstants.ACTIVESTATUS);
		for (Transactions transaction : transactions)
		{
			Calendar calTransaction = Calendar.getInstance();
			calTransaction.setTimeInMillis(transaction.getT_date());
			if (transaction.getT_type() == AppConstants.INCOME)
			{
				if (calNow.get(Calendar.DAY_OF_YEAR) == calTransaction
						.get(Calendar.DAY_OF_YEAR)
						&& (calNow.get(Calendar.YEAR) == calTransaction
								.get(Calendar.YEAR)))
				{
					todayincome += transaction.getT_amount();
				}
				if (calNow.get(Calendar.WEEK_OF_YEAR) == calTransaction
						.get(Calendar.WEEK_OF_YEAR)
						&& (calNow.get(Calendar.YEAR) == calTransaction
								.get(Calendar.YEAR)))
				{
					weekincome += transaction.getT_amount();

				}
				if (calNow.get(Calendar.MONTH) == calTransaction
						.get(Calendar.MONTH)
						&& (calNow.get(Calendar.YEAR) == calTransaction
								.get(Calendar.YEAR)))
				{
					monthincome += transaction.getT_amount();

				}
				if (calNow.get(Calendar.YEAR) == calTransaction
						.get(Calendar.YEAR))
				{
					yearincome += transaction.getT_amount();
				}

			} else if (transaction.getT_type() == AppConstants.Expense)
			{
				if (calNow.get(Calendar.DAY_OF_YEAR) == calTransaction
						.get(Calendar.DAY_OF_YEAR)
						&& (calNow.get(Calendar.YEAR) == calTransaction
								.get(Calendar.YEAR)))
				{
					todayexpense += transaction.getT_amount();

				}
				if (calNow.get(Calendar.WEEK_OF_YEAR) == calTransaction
						.get(Calendar.WEEK_OF_YEAR)
						&& (calNow.get(Calendar.YEAR) == calTransaction
								.get(Calendar.YEAR)))
				{
					weekexpense += transaction.getT_amount();

				}
				if (calNow.get(Calendar.MONTH) == calTransaction
						.get(Calendar.MONTH)
						&& (calNow.get(Calendar.YEAR) == calTransaction
								.get(Calendar.YEAR)))
				{
					monthexpense += transaction.getT_amount();

				}
				if (calNow.get(Calendar.YEAR) == calTransaction
						.get(Calendar.YEAR))
				{
					yearexpense += transaction.getT_amount();
				}
			}
		}

		todayIncome = (TextView) getActivity().findViewById(R.id.todayincome);
		todayExpense = (TextView) getActivity().findViewById(R.id.todayexpense);
		weekIncome = (TextView) getActivity().findViewById(R.id.weekincome);
		weekExpense = (TextView) getActivity().findViewById(R.id.weekexpense);
		yearIncome = (TextView) getActivity().findViewById(R.id.yearincome);
		yearExpense = (TextView) getActivity().findViewById(R.id.yearexpense);
		monthIncome = (TextView) getActivity().findViewById(R.id.monthincome);
		monthExpense = (TextView) getActivity().findViewById(R.id.monthexpense);

		todayIncome.setText(String.valueOf(todayincome));
		weekIncome.setText(String.valueOf(weekincome));
		monthIncome.setText(String.valueOf(monthincome));
		yearIncome.setText(String.valueOf(yearincome));

		todayExpense.setText(String.valueOf(todayexpense));
		weekExpense.setText(String.valueOf(weekexpense));
		monthExpense.setText(String.valueOf(monthexpense));
		yearExpense.setText(String.valueOf(yearexpense));
		if (todayexpense > Double.parseDouble(preference.getString(
				AppConstants.DAILYLIMIT, "0")))
		{
			note(getActivity().getApplicationContext(), account + " has crossed daily limit");
		}
		if (weekexpense > Double.parseDouble(preference.getString(
				AppConstants.WEEKLYLIMIT, "0")))
		{
			note(getActivity().getApplicationContext(), account + " has crossed weekly limit");
		}
		if (monthexpense > Double.parseDouble(preference.getString(
				AppConstants.MONTHLYLIMIT, "0")))
		{
			note(getActivity().getApplicationContext(), account + " has crossed monthly limit");
		}
		if (yearexpense > Double.parseDouble(preference.getString(
				AppConstants.YEARLYLIMIT, "0")))
		{
			note(getActivity().getApplicationContext(), account + " has crossed weekly limit");
		}

	}

	@Override
	public void onResume()
	{
		super.onResume();
		baseLayout = (LinearLayout) getActivity().findViewById(
				R.id.main_page_base_layout);
		Utility.setBackGround(baseLayout, preference);
	}

	// Notification Method
	public void note(Context context, String message)
	{
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				context).setSmallIcon(R.drawable.ic_launcher)
				.setContentTitle("Piggy Bank").setContentText(message);
		// Creates an explicit intent for an Activity in your app
		Intent resultIntent = new Intent(context, PiggyBank.class);

		// The stack builder object will contain an artificial back stack for
		// the
		// started Activity.
		// This ensures that navigating backward from the Activity leads out of
		// your application to the Home screen.
		TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// Adds the back stack for the Intent (but not the Intent itself)
		stackBuilder.addParentStack(PiggyBank.class);
		// Adds the Intent that starts the Activity to the top of the stack
		stackBuilder.addNextIntent(resultIntent);
		PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
				PendingIntent.FLAG_UPDATE_CURRENT);
		mBuilder.setContentIntent(resultPendingIntent);
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		// mId allows you to update the notification later on.
		mNotificationManager.notify(0, mBuilder.build());
	}

	public void newAccount()
	{
		// Do something in response to button
		Intent intent = new Intent(getActivity(), Newaccount_Page.class);
		startActivity(intent);
	}

	public void showAccounts()
	{
		// Do something in response to button
		Intent intent = new Intent(getActivity(), accountspage.class);
		startActivity(intent);
	}

	public void deleteAccount(View view)
	{
		// Do something in response to button
		if (account.equals("default"))
		{
			Toast.makeText(getActivity(), "Default account cannot be deleted",
					Toast.LENGTH_LONG).show();
		} else
		{
			datasource.deleteRecord("accounts", "account_name", account);
			datasource.deleteRecord("transactions", "account_name", account);
			Intent intent = new Intent(getActivity(), accountspage.class);
			startActivity(intent);
		}
	}

}
