package com.viral.piggybank.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Bill implements Serializable{

	private int _ID;
	private String account_name;
	private long bill_no;
	private String bill_name;
	private long due_Date;
	private long last_Date;
	private double bill_amount;
	private int reminder_status;
	private int isPaid;
	private int isActive;
	private long reminder_time;
	
	public int get_ID() {
		return _ID;
	}
	public void set_ID(int _ID) {
		this._ID = _ID;
	}
	
	public String getBill_name() {
		return bill_name;
	}
	public void setBill_name(String bill_name) {
		this.bill_name = bill_name;
	}

	public double getBill_amount() {
		return bill_amount;
	}
	public void setBill_amount(double bill_amount) {
		this.bill_amount = bill_amount;
	}
	public int getReminder_status() {
		return reminder_status;
	}
	public void setReminder_status(int reminder_status) {
		this.reminder_status = reminder_status;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	public int getIsPaid() {
		return isPaid;
	}
	public void setIsPaid(int isPaid) {
		this.isPaid = isPaid;
	}
	public long getBill_no() {
		return bill_no;
	}
	public void setBill_no(long bill_no) {
		this.bill_no = bill_no;
	}
	public long getDue_Date() {
		return due_Date;
	}
	public void setDue_Date(long due_Date) {
		this.due_Date = due_Date;
	}
	public long getLast_Date() {
		return last_Date;
	}
	public void setLast_Date(long last_Date) {
		this.last_Date = last_Date;
	}
	public int getIsActive()
	{
		return isActive;
	}
	public void setIsActive(int isActive)
	{
		this.isActive = isActive;
	}
	public long getReminder_time()
	{
		return reminder_time;
	}
	public void setReminder_time(long reminder_time)
	{
		this.reminder_time = reminder_time;
	}
	}
