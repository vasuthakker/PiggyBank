package com.viral.piggybank.activities;

import java.util.List;

import com.viral.piggybank.R;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.entities.Bill;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class BillListAdapter extends BaseAdapter {

	private Activity activity;
	private List<Bill> data;
	private LayoutInflater inflater = null;
	Bill bill;

	public BillListAdapter(Activity a, List<Bill> billList) {
		activity = a;
		data = billList;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

	}

	public int getCount()
	{
		return data.size();
	}

	public Object getItem(int position)
	{
		return position;
	}

	public long getItemId(int position)
	{
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.billrow, null);

		TextView billNo = (TextView) vi.findViewById(R.id.billno);
		TextView billName = (TextView) vi.findViewById(R.id.billname);
		TextView billAmount = (TextView) vi.findViewById(R.id.billamount);
		TextView billDate = (TextView) vi.findViewById(R.id.billdate);
		ImageView reminder = (ImageView) vi.findViewById(R.id.billreminder);
		ImageView paid = (ImageView) vi.findViewById(R.id.billpaid);
		// ImageView deleteBill = (ImageView) vi.findViewById(R.id.delete);
		
		bill = data.get(position);

		billName.setText(bill.getBill_name());
		billNo.setText(String.valueOf(bill.getBill_no()));
		billAmount.setText(String.valueOf(bill.getBill_amount()));
		billDate.setText(DateFormat.format("dd-MMM-yyyy", bill.getDue_Date()));
		if (bill.getIsPaid() == AppConstants.BILLPAID)
		{
			paid.setVisibility(View.VISIBLE);
		} else
		{
			paid.setVisibility(View.INVISIBLE);
		}
		if (bill.getReminder_status() == AppConstants.ACTIVESTATUS)
		{
			reminder.setVisibility(View.VISIBLE);
		} else
		{
			reminder.setVisibility(View.INVISIBLE);
		}

		/*
		 * deleteBill.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) {
		 * bill.setIsActive(AppConstants.INACTIVESTATUS);
		 * BillHelper.updateBill(getActivity(), bill); } });
		 */
		return vi;
	}
}
