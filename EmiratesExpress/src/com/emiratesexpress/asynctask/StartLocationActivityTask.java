package com.emiratesexpress.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.emiratesexpress.activities.EmiratesExpressGoogleMapActivity;
import com.emiratesexpress.common.CommonConstants;
import com.emiratesexpress.common.Utilities;


public class StartLocationActivityTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private String languageCode;
	
	
	public StartLocationActivityTask(Context ctx){
		context = ctx;
		
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Utilities.showprogressDialog(context, "Please Wait!");
	}

	@Override
	protected Void doInBackground(Void... params) {
		Utilities.getLastReknownedGPSLocation();
		Intent intent = new Intent(context, EmiratesExpressGoogleMapActivity.class);
		context.startActivity(intent);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Utilities.cancelprogressDialog();
	}
};