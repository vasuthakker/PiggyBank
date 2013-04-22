package proj.utility;

import java.util.ArrayList;
import java.util.List;

public class TableCreationQueries {


	private static List<String> quryList=null;
	public static List<String> getQueries()
	{
		quryList=new ArrayList<String>();
		quryList.add("CREATE TABLE IF NOT EXISTS ACCOUNTS(ACCOUNT_ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCOUNT_NAME TEXT UNIQUE,BALANCE TEXT NOT NULL,CREATION_DATE REAL NOT NULL,ISACTIVE INTEGER NOT NULL)");//for account table
		quryList.add("CREATE TABLE IF NOT EXISTS TRANSACTIONS(T_ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCOUNT_NAME TEXT NOT NULL,T_DATE REAL NOT NULL,T_TYPE INTEGER NOT NULL,T_CATEGORY TEXT NOT NULL,T_AMOUNT REAL NOT NULL,T_ADD_INFO TEXT,ISACTIVE INTEGER NOT NULL)");//for transaction table
		quryList.add("CREATE TABLE IF NOT EXISTS BILLS(BILL_ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCOUNT_NAME TEXT NOT NULL,BILL_NO REAL,BILL_NAME TEXT NOT NULL,DUE_DATE REAL,LAST_DATE REAL,BILL_AMOUNT REAL NOT NULL,REMINDER_STATUS INTEGER,IS_PAID INTEGER,ISACTIVE INTEGER NOT NULL,REMINDER_TIME INTEGER)");//for bill table
		quryList.add("CREATE TABLE IF NOT EXISTS LIMITS(LIMIT_ID INTEGER PRIMARY KEY AUTOINCREMENT,ACCOUNT_NAME TEXT NOT NULL,DAILY_LIMIT INTEGER NOT NULL,WEEKLY_LIMIT INTEGER NOT NULL,MONTHLY_LIMIT INTEGER NOT NULL,YEARLY_LIMIT INTEGER NOT NULL)");//for bill table
		return quryList;
	}
	
}
