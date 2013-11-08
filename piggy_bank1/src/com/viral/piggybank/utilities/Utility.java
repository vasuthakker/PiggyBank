package com.viral.piggybank.utilities;

import java.util.Calendar;

import android.content.SharedPreferences;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;

import com.viral.piggybank.R;
import com.viral.piggybank.common.AppConstants;

public class Utility {

	public static String getFormattedDate(long lastCallTimeInMilis) {
		Calendar lastCallTime = Calendar.getInstance();
		lastCallTime.setTimeInMillis(lastCallTimeInMilis);

		Calendar now = Calendar.getInstance();

		final String timeFormatString = "h:mm aa";
		final String dateTimeFormatString = "EEEE, MMMM d, h:mm aa";
		// final long HOURS = 60 * 60 * 60;
		if (now.get(Calendar.DATE) == lastCallTime.get(Calendar.DATE)) {
			return "Today " + DateFormat.format(timeFormatString, lastCallTime);
		} else if (now.get(Calendar.DATE) - lastCallTime.get(Calendar.DATE) == 1) {
			return "Yesterday "
					+ DateFormat.format(timeFormatString, lastCallTime);
		} else if (now.get(Calendar.YEAR) == lastCallTime.get(Calendar.YEAR)) {
			return DateFormat.format(dateTimeFormatString, lastCallTime)
					.toString();
		} else
			return DateFormat.format("MMMM dd yyyy, h:mm aa", lastCallTime)
					.toString();
	}

	public static void setBackGround(View layout, SharedPreferences preferences) {
		SharedPreferences preference = preferences;
		String backgorundColor = preference.getString(
				AppConstants.BACKGROUNDCOLOR, AppConstants.BLUECOLOR);

		if (backgorundColor.equalsIgnoreCase(AppConstants.GREENCOLOR)) {
			layout.setBackgroundResource(R.drawable.green_background);
		} else if (backgorundColor.equalsIgnoreCase(AppConstants.ORANGENCOLOR)) {
			layout.setBackgroundResource(R.drawable.orange_background);
		} else if (backgorundColor.equalsIgnoreCase(AppConstants.PURPLECOLOR)) {
			layout.setBackgroundResource(R.drawable.purple_background);
		} else if (backgorundColor.equalsIgnoreCase(AppConstants.YELLOWCOLOR)) {
			layout.setBackgroundResource(R.drawable.yellow_background);
		} else if (backgorundColor.equalsIgnoreCase(AppConstants.PINKCOLOR)) {
			layout.setBackgroundResource(R.drawable.pink_background);
		} else {
			layout.setBackgroundResource(R.drawable.background);
		}

	}

	public static void setButtonColor(Button button,
			SharedPreferences preferences) {
		SharedPreferences preference = preferences;
		String backgorundColor = preference.getString(
				AppConstants.BACKGROUNDCOLOR, AppConstants.BLUECOLOR);

		if (backgorundColor.equalsIgnoreCase(AppConstants.GREENCOLOR)) {
			button.setBackgroundResource(R.drawable.green_button_bg);
		} else if (backgorundColor.equalsIgnoreCase(AppConstants.ORANGENCOLOR)) {
			button.setBackgroundResource(R.drawable.orange_button_bg);
		} else if (backgorundColor.equalsIgnoreCase(AppConstants.PURPLECOLOR)) {
			button.setBackgroundResource(R.drawable.purple_button_bg);
		} else if (backgorundColor.equalsIgnoreCase(AppConstants.YELLOWCOLOR)) {
			button.setBackgroundResource(R.drawable.yellow_button_bg);
		} else if (backgorundColor.equalsIgnoreCase(AppConstants.PINKCOLOR)) {
			button.setBackgroundResource(R.drawable.pink_button_bg);
		} else {
			button.setBackgroundResource(R.drawable.blue_button_bg);
		}

	}
}
