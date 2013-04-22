package proj.entities;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Transactions implements Serializable {
	private long t_id;
	private String account_name;
	private long t_date;
	private int t_type;
	private String t_category;
	private double t_amount;
	private String t_add_info;
	private int isActive;
	
	public Transactions() {
		// TODO Auto-generated constructor stub
	}
	public long getT_id() {
		return t_id;
	}
	public void setT_id(long t_id) {
		this.t_id = t_id;
	}
	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}

	public String getT_category() {
		return t_category;
	}
	public void setT_category(String t_category) {
		this.t_category = t_category;
	}
	public String getT_add_info() {
		return t_add_info;
	}
	public void setT_add_info(String t_add_info) {
		this.t_add_info = t_add_info;
	}
	
	public Double getT_amount() {
		return t_amount;
	}
	public void setT_amount(double t_amount) {
		this.t_amount = t_amount;
	}
	public long getT_date() {
		return t_date;
	}
	public void setT_date(long t_date) {
		this.t_date = t_date;
	}
	public int getT_type() {
		return t_type;
	}
	public void setT_type(int t_type) {
		this.t_type = t_type;
	}
	public int getIsActive()
	{
		return isActive;
	}
	public void setIsActive(int isActive)
	{
		this.isActive = isActive;
	}

}
