package com.emiratesexpress.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.apache.http.protocol.HTTP;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;

import com.emiratesexpress.activities.CareersActivity;
import com.emiratesexpress.activities.EmiratesExpressActivity;

public class Utilities {

	public static AlertDialog alertDialog = null;
	public static ProgressDialog progressDialog = null;

	private static BitmapFactory.Options options = null;

	public static HashMap<String, BitmapDrawable> imageMap = new HashMap<String, BitmapDrawable>();

	/**
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getStringValuesFromPreference(Context context, String name, String defaultValue) {

		SharedPreferences myPref = context.getSharedPreferences(CommonConstants.USER_DEFAULT_PREFERENCES, Context.MODE_PRIVATE);
		return myPref.getString(name, defaultValue);

	}

	public static void responseDialog(Context ctx, String title, String errorMessage) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.cancel();
		}
		if (alertDialog != null && alertDialog.isShowing()) {
			alertDialog.cancel();
		}
		alertDialog = new AlertDialog.Builder(ctx).create();
		alertDialog.setTitle(title);
		errorMessage = errorMessage.trim();
		alertDialog.setMessage(errorMessage);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {

			public void onClick(DialogInterface dialog, int which) {
				alertDialog.cancel();
			}

		});
		alertDialog.show();
	}

	public static void showprogressDialog(Context ctx, String errorMessage) {
		if (progressDialog != null && progressDialog.isShowing()) {
			progressDialog.cancel();
		}
		errorMessage = errorMessage.trim();

		progressDialog = new ProgressDialog(ctx);
		progressDialog.setCancelable(false);
		progressDialog.setMessage(errorMessage);

		// ((DJIMainActivity) CommonConstants.DJI_MAIN_ACTIVITY_CONTEXT)
		// .runOnUiThread(new Runnable() {
		//
		// @Override
		// public void run() {
		progressDialog.show();
		// }
		// });
	}

	public static void cancelprogressDialog() {
		if (progressDialog != null && progressDialog.isShowing()) {
			// ((DJIMainActivity) CommonConstants.DJI_MAIN_ACTIVITY_CONTEXT)
			// .runOnUiThread(new Runnable() {
			//
			// @Override
			// public void run() {
			progressDialog.cancel();

			// }
			// });
		}
	}

	/**
	 * @param name
	 * @param value
	 */
	public static void setStringValuesToPreferences(Context context, String name, String value) {
		SharedPreferences myPref = context.getSharedPreferences(CommonConstants.USER_DEFAULT_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPref.edit();

		if (editor != null) {
			editor.putString(name, value);
			editor.commit();
		}
	}

	/**
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBooleanValuesFromPreference(Context context, String name, boolean defaultValue) {

		SharedPreferences myPref = context.getSharedPreferences(CommonConstants.USER_DEFAULT_PREFERENCES, Context.MODE_PRIVATE);
		return myPref.getBoolean(name, defaultValue);

	}

	/**
	 * @param name
	 * @param value
	 */
	public static void setBooleanValuesToPreferences(Context context, String name, boolean value) {
		SharedPreferences myPref = context.getSharedPreferences(CommonConstants.USER_DEFAULT_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPref.edit();

		if (editor != null) {
			editor.putBoolean(name, value);
			editor.commit();
		}
	}

	public static int getIntegerValuesFromPreference(Context context, String name, int defaultValue) {

		SharedPreferences myPref = context.getSharedPreferences(CommonConstants.USER_DEFAULT_PREFERENCES, Context.MODE_PRIVATE);
		return myPref.getInt(name, defaultValue);

	}

	public static void setIntegerValuesToPreferences(Context context, String name, int value) {
		SharedPreferences myPref = context.getSharedPreferences(CommonConstants.USER_DEFAULT_PREFERENCES, Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = myPref.edit();

		if (editor != null) {
			editor.putInt(name, value);
			editor.commit();
		}
	}

	/**
	 * 
	 * Check whether network is connected or not
	 * 
	 * @param context
	 *            Context of application
	 * @return true if connected false otherwise
	 */
	public static boolean isNetworkAvailable(Context context) {

		boolean connected = false;
		ConnectivityManager connectivityManager = null;
		connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connectivityManager.getActiveNetworkInfo();
		if (netInfo != null) {
			connected = netInfo.isConnected();
		}
		return connected;
	}

	public static void createSuccessAlertDialog(Context context, String message) {
		final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(message);
		alertDialog.setButton("Okay", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				alertDialog.cancel();
			}
		});
		alertDialog.setCancelable(false);
		alertDialog.show();
	}

	public static boolean isArrayValuesEmptyOrNull(String[] values) {
		for (int i = 0; i < values.length; i++) {
			if (null == values[i] || "".equals(values[i])) {
				return true;
			}
		}
		return false;
	}

	public final static boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}

