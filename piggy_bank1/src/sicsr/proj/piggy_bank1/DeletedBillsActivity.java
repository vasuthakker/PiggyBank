package sicsr.proj.piggy_bank1;

import java.util.ArrayList;
import java.util.List;

import proj.entities.Bill;
import proj.helpers.BillHelper;
import proj.utility.AppConstants;
import proj.utility.Utility;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class DeletedBillsActivity extends Activity {
	ListView deletedBillListView;
	List<Bill> billList = null;
	LazyAdapter4Bills billsAdapter = null;
	LinearLayout baseLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deleted_bills);
		deletedBillListView = (ListView) findViewById(R.id.deleted_bill_list);
		SharedPreferences preference = getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, this.MODE_PRIVATE);
		String accountName = preference.getString(AppConstants.ACCOUNT, "");
		baseLayout=(LinearLayout)findViewById(R.id.deleted_bill_base_layout);
		Utility.setBackGround(baseLayout, preference);
		billList = BillHelper.fetchBill(getApplicationContext(), accountName,
				AppConstants.INACTIVESTATUS);
		if (billList == null)
		{
			billList = new ArrayList<Bill>();
		}
		if (billList.size() > 0)
		{
			billsAdapter = new LazyAdapter4Bills(this, billList);
			deletedBillListView.setAdapter(billsAdapter);
			deletedBillListView
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3)
						{
							Intent intent = new Intent(getApplicationContext(),
									BillDetailActivty.class);
							intent.putExtra(AppConstants.BILL,
									billList.get(position));
							startActivity(intent);
						}

					});
			
			deletedBillListView.setOnItemLongClickListener(new OnItemLongClickListener() {

				@Override
				public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
						int position, long arg3)
				{
					Bill bill = billList.get(position);
					bill.setIsActive(AppConstants.ACTIVESTATUS);
					BillHelper.updateBill(getApplicationContext(), bill);
					billList.remove(position);
					billsAdapter.notifyDataSetChanged();
							Toast.makeText(getApplicationContext(),
									"Bill restored", Toast.LENGTH_SHORT).show();
					return true;
				}
			});
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_deleted_bills, menu);
		return true;
	}

}
