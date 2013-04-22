package proj.helpers;

import java.util.ArrayList;
import java.util.List;

import proj.entities.Limits;
import sicsr.proj.piggy_bank1.DatabaseHandler;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

public class LimitsHelper {

	public static final String TABLE_NAME = "LIMITS";

	public static final String KEY_ID = "LIMIT_ID";
	public static final String KEY_ACCOUNT_NAME = "ACCOUNT_NAME";
	public static final String KEY_DAILY_LIMIT = "DAILY_LIMIT";
	public static final String KEY_WEEKLY_LIMIT = "WEEKLY_LIMIT";
	public static final String KEY_MONTHLY_LIMIT = "MONTHLY_LIMIT";
	public static final String KEY_YEARLY_LIMIT = "YEARLY_LIMIT";

	private static final String TAG = "LIMITS_HELPER";

	public static void addLimit(Context context, Limits limit)
	{
		DatabaseHandler db = new DatabaseHandler(context);
		ContentValues cv = setLimtiContentValue(limit);
		db.insertRecord(TABLE_NAME, cv);
		db.close();
	}

	private static ContentValues setLimtiContentValue(Limits limit)
	{
		ContentValues cv = new ContentValues();
		cv.put(KEY_ACCOUNT_NAME, limit.getAccount_name());
		cv.put(KEY_DAILY_LIMIT, limit.getDaily_limit());
		cv.put(KEY_WEEKLY_LIMIT, limit.getWeekly_limit());
		cv.put(KEY_MONTHLY_LIMIT, limit.getMonthly_limit());
		cv.put(KEY_YEARLY_LIMIT, limit.getYearly_limit());
		return cv;
	}

	public static List<Limits> fetchLimit(final Context context, String accountName)
	{
		List<Limits> limitList = new ArrayList<Limits>();
		DatabaseHandler db = new DatabaseHandler(context);
		try
		{

			Cursor cursor = db.fetchRecord(TABLE_NAME, null, KEY_ACCOUNT_NAME
					+ "=?", new String[] { accountName }, null, null, null);
			while (cursor.moveToNext())
			{
				Limits limit = new Limits();
				limit.set_id(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
				limit.setAccount_name(cursor.getString(cursor
						.getColumnIndex(KEY_ACCOUNT_NAME)));
				limit.setDaily_limit(cursor.getDouble(cursor
						.getColumnIndex(KEY_DAILY_LIMIT)));
				limit.setWeekly_limit(cursor.getDouble(cursor
						.getColumnIndex(KEY_WEEKLY_LIMIT)));
				limit.setMonthly_limit(cursor.getDouble(cursor
						.getColumnIndex(KEY_MONTHLY_LIMIT)));
				limit.setYearly_limit(cursor.getDouble(cursor
						.getColumnIndex(KEY_YEARLY_LIMIT)));
				limitList.add(limit);
			}
			cursor.close();
		} catch (Exception e)
		{
			Log.e(TAG,e.getMessage());
		} finally
		{
			db.close();
		}
		return limitList;
	}

	public static void updateLimits(Context context,Limits limit)
	{
		DatabaseHandler db = new DatabaseHandler(context);
		try
		{
		ContentValues cv=setLimtiContentValue(limit);
		db.updateRecord(TABLE_NAME, cv, KEY_ACCOUNT_NAME, limit.getAccount_name());
		}
		catch(Exception e)
		{
			Log.e(TAG,e.getMessage());
		}
		
	}
}
