package com.emexgr.asynctask;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import com.emexgr.R;
import com.emexgr.activities.EmiratesExpressGoogleMapActivity;
import com.emexgr.common.Utilities;


public class StartLocationActivityTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private String languageCode;
	
	
	public StartLocationActivityTask(Context ctx){
		context = ctx;
		
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Utilities.showprogressDialog(context, context.getString(R.string.loading_please_wait));
	}

	@Override
	protected Void doInBackground(Void... params) {
		Utilities.getLastReknownedGPSLocation();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Intent intent = new Intent(context, EmiratesExpressGoogleMapActivity.class);
		context.startActivity(intent);
		Utilities.cancelprogressDialog();
	}
};