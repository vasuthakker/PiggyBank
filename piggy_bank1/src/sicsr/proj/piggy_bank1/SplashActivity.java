package sicsr.proj.piggy_bank1;

import proj.entities.Accounts;
import proj.entities.Limits;
import proj.helpers.LimitsHelper;
import proj.utility.AppConstants;
import proj.utility.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SplashActivity extends Activity {
	EditText defaultAccountName;
	EditText defaultAccountBalance;
	Button addDefaultAccountBalance;
	DatabaseHandler dataSource;
	SharedPreferences preferences = null;
	LinearLayout baseLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		preferences = getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);

		if (preferences.getString(AppConstants.ACCOUNT, null) != null)
		{
			Intent intent = new Intent(getApplicationContext(), PiggyBank.class);
			startActivity(intent);
		} else
		{
			dataSource = new DatabaseHandler(this);
			defaultAccountName = (EditText) findViewById(R.id.defaultaccountname);
			defaultAccountBalance = (EditText) findViewById(R.id.defaultaccountbalance);
			addDefaultAccountBalance = (Button) findViewById(R.id.adddefaultaccount);
			addDefaultAccountBalance.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v)
				{
					addDefaultAccount();
				}
			});
		}
	}

	@Override
	public void onPause()
	{
		super.onPause();
		finish();
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		System.exit(0);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		baseLayout = (LinearLayout) findViewById(R.id.splash_base_layout);
		Utility.setBackGround(baseLayout, preferences);

	}

	private void addDefaultAccount()
	{
		try
		{
			if(defaultAccountName.getText()==null && defaultAccountName.getText().length()==0)
			{
				defaultAccountName.setError("pls enter account name");
			}
			if(defaultAccountBalance.getText()==null && defaultAccountBalance.getText().length()==0)
			{
				defaultAccountBalance.setError("pls enter balance");
			}
			String defautName = defaultAccountName.getText().toString();
			double defaultBalance = Double.parseDouble(defaultAccountBalance
					.getText().toString());
			Accounts account = new Accounts();
			account.setAccount_name(defautName);
			account.setBalance(defaultBalance);
			account.setCreation_date(System.currentTimeMillis());
			account.setIsActive(AppConstants.ACTIVESTATUS);
			dataSource.createAccount(account);
			
			Limits limit=new Limits();
			limit.setAccount_name(defautName);
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
			editor.putString(AppConstants.ACCOUNT, defautName);
			editor.putString(AppConstants.BALANCE, String.valueOf(defaultBalance));
			editor.putString(AppConstants.DAILYLIMIT, String.valueOf(limit.getDaily_limit()));
			editor.putString(AppConstants.WEEKLYLIMIT, String.valueOf(limit.getWeekly_limit()));
			editor.putString(AppConstants.MONTHLYLIMIT, String.valueOf(limit.getMonthly_limit()));
			editor.putString(AppConstants.YEARLYLIMIT, String.valueOf(limit.getYearly_limit()));
			editor.commit();
			
		
			
			Intent intent = new Intent(getApplicationContext(), PiggyBank.class);
			startActivity(intent);
		} catch (Exception e)
		{
			Toast.makeText(getApplicationContext(),
					"account name or password invalid", Toast.LENGTH_SHORT)
					.show();
		}
	}

}
