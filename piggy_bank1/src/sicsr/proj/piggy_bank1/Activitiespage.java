package sicsr.proj.piggy_bank1;

import java.util.ArrayList;
import java.util.List;

import proj.entities.Transactions;
import proj.utility.AppConstants;
import proj.utility.Utility;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class Activitiespage extends Fragment {
	protected static final String TAG = "acitivitie_page";
	TextView bal;
	String account, balance;
	ListView transactionListView;
	List<Transactions> transactionList = null;
	private DatabaseHandler datasource;
	LazyAdapter4Activites adapter = null;
	LinearLayout baseLayout;
	SharedPreferences preference = null;
	Button newTransaction;
	RadioGroup viewByGroup;
	RadioButton radioIncome;
	RadioButton radioExpense;
	RadioButton radioAll;
	List<Transactions> incomeList=null;
	List<Transactions> expenseList=null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup containter,
			Bundle savedInstanceState)
	{
		return inflater.inflate(R.layout.activity_activitiespage, containter,
				false);
	}

	@Override
	public void onStart()
	{
		super.onStart();
		viewByGroup=(RadioGroup)getActivity().findViewById(R.id.radioGroup1);
		radioExpense=(RadioButton)getActivity().findViewById(R.id.radioexpense);
		radioIncome=(RadioButton)getActivity().findViewById(R.id.radioincome);
		radioAll=(RadioButton)getActivity().findViewById(R.id.radioall);
		radioAll.setChecked(true);
		// Intent intent = getIntent();
		bal = (TextView) getActivity().findViewById(
				R.id.totaltransactionbalance);
		// variable initialization
		/*
		 * account = intent.getStringExtra("account_name"); balance =
		 * intent.getStringExtra("account_balance");
		 */
		preference = getActivity().getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		account = preference.getString(AppConstants.ACCOUNT, null);
		balance = preference.getString(AppConstants.BALANCE, null);

		newTransaction = (Button) getActivity().findViewById(
				R.id.newtransaction);
		newTransaction.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v)
			{
				Intent intent = new Intent(getActivity()
						.getApplicationContext(), transactionpage.class);
				startActivity(intent);
			}
		});

		if (!(balance.charAt(0) == '-'))
			bal.setTextColor(Color.rgb(36, 147, 51));
		else
			bal.setTextColor(Color.RED);
		transactionListView = (ListView) getActivity().findViewById(R.id.listactivity);
		bal.setText(balance);
		datasource = new DatabaseHandler(getActivity());
		transactionList = datasource.getAllActivities(account,
				AppConstants.ACTIVESTATUS);
		incomeList = new ArrayList<Transactions>();
		expenseList = new ArrayList<Transactions>();
		for (Transactions transaction : transactionList)
		{
			if (transaction.getT_type() == AppConstants.INCOME)
				incomeList.add(transaction);
			else if (transaction.getT_type() == AppConstants.Expense)
				expenseList.add(transaction);
		}
		adapter = new LazyAdapter4Activites(getActivity(), transactionList);

		transactionListView.setAdapter(adapter);
		transactionListView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
					int position, long id)
			{
				Log.v(TAG, String.valueOf(id));
				Transactions transaction = transactionList.get(position);
				transaction.setIsActive(AppConstants.INACTIVESTATUS);
				ContentValues cv = DatabaseHandler
						.setContentValueTransaction(transaction);

				datasource.updateRecord(AppConstants.TABLETRANSACTION, cv,
						DatabaseHandler.KEY_T_ID,
						String.valueOf(transaction.getT_id()));
				Toast.makeText(getActivity().getApplicationContext(),
						"transaction deleted", Toast.LENGTH_SHORT).show();
				transactionList.remove(position);
				adapter.notifyDataSetChanged();
				return true;
			}
		});
		transactionListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3)
			{
				Intent intent = new Intent(getActivity(),
						TransactionDetailActivity.class);
				intent.putExtra(AppConstants.TRANSACTION,
						transactionList.get(position));
				startActivity(intent);
			}
		});

		viewByGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId)
			{
				List<Transactions> transactionViewByList=null;
				switch (checkedId)
				{
				case R.id.radioincome:
					transactionViewByList=incomeList;
					break;
				case R.id.radioexpense:
					transactionViewByList=expenseList;
					break;
				case R.id.radioall:
					transactionViewByList=transactionList;
					break;
				}
				adapter=new LazyAdapter4Activites(getActivity(), transactionViewByList);
				transactionListView.setAdapter(adapter);
			}
		});
	}

	@Override
	public void onResume()
	{
		super.onResume();
		baseLayout = (LinearLayout) getActivity().findViewById(
				R.id.activitiespage_base_layout);
		Utility.setButtonColor(newTransaction, preference);
		Utility.setBackGround(baseLayout, preference);
	}

}
