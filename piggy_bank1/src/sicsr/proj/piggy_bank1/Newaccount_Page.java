package sicsr.proj.piggy_bank1;

import proj.entities.Accounts;
import proj.utility.AppConstants;
import proj.utility.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Newaccount_Page extends Activity {

	EditText accountnm, balance;
	String accnm, bal;
	LinearLayout baseLayout;
	private DatabaseHandler datasource;

	public static final String EXTRA_MESSAGE = "MESSAGE";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_newaccount__page);
		System.out.println("newaccount1");
		datasource = new DatabaseHandler(this);
		// datasource.open();
		SharedPreferences preference=getSharedPreferences(AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		baseLayout=(LinearLayout)findViewById(R.id.new_account_base_layout);
		Utility.setBackGround(baseLayout, preference);

	}

	
	public void saveAccount(View view) {
		String error = "";

		accountnm = (EditText) findViewById(R.id.accountnm);
		balance = (EditText) findViewById(R.id.accountbal);
		accnm = accountnm.getText().toString();
		bal = balance.getText().toString();
		if (accnm.equals("")) {
			if (bal.equals("")) {
				error = "Account Name is required \n";
				error = error + "Balance is required";
				Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG)
						.show();
			} else {
				error = "Account Name is required \n";
				Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG)
						.show();
			}
		} else {
			if (bal.equals("")) {
				error = error + "Balance is required";
				Toast.makeText(getBaseContext(), error, Toast.LENGTH_LONG)
						.show();
			} else {

				Double balanceInDouble = Double.parseDouble(bal);
				Accounts acc = new Accounts(accnm, balanceInDouble,
						System.currentTimeMillis());

				datasource.createAccount(acc);
				Intent intent = new Intent(this, accountspage.class);
				startActivity(intent);
			}
		}
	}
	
	@Override
	public void onPause()
	{
		super.onPause();
		finish();
	}

}
