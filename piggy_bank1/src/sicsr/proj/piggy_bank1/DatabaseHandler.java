package sicsr.proj.piggy_bank1;

import java.util.ArrayList;
import java.util.List;

import proj.entities.Accounts;
import proj.entities.Transactions;
import proj.utility.AppConstants;
import proj.utility.TableCreationQueries;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

	// All Static variables
	// Database Version
	private static final int DATABASE_VERSION = 1;

	// Database Name
	private static final String DATABASE_NAME = "piggybank";

	// Contacts table name
	private static final String TABLE_ACCOUNTS = "ACCOUNTS";
	private static final String TABLE_TRANSACTIONS = "TRANSACTIONS";

	// Contacts Table Columns names
	private static final String KEY_ID = "ACCOUNT_ID";
	private static final String KEY_NAME = "ACCOUNT_NAME";
	private static final String KEY_balance = "BALANCE";
	private static final String KEY_date = "CREATION_DATE";

	public static final String KEY_T_ID = "T_ID";
	private static final String KEY_T_DATE = "T_DATE";
	private static final String KEY_T_TYPE = "T_TYPE";
	private static final String KEY_T_CATEGORY = "T_CATEGORY";
	private static final String KEY_T_AMOUNT = "T_AMOUNT";
	private static final String KEY_T_ADD_INFO = "T_ADD_INFO";
	private static final String KEY_ISACTIVE = "ISACTIVE";

	private static final String TAG = "DatbaseHandler";

	public DatabaseHandler(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	// Creating Tables
	@Override
	public void onCreate(SQLiteDatabase db) {
		List<String> queries = TableCreationQueries.getQueries();
		for (String query : queries) {
			Log.v(TAG, "Creating table->" + query);
			db.execSQL(query);
		}
		/*String insert = "insert into " + TABLE_ACCOUNTS + "(" + KEY_NAME + ","
				+ KEY_balance + "," + KEY_date+","
				+ KEY_ISACTIVE+") values('default',0000000,"+System.currentTimeMillis()+","+AppConstants.ACTIVESTATUS+")";

		db.execSQL(insert);*/

	}

	// Upgrading database
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// Drop older table if existed
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRANSACTIONS);
		// Create tables again
		onCreate(db);
	}

	/**
	 * All CRUD(Create, Read, Update, Delete) Operations
	 */
	// Adding new Account
	public void createAccount(Accounts account) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, account.getAccount_name());
		values.put(KEY_balance, account.getBalance());
		values.put(KEY_date, account.getCreation_date());
		values.put(KEY_ISACTIVE, AppConstants.ACTIVESTATUS);
		// Inserting Row
		db.insert(TABLE_ACCOUNTS, null, values);
		System.out.println(account.getBalance());
		db.close(); // Closing database connection
	}

	// Adding new Transaction
	public void createTransation(Transactions transaction) {
		SQLiteDatabase db = this.getWritableDatabase();

		ContentValues values = new ContentValues();
		values=setContentValueTransaction(transaction);
		// Inserting Row
		System.out.println(transaction.getT_amount());
		db.insert(TABLE_TRANSACTIONS, null, values);
		db.close(); // Closing database connection

		// System.out.println("createtransaction");
	}
	
	public static ContentValues setContentValueTransaction(Transactions transaction)
	{
		ContentValues values=new ContentValues();
		values.put(KEY_NAME, transaction.getAccount_name());
		values.put(KEY_T_DATE, transaction.getT_date());
		values.put(KEY_T_TYPE, transaction.getT_type());
		values.put(KEY_T_CATEGORY, transaction.getT_category());
		values.put(KEY_T_AMOUNT, transaction.getT_amount());
		values.put(KEY_T_ADD_INFO, transaction.getT_add_info());
		values.put(KEY_ISACTIVE, transaction.getIsActive());
		return values;
	}

	// Getting balance of particular account
	public Accounts getAccount(String name) {
		Accounts account=null;
		String selectQuery = "SELECT * FROM " + TABLE_ACCOUNTS
				+ " WHERE ACCOUNT_NAME='" + name + "'";
		System.out.println(selectQuery);
		SQLiteDatabase db = this.getReadableDatabase();

		Cursor cursor = db.rawQuery(selectQuery, null);
		while(cursor.moveToNext())
		{
			account = new Accounts();
			account=setAccount(cursor);
		}
		
		cursor.close();
		db.close();
		return account;

	}

	/*
	 * // Getting a single account public Accounts getAccount(String accnm) {
	 * SQLiteDatabase db = this.getReadableDatabase();
	 * 
	 * Cursor cursor = db.query(TABLE_ACCOUNTS, new String[] { KEY_ID, KEY_NAME,
	 * KEY_balance, KEY_date }, KEY_NAME + "=?", new String[] {
	 * String.valueOf(accnm) }, null, null, null, null); if (cursor != null)
	 * cursor.moveToFirst();
	 * 
	 * Accounts account = new Accounts(cursor.getString(1), cursor.getString(2),
	 * cursor.getString(3)); // return contact return account; }
	 */
	// Getting All Contacts
	public List<Accounts> getAllAccounts() {
		List<Accounts> accountList = new ArrayList<Accounts>();
		// Select All Query

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(TABLE_ACCOUNTS, null, null, null, null, null,
				null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Accounts account = new Accounts();
				account=setAccount(cursor);
				// Adding contact to list
				accountList.add(account);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return accountList;
	}

	Accounts setAccount(Cursor cursor)
	{
		Accounts account=new Accounts();
		account.setAccount_id(cursor.getLong(cursor.getColumnIndex(KEY_ID)));
		account.setAccount_name(cursor.getString(cursor
				.getColumnIndex(KEY_NAME)));
		account.setBalance(cursor.getDouble(cursor
				.getColumnIndex(KEY_balance)));
		account.setCreation_date(cursor.getLong(cursor
				.getColumnIndex(KEY_date)));
		return account;
	}
	public ArrayList<Transactions> getAllActivities(String accnm,int status) {
		ArrayList<Transactions> TList = new ArrayList<Transactions>();
		// Select All Query
	/*	String selectQuery = "SELECT * FROM " + TABLE_TRANSACTIONS
				+ " WHERE ACCOUNT_NAME='" + accnm + "' ORDER BY T_ID DESC";
		System.out.println(selectQuery);*/
		SQLiteDatabase db = this.getReadableDatabase();
		//Cursor cursor = db.rawQuery(selectQuery, null);
		Cursor cursor=db.query(TABLE_TRANSACTIONS, null, KEY_NAME+"=? AND "+KEY_ISACTIVE+"=?", new String[]{accnm,String.valueOf(status)},null,null, KEY_T_ID+" DESC");
		Transactions transaction = null;
		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				transaction = setTransaction(cursor);
				TList.add(transaction);
			} while (cursor.moveToNext());
		}
		cursor.close();
		db.close();
		return TList;

	}

	Transactions setTransaction(Cursor cursor) {
		Transactions transaction = new Transactions();
		transaction.setT_id(cursor.getLong(cursor.getColumnIndex(KEY_T_ID)));
		transaction.setAccount_name(cursor.getString(cursor
				.getColumnIndex(KEY_NAME)));
		transaction.setT_type(cursor.getInt(cursor.getColumnIndex(KEY_T_TYPE)));
		transaction.setT_add_info(cursor.getString(cursor
				.getColumnIndex(KEY_T_ADD_INFO)));
		transaction.setT_amount(cursor.getDouble(cursor
				.getColumnIndex(KEY_T_AMOUNT)));
		transaction.setT_category(cursor.getString(cursor
				.getColumnIndex(KEY_T_CATEGORY)));
		transaction
				.setT_date(cursor.getLong(cursor.getColumnIndex(KEY_T_DATE)));
		transaction.setIsActive(cursor.getInt(cursor.getColumnIndex(KEY_ISACTIVE)));
		return transaction;
	}

	// Updating single Account
	public int updateAccount(Context context,Accounts account, Transactions transaction) {
		SQLiteDatabase db = this.getWritableDatabase();

		Double balance = account.getBalance();
		Double amount = transaction.getT_amount();
		int type = transaction.getT_type();
		System.out.println(balance);
		if (type==AppConstants.INCOME)
			balance = balance + amount;
		else if (type==AppConstants.Expense)
			balance = balance - amount;

		System.out.println(balance);
		ContentValues values = new ContentValues();
		values.put(KEY_NAME, account.getAccount_name());
		values.put(KEY_balance, balance);
		values.put(KEY_date, account.getCreation_date());
		SharedPreferences pref=context.getSharedPreferences(AppConstants.PIGGYBANKSAHREDPREFERNECE, context.MODE_PRIVATE);
		Editor editor=pref.edit();
		editor.remove(AppConstants.BALANCE);
		editor.putString(AppConstants.BALANCE,String.valueOf(balance));
		editor.commit();
		// updating row
		return db.update(TABLE_ACCOUNTS, values, KEY_NAME + " = ?",
				new String[] { String.valueOf(account.getAccount_name()) });
	}

	// Deleting single record
	public void deleteRecord(String tableName, String key, String keyValue) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.delete(tableName, key + " = ?", new String[] { keyValue });
		db.close();
	}

	public void updateRecord(String tableName,ContentValues cv,String key, String value) {
		SQLiteDatabase db = this.getWritableDatabase();
		long noOfUpdatedRows=db.update(tableName,cv, key + " = ?", new String[] { value });
		Log.v(TAG,"no of rows updated "+noOfUpdatedRows);
		db.close();
	}
	
	public long insertRecord(String table_name, ContentValues cv) {
		SQLiteDatabase db = this.getWritableDatabase();
		long status=db.insert(table_name, null, cv);
		db.close();
		return status;
	}

	public Cursor fetchRecord(String table, String[] columns, String selection,
			String[] selectionArgs, String groupBy, String having,
			String orderBy) {
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(table, columns, selection, selectionArgs,
				groupBy, having, orderBy);
		return cursor;
	}
	public void executeQeury(String query)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL(query);
		db.close();
	}
}