	public final static boolean isValidNumber(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.PHONE.matcher(target).matches();
		}
	}

	public static boolean isStringEmptyOrNull(String checkString) {
		// return !(checkString != null && !"".equals(checkString) &&
		// checkString
		// .length() > 0);
		if ("".equals(checkString) || "null".equals(checkString) || null == checkString) {
			return true;
		}

		return false;
	}

	public static void unbindDrawables(View view) {
		if (view != null) {
			if (view.getBackground() != null) {
				view.getBackground().setCallback(null);
				view.getResources().flushLayoutCache();
				view.destroyDrawingCache();
			}
			if (view instanceof ViewGroup) {
				for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
					unbindDrawables(((ViewGroup) view).getChildAt(i));
				}
			} else {
				view = null;
			}
		}
	}

	public static BitmapFactory.Options getBitmapFactoryoptions(int inSampleSize) {
		if (options == null) {
			options = new BitmapFactory.Options();
			options.inPreferredConfig = Bitmap.Config.ARGB_4444;
			options.inDither = true;
			options.inPurgeable = true;
			options.inSampleSize = inSampleSize;
		} else {
			options.inSampleSize = inSampleSize;
		}
		return options;
	}

	public static String readServerResponse(InputStream inputStream) {
		StringBuilder response = new StringBuilder("");
		try {
			char[] buffer = new char[1024];

			try {
				BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, HTTP.UTF_8));
				while (reader.read(buffer) != -1) {
					response.append(buffer);
				}
			} finally {
				if (inputStream != null) {
					inputStream.close();
				}
			}
		} catch (MalformedURLException malFormedExp) {
			malFormedExp.printStackTrace();

		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}

		if (response == null || response.length() <= 0) {
			response = new StringBuilder("");
		}
		return response.toString();
	}

	public static void getLastReknownedGPSLocation() {
		Geocoder geocoder;
		String bestProvider;
		List<Address> user = null;
		double lat;
		double lng;
		String longitude;
		String latitude;
		LocationManager lm = (LocationManager) CommonConstants.EMIRATES_EXPRESS_CONTEXT.getSystemService(Context.LOCATION_SERVICE);

		Criteria criteria = new Criteria();
		bestProvider = lm.getBestProvider(criteria, false);
		Location location = lm.getLastKnownLocation(bestProvider);

		if (location == null) {
//			Toast.makeText(CommonConstants.EMIRATES_EXPRESS_CONTEXT, "Current Location Not found", Toast.LENGTH_LONG).show();
		} else {
			geocoder = new Geocoder(CommonConstants.EMIRATES_EXPRESS_CONTEXT);
			try {
				user = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
				lat = (double) user.get(0).getLatitude();
				lng = (double) user.get(0).getLongitude();
				// Toast.makeText(CommonConstants.DJI_MAIN_ACTIVITY_CONTEXT,
				// " DDD lat: " + lat + ",  longitude: " + lng,
				// Toast.LENGTH_LONG).show();
				longitude = String.valueOf(lng);
				latitude = String.valueOf(lat);

				if ("null".equals(longitude)) {
					longitude = "";
				} else {
					CommonConstants.CURRENT_LONGITUDE = Double.parseDouble(longitude);
				}
				if ("null".equals(latitude)) {
					latitude = "";
				} else {
					CommonConstants.CURRENT_LATITUDE = Double.parseDouble(latitude);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
	}

	public static void updateLocale(String localeCode) {
		Locale locale = new Locale(localeCode);
		Locale.setDefault(locale);
		Configuration config = new Configuration();
		config.locale = locale;
		
		CommonConstants.EMIRATES_EXPRESS_CONTEXT.getApplicationContext().getResources().updateConfiguration(config, null);
	}
	
	public static String getLocale() {
		Configuration config = CommonConstants.EMIRATES_EXPRESS_CONTEXT.getApplicationContext().getResources().getConfiguration();
		Locale locale = config.locale;
		return locale.getLanguage();
		
		
	}
	
	public static void writeOnSdCard(File folderPath, Bitmap bitmap) {
		FileOutputStream out = null;
		try {
			String PROFILE = "career";
			String JPG_STRING = ".jpg";
			String path = folderPath + File.separator + PROFILE + JPG_STRING;
			out = new FileOutputStream(path);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
	}

	public static void restartMainActivity(Context context) {
		((EmiratesExpressActivity)CommonConstants.EMIRATES_EXPRESS_CONTEXT).onCreate(null);
	}
	
	public static void email(Context context, String to, String subject, String body) {
		to = to.trim();
		Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
		String aEmailList[] = { to };
		emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, aEmailList);
		emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject);
		emailIntent.setType("image/jpeg");
		File file = new File(CommonConstants.CAREER_IMAGE_PATH);
		if(file.exists()){
			emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file://"+ CommonConstants.CAREER_IMAGE_PATH));
		}
		emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, body);
		((CareersActivity) context).startActivityForResult(emailIntent,300);
	

	}

}
