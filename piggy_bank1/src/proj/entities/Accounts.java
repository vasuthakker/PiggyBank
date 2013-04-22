package proj.entities;

public class Accounts {
	private long account_id;
	private String account_name;
	private double balance;
	private long creation_date;
	private int isActive;
	
	public Accounts(String account_name,double balance,long creation_date) {
		// TODO Auto-generated constructor stub
		this.account_name=account_name;
		this.setBalance(balance);
		this.setCreation_date(creation_date);
	}
	
	public Accounts() {
		// TODO Auto-generated constructor stub
	}

	public String getAccount_name() {
		return account_name;
	}
	public void setAccount_name(String account_name) {
		this.account_name = account_name;
	}
	
	
	public long getAccount_id() {
		return account_id;
	}
	public void setAccount_id(long account_id) {
		this.account_id = account_id;
	}

	public long getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(long creation_date) {
		this.creation_date = creation_date;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
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
