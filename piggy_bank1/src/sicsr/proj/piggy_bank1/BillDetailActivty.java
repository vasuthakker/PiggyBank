package sicsr.proj.piggy_bank1;

import proj.entities.Bill;
import proj.helpers.BillHelper;
import proj.utility.AppConstants;
import proj.utility.Utility;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

public class BillDetailActivty extends Activity {
	Bill bill = null;
	TextView billDetailNo = null;
	TextView billDetailName = null;
	TextView billDetailLastDate = null;
	TextView billDetailDueDate = null;
	CheckBox billDetailIsPaid = null;
	TextView billDetailIsReminder = null;
	TextView billDetailAmount = null;
	TextView billDetailDateLabel = null;
	TextView billDetailDate_time = null;
	Button finishBillDetail = null;
	LinearLayout baseLayout;
	SharedPreferences preference = null;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bill_detail_activty);
		preference = getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, this.MODE_PRIVATE);

		bill = (Bill) getIntent().getSerializableExtra(AppConstants.BILL);
		billDetailNo = (TextView) findViewById(R.id.billdetailno);
		billDetailName = (TextView) findViewById(R.id.billdetailname);
		billDetailLastDate = (TextView) findViewById(R.id.billdetaillastdate);
		billDetailDueDate = (TextView) findViewById(R.id.billdetailduedate);
		billDetailIsReminder = (TextView) findViewById(R.id.billdetailreminder);
		billDetailIsPaid = (CheckBox) findViewById(R.id.ispaidcheckbox);
		billDetailAmount = (TextView) findViewById(R.id.billdetailamount);
		finishBillDetail = (Button) findViewById(R.id.finishbilldetail);
		billDetailDateLabel = (TextView) findViewById(R.id.billdetaildate);
		billDetailDate_time = (TextView) findViewById(R.id.billdetailremindertime);
		finishBillDetail.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v)
			{
				udpateBill(getApplicationContext());
				finish();
			}
		});
		displayBillDetail();
	}

	private void udpateBill(Context context)
	{
		if (billDetailIsPaid.isChecked() == true)
		{
			bill.setIsPaid(AppConstants.BILLPAID);
			BillHelper.updateBill(context, bill);
		} else
		{
			bill.setIsPaid(AppConstants.BILLNOPAID);
			BillHelper.updateBill(context, bill);
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();
		udpateBill(getApplicationContext());
		finish();
	}

	private void displayBillDetail()
	{
		billDetailAmount.setText(String.valueOf(bill.getBill_amount()));
		billDetailDueDate.setText(DateFormat.format("dd-MMM-yyyy",
				bill.getDue_Date()));
		billDetailLastDate.setText(DateFormat.format("dd-MMM-yyyy",
				bill.getLast_Date()));
		if (bill.getIsPaid() == AppConstants.BILLPAID)
		{
			billDetailIsPaid.setChecked(true);
		} else
		{
			billDetailIsPaid.setChecked(false);
		}
		billDetailName.setText(bill.getBill_name());
		if (bill.getReminder_status() == AppConstants.ACTIVESTATUS)
		{
			billDetailIsReminder.setText("Yes");
		} else
		{
			billDetailIsReminder.setText("No");
		}
		billDetailNo.setText(String.valueOf(bill.getBill_no()));
		billDetailDateLabel.setVisibility(View.VISIBLE);
		billDetailDate_time.setVisibility(View.VISIBLE);
		billDetailDate_time.setText(new DateFormat().format(
				"dd(E)-MMM-yyyy h:m a", bill.getReminder_time()));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_bill_detail_activty, menu);
		return true;
	}

	@Override
	public void onResume()
	{
		super.onResume();
		baseLayout = (LinearLayout) findViewById(R.id.bill_detail_base_layout);
		Utility.setBackGround(baseLayout, preference);
	}

}
