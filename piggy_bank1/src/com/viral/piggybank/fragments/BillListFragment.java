package com.viral.piggybank.fragments;

import java.util.ArrayList;
import java.util.List;

import com.viral.piggybank.R;
import com.viral.piggybank.activities.BillActivity;
import com.viral.piggybank.activities.BillDetailActivty;
import com.viral.piggybank.activities.BillListAdapter;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.entities.Bill;
import com.viral.piggybank.helpers.BillHelper;
import com.viral.piggybank.utilities.Utility;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class BillListFragment extends Fragment {
	private static final String TAG = "View_Bill";
	String accountName = null;
	ListView billListView;
	Button addNewBillButton;
	Bill bill;
	Context context;
	List<Bill> billList = null;
	BillListAdapter adapter = null;
	RelativeLayout baseLayout;
	SharedPreferences preference = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup containter,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.activity_view_bill, containter, false);
	}

	@Override
	public void onStart() {
		super.onStart();

		addNewBillButton = (Button) getActivity().findViewById(R.id.newbill);
		addNewBillButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(), BillActivity.class);
				startActivity(intent);

			}
		});

		preference = getActivity().getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		accountName = preference.getString(AppConstants.ACCOUNT, null);

		billListView = (ListView) getActivity().findViewById(R.id.billlistview);
		billList = new ArrayList<Bill>();
		billList = BillHelper.fetchBill(getActivity().getApplicationContext(),
				accountName, AppConstants.ACTIVESTATUS);
		Log.v(TAG, "size of bills is " + billList.size());
		TextView totalBillAmount = (TextView) getActivity().findViewById(
				R.id.billtotalamount);
		double billAmount = getBillTotal(billList);

		totalBillAmount.setText(String.valueOf(billAmount));
		adapter = new BillListAdapter(getActivity(), billList);
		billListView.setAdapter(adapter);
		context = this.getActivity().getApplicationContext();
		billListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Intent billDetailIntent = new Intent(context,
						BillDetailActivty.class);
				billDetailIntent.putExtra(AppConstants.BILL,
						billList.get(position));
				startActivity(billDetailIntent);
			}
		});

		billListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Bill bill = billList.get(position);
				bill.setIsActive(AppConstants.INACTIVESTATUS);
				BillHelper.updateBill(context, bill);
				changeBillListView(position);
				Toast.makeText(context, "Bill deleted", Toast.LENGTH_SHORT)
						.show();
				return true;
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		baseLayout = (RelativeLayout) getActivity().findViewById(
				R.id.viewbill_base_layout);
		Utility.setButtonColor(addNewBillButton, preference);
		Utility.setBackGround(baseLayout, preference);

	}

	private void changeBillListView(int position) {
		billList.remove(position);
		adapter.notifyDataSetChanged();
	}

	double getBillTotal(List<Bill> billList) {
		double billAmount = 0d;
		for (Bill bill : billList) {
			billAmount += bill.getBill_amount();
		}
		return billAmount;
	}

}
