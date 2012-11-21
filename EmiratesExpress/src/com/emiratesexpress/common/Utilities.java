package com.emiratesexpress.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.HashMap;

import org.apache.http.protocol.HTTP;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewGroup;

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

	
}
