package com.viral.piggybank.activities;

import java.util.List;

import com.viral.piggybank.R;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.entities.Transactions;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class TransactionListAdapter extends BaseAdapter {

	private Activity activity;
	private List<Transactions> data;
	private LayoutInflater inflater = null;

	public TransactionListAdapter(Activity a, List<Transactions> transactionList) {
		activity = a;
		data = transactionList;
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
			vi = inflater.inflate(R.layout.list_row, null);

		TextView amountTextView = (TextView) vi.findViewById(R.id.textamount); // title
		TextView typeTextView = (TextView) vi.findViewById(R.id.textType); // artist
																	// name
		TextView dateTextView = (TextView) vi.findViewById(R.id.textDate); // duration
		ImageView category_image_View = (ImageView) vi.findViewById(R.id.imageView1); // thumb
		TextView addInfoTextView=(TextView)vi.findViewById(R.id.addinfotextview);
		TextView categoryNameTextView = (TextView) vi.findViewById(R.id.textCategory); // duration

		Transactions transaction = data.get(position);
		amountTextView.setText(transaction.getT_amount().toString());
		// Setting all values in listview
		String addInfo = transaction.getT_add_info();
		if (addInfo != null && addInfo.trim().length()>0)
		{
			addInfoTextView.setVisibility(View.VISIBLE);
			addInfoTextView.setText(addInfo);
		} else
		{
			addInfoTextView.setVisibility(View.GONE);
		}
		
		if (transaction.getT_type() == AppConstants.INCOME)
		{
			typeTextView.setTextColor(Color.rgb(36, 147, 51));
			typeTextView.setText("INCOME");
		} else
		{
			typeTextView.setTextColor(Color.RED);
			typeTextView.setText("EXPENSE");
		}

		dateTextView.setText(DateFormat.format("dd-MMM-yyyy", transaction.getT_date()));
		String category = transaction.getT_category();
		if (category.equalsIgnoreCase("shopping"))
			category_image_View.setImageResource(R.drawable.shopping);
		else if (category.equalsIgnoreCase("grocery"))
			category_image_View.setImageResource(R.drawable.fruit);
		else if (category.equalsIgnoreCase("salary"))
			category_image_View.setImageResource(R.drawable.salary);
		else if (category.equalsIgnoreCase("gift"))
			category_image_View.setImageResource(R.drawable.gift);
		else if (category.equalsIgnoreCase("Home Expence"))
			category_image_View.setImageResource(R.drawable.home);
		else if (category.equalsIgnoreCase("Lunch/Dinner"))
			category_image_View.setImageResource(R.drawable.meal);
		else if (category.equalsIgnoreCase("Mobile Recharge"))
			category_image_View.setImageResource(R.drawable.recharge);
		else if (category.equalsIgnoreCase("Travelling"))
			category_image_View.setImageResource(R.drawable.plane);
		else if (category.equalsIgnoreCase("Hotel Charge"))
			category_image_View.setImageResource(R.drawable.hotel);
		else
			category_image_View.setImageResource(R.drawable.others);
		categoryNameTextView.setText(category);
		return vi;
	}
}