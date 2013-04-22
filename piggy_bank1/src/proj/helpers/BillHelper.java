package proj.helpers;

import java.util.ArrayList;
import java.util.List;

import proj.entities.Bill;
import sicsr.proj.piggy_bank1.DatabaseHandler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
/**
 * Helper class for Billing information
 * @author Viral
 *
 */
public class BillHelper {

	private static final String TABLE_NAME = "BILLS";

	private static final String KEY_ID = "BILL_ID";
	private static final String KEY_ACCOUNT_NAME = "ACCOUNT_NAME";
	private static final String KEY_BILL_NO = "BILL_NO";
	private static final String KEY_BILL_NAME = "BILL_NAME";
	private static final String KEY_DUE_DATE = "DUE_DATE";
	private static final String KEY_LAST_DATE = "LAST_DATE";
	private static final String KEY_BILL_AMOUNT = "BILL_AMOUNT";
	private static final String KEY_REMINDER_STATUS = "REMINDER_STATUS";
	private static final String KEY_IS_PAID = "IS_PAID";
	private static final String KEY_ISACTIVE = "ISACTIVE";
	private static final String KEY_REMINDER_TIME = "REMINDER_TIME";

	private static final String TAG = "bill_helper";

	public static void addBill(Context context, Bill bill)
	{
		try
		{
			Log.v(TAG, "inserting a record");
			ContentValues cv = setContentValuesForBill(bill);
			DatabaseHandler db = new DatabaseHandler(context);
			long status = db.insertRecord(TABLE_NAME, cv);
			Log.v(TAG, "bill inserted with status " + status);
		} catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static List<Bill> fetchBill(Context context, String accountName,
			int status)
	{
		List<Bill> billList = new ArrayList<Bill>();
		try
		{
			DatabaseHandler db = new DatabaseHandler(context);
			Cursor cursor = db.fetchRecord(TABLE_NAME, null, KEY_ACCOUNT_NAME
					+ "=? AND " + KEY_ISACTIVE + "=?", new String[] {
					accountName, String.valueOf(status) }, null, null, KEY_ID
					+ " DESC");
			Bill bill = null;
			while (cursor.moveToNext())
			{
				bill = new Bill();
				bill.set_ID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				bill.setAccount_name(cursor.getString(cursor
						.getColumnIndex(KEY_ACCOUNT_NAME)));
				bill.setBill_amount(cursor.getDouble(cursor
						.getColumnIndex(KEY_BILL_AMOUNT)));
				bill.setBill_name(cursor.getString(cursor
						.getColumnIndex(KEY_BILL_NAME)));
				bill.setBill_no(cursor.getLong(cursor
						.getColumnIndex(KEY_BILL_NO)));
				bill.setDue_Date(cursor.getLong(cursor
						.getColumnIndex(KEY_DUE_DATE)));
				bill.setLast_Date(cursor.getLong(cursor
						.getColumnIndex(KEY_LAST_DATE)));
				bill.setReminder_status(cursor.getInt(cursor
						.getColumnIndex(KEY_REMINDER_STATUS)));
				bill.setIsPaid(cursor.getInt(cursor.getColumnIndex(KEY_IS_PAID)));
				bill.setIsActive(cursor.getInt(cursor
						.getColumnIndex(KEY_ISACTIVE)));
				bill.setReminder_time(cursor.getLong(cursor
						.getColumnIndex(KEY_REMINDER_TIME)));
				billList.add(bill);
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return billList;
	}

	/*public static Bill fetchBillByName(Context context, String billName, int status)
	{
		Bill bill = null;
		try
		{
			DatabaseHandler db = new DatabaseHandler(context);
			Cursor cursor = db.fetchRecord(
					TABLE_NAME,
					null,
					KEY_ID + "=? AND " + KEY_ISACTIVE + "=?",
					new String[] { String.valueOf(billName),
							String.valueOf(status) }, null, null, KEY_ID
							+ " DESC");
			while (cursor.moveToNext())
			{
				bill = new Bill();
				bill.set_ID(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				bill.setAccount_name(cursor.getString(cursor
						.getColumnIndex(KEY_ACCOUNT_NAME)));
				bill.setBill_amount(cursor.getDouble(cursor
						.getColumnIndex(KEY_BILL_AMOUNT)));
				bill.setBill_name(cursor.getString(cursor
						.getColumnIndex(KEY_BILL_NAME)));
				bill.setBill_no(cursor.getLong(cursor
						.getColumnIndex(KEY_BILL_NO)));
				bill.setDue_Date(cursor.getLong(cursor
						.getColumnIndex(KEY_DUE_DATE)));
				bill.setLast_Date(cursor.getLong(cursor
						.getColumnIndex(KEY_LAST_DATE)));
				bill.setReminder_status(cursor.getInt(cursor
						.getColumnIndex(KEY_REMINDER_STATUS)));
				bill.setIsPaid(cursor.getInt(cursor.getColumnIndex(KEY_IS_PAID)));
				bill.setIsActive(cursor.getInt(cursor
						.getColumnIndex(KEY_ISACTIVE)));
				bill.setReminder_time(cursor.getLong(cursor
						.getColumnIndex(KEY_REMINDER_TIME)));
			}
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return bill;
	}*/

	private static ContentValues setContentValuesForBill(Bill bill)
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_ACCOUNT_NAME, bill.getAccount_name());
		cv.put(KEY_BILL_NO, bill.getBill_no());
		cv.put(KEY_BILL_NAME, bill.getBill_name());
		cv.put(KEY_DUE_DATE, bill.getDue_Date());
		cv.put(KEY_LAST_DATE, bill.getLast_Date());
		cv.put(KEY_BILL_AMOUNT, bill.getBill_amount());
		cv.put(KEY_REMINDER_STATUS, bill.getReminder_status());
		cv.put(KEY_IS_PAID, bill.getIsPaid());
		cv.put(KEY_ISACTIVE, bill.getIsActive());
		cv.put(KEY_REMINDER_TIME, bill.getReminder_time());
		return cv;
	}

	public static void updateBill(Context context, Bill bill)
	{
		DatabaseHandler db = new DatabaseHandler(context);
		ContentValues cv = setContentValuesForBill(bill);
		db.updateRecord(TABLE_NAME, cv, KEY_ID, String.valueOf(bill.get_ID()));
	}

}
