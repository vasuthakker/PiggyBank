package com.viral.piggybank.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.viral.piggybank.R;
import com.viral.piggybank.common.AppConstants;
import com.viral.piggybank.common.DatabaseHandler;
import com.viral.piggybank.entities.Transactions;
import com.viral.piggybank.utilities.Utility;

public class DeletedTransactionsActivity extends Activity {
	DatabaseHandler db;
	ListView deletedTransactionListView;
	ArrayList<Transactions> transactionList = null;
	TransactionListAdapter activitiesAdapter = null;
	RelativeLayout baseLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_deleted_transactions);
		db = new DatabaseHandler(this);
		SharedPreferences preference = getSharedPreferences(
				AppConstants.PIGGYBANKSAHREDPREFERNECE, Context.MODE_PRIVATE);
		String accountName = preference.getString(AppConstants.ACCOUNT, "");
		baseLayout = (RelativeLayout) findViewById(R.id.delted_transaction_base_layout);
		Utility.setBackGround(baseLayout, preference);
		deletedTransactionListView = (ListView) findViewById(R.id.deleted_transactions_list_2);
		transactionList = db.getAllActivities(accountName,
				AppConstants.INACTIVESTATUS);
		if (transactionList == null) {
			transactionList = new ArrayList<Transactions>();
		}
		if (transactionList.size() > 0) {
			activitiesAdapter = new TransactionListAdapter(this, transactionList);
			deletedTransactionListView.setAdapter(activitiesAdapter);
			deletedTransactionListView
					.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int position, long arg3) {
							Intent intent = new Intent(getApplicationContext(),
									TransactionDetailActivity.class);
							intent.putExtra(AppConstants.TRANSACTION,
									transactionList.get(position));
							startActivity(intent);
						}
					});

			deletedTransactionListView
					.setOnItemLongClickListener(new OnItemLongClickListener() {

						@Override
						public boolean onItemLongClick(AdapterView<?> arg0,
								View arg1, int position, long arg3) {
							Transactions transaction = transactionList
									.get(position);
							transaction.setIsActive(AppConstants.ACTIVESTATUS);
							ContentValues cv = DatabaseHandler
									.setContentValueTransaction(transaction);

							db.updateRecord(AppConstants.TABLETRANSACTION, cv,
									DatabaseHandler.KEY_T_ID,
									String.valueOf(transaction.getT_id()));
							Toast.makeText(getApplicationContext(),
									"transaction restored", Toast.LENGTH_SHORT)
									.show();
							transactionList.remove(position);
							activitiesAdapter.notifyDataSetChanged();
							return true;
						}
					});
		}

	}

}
