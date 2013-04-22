package proj.entities;

public class Limits {

	private int _id;
	private String account_name;
	private double daily_limit;
	private double weekly_limit;
	private double monthly_limit;
	private double yearly_limit;
	public int get_id()
	{
		return _id;
	}
	public void set_id(int _id)
	{
		this._id = _id;
	}
	public String getAccount_name()
	{
		return account_name;
	}
	public void setAccount_name(String account_name)
	{
		this.account_name = account_name;
	}
	public double getDaily_limit()
	{
		return daily_limit;
	}
	public void setDaily_limit(double daily_limit)
	{
		this.daily_limit = daily_limit;
	}
	public double getWeekly_limit()
	{
		return weekly_limit;
	}
	public void setWeekly_limit(double weekly_limit)
	{
		this.weekly_limit = weekly_limit;
	}
	public double getMonthly_limit()
	{
		return monthly_limit;
	}
	public void setMonthly_limit(double monthly_limit)
	{
		this.monthly_limit = monthly_limit;
	}
	public double getYearly_limit()
	{
		return yearly_limit;
	}
	public void setYearly_limit(double yearly_limit)
	{
		this.yearly_limit = yearly_limit;
	}
	
}
