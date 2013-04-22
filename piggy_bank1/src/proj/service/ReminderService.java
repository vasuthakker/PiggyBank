package proj.service;

import proj.utility.AppConstants;
import sicsr.proj.piggy_bank1.AlaramActivity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.widget.Toast;

public class ReminderService extends Service {

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{

		// TODO Auto-generated method stub
		Toast.makeText(this, "MyAlarmService.onCreate()", Toast.LENGTH_LONG)
				.show();
		Intent intentApp = new Intent(this, AlaramActivity.class);
		intentApp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		intentApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intentApp.setFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
		intentApp.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		intentApp.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intentApp.addCategory(Intent.CATEGORY_LAUNCHER);

		if (intent.getStringExtra(AppConstants.BILLNAME) != null)
		{
			intentApp.putExtra(AppConstants.BILLNAME,
					intent.getStringExtra(AppConstants.BILLNAME));
			intentApp.putExtra(AppConstants.BILLAMOUNT,
					intent.getStringExtra(AppConstants.BILLAMOUNT));
		}
		PowerManager pm = (PowerManager) getApplicationContext()
				.getSystemService(Context.POWER_SERVICE);
		WakeLock wl = pm.newWakeLock(
				PowerManager.PARTIAL_WAKE_LOCK, "reminder");
		wl.acquire();

		startActivity(intentApp);
		return START_STICKY;
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		// TODO Auto-generated method stub
		Toast.makeText(this, "MyAlarmService.onBind()", Toast.LENGTH_LONG)
				.show();
		return null;
	}

	/*
	 * @Override public void onDestroy() { // TODO Auto-generated method stub
	 * super.onDestroy(); Toast.makeText(this, "MyAlarmService.onDestroy()",
	 * Toast.LENGTH_LONG) .show(); }
	 * 
	 * @Override public void onStart(Intent intent, int startId) { // TODO
	 * Auto-generated method stub super.onStart(intent, startId);
	 * Toast.makeText(this, "MyAlarmService.onStart()", Toast.LENGTH_LONG)
	 * .show(); }
	 * 
	 * @Override public boolean onUnbind(Intent intent) { // TODO Auto-generated
	 * method stub Toast.makeText(this, "MyAlarmService.onUnbind()",
	 * Toast.LENGTH_LONG) .show(); return super.onUnbind(intent); }
	 */
}
