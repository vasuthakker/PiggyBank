package sicsr.proj.piggy_bank1;

import proj.entities.Transactions;
import proj.utility.AppConstants;
import proj.utility.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class TransactionDetailActivity extends Activity {
	TextView dateTextView;
	TextView accountNameTextView;
	TextView amountTextView;
	TextView typeTextView;
	Button goBackButton;
	ImageView categoryImage;
	Transactions transaction = null;
	TextView categoryText;
	String accountName;
	TextView additionalInfoTextView;
	LinearLayout baseLayout;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_transaction_detail);
		SharedPreferences preference = getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		accountName = preference.getString(AppConstants.ACCOUNT, null);

		transaction = (Transactions) getIntent().getSerializableExtra(
				AppConstants.TRANSACTION);
		baseLayout=(LinearLayout)findViewById(R.id.transaction_detail_base_layout);
		Utility.setBackGround(baseLayout, preference);

		dateTextView = (TextView) findViewById(R.id.t_detail_Date);
		typeTextView = (TextView) findViewById(R.id.t_detail_type);
		amountTextView = (TextView) findViewById(R.id.t_detail_transaction_amount);
		accountNameTextView = (TextView) findViewById(R.id.t_detail_account_name);
		goBackButton = (Button) findViewById(R.id.t_detail_goback);
		categoryImage = (ImageView) findViewById(R.id.t_detail_category);
		categoryText=(TextView)findViewById(R.id.t_detail_category_text);
		additionalInfoTextView=(TextView)findViewById(R.id.t_detail_add_info);
		goBackButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				finish();
			}
		});
		setDetail();

	}

	private void setDetail()
	{
		accountNameTextView.setText(accountName);
		amountTextView.setText(transaction.getT_amount().toString());
		if (transaction.getT_type() == AppConstants.INCOME)
		{
			typeTextView.setTextColor(Color.rgb(36, 147, 51));
			typeTextView.setText("INCOME");
		} else
		{
			typeTextView.setTextColor(Color.RED);
			typeTextView.setText("EXPENSE");
		}

		dateTextView.setText(DateFormat.format("dd-MMM-yyyy",
				transaction.getT_date()));
		String category = transaction.getT_category();
		if (category.equalsIgnoreCase("shopping"))
			categoryImage.setImageResource(R.drawable.shopping);
		else if (category.equalsIgnoreCase("grocery"))
			categoryImage.setImageResource(R.drawable.fruit);
		else if (category.equalsIgnoreCase("salary"))
			categoryImage.setImageResource(R.drawable.salary);
		else if (category.equalsIgnoreCase("gift"))
			categoryImage.setImageResource(R.drawable.gift);
		else if (category.equalsIgnoreCase("Home Expence"))
			categoryImage.setImageResource(R.drawable.home);
		else if (category.equalsIgnoreCase("Lunch/Dinner"))
			categoryImage.setImageResource(R.drawable.meal);
		else if (category.equalsIgnoreCase("Mobile Recharge"))
			categoryImage.setImageResource(R.drawable.recharge);
		else if (category.equalsIgnoreCase("Travelling"))
			categoryImage.setImageResource(R.drawable.plane);
		else if (category.equalsIgnoreCase("Hotel Charge"))
			categoryImage.setImageResource(R.drawable.hotel);
		else
			categoryImage.setImageResource(R.drawable.others);
		
		categoryText.setText(category);
		additionalInfoTextView.setText(transaction.getT_add_info());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_transaction_detail, menu);
		return true;
	}

}
