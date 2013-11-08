package com.viral.piggybank.activities;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viral.piggybank.R;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.services.ReminderService;
import com.viral.piggybank.utilities.Utility;

public class AlaramActivity extends Activity {
	LinearLayout baseLayout;
	TextView billName;
	TextView billAmount;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_alaram);
		SharedPreferences preference=getSharedPreferences(AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		baseLayout=(LinearLayout)findViewById(R.id.alarm_base_layout);
		Utility.setBackGround(baseLayout, preference);
		billName=(TextView)findViewById(R.id.alarambillname);
		billAmount=(TextView)findViewById(R.id.alarambillamount);
		String bill_Name=getIntent().getStringExtra(AppConstants.BILLNAME);
		String bill_amount=getIntent().getStringExtra(AppConstants.BILLAMOUNT);
		if(bill_Name!=null)
		{
			billName.setText(bill_Name);
			billAmount.setText(bill_amount);
		}
		else
		{
			billName.setText("no bill to reminder");
			billAmount.setText("");
		}
		Uri notification = RingtoneManager
				.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(getApplicationContext(),
				notification);
		r.play();
		Button canelAlaram=(Button)findViewById(R.id.cancelalaram);
		canelAlaram.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				cancelAlaram(getApplicationContext());
			}
		});
	}

	public void cancelAlaram(Context context)
	{
		Intent intent = new Intent(context, AlaramActivity.class);
		PendingIntent sender = PendingIntent
				.getBroadcast(context, 0, intent, 0);
		AlarmManager alarmManager = (AlarmManager) context
				.getSystemService(Context.ALARM_SERVICE);
		alarmManager.cancel(sender);
		Intent stopIntent=new Intent(context,ReminderService.class);
		stopService(stopIntent);
		//Bill bill=BillHelper.
		finish();
	}


}
