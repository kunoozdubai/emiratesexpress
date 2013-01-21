package com.emiratesexpress.asynctask;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.RelativeLayout;

import com.emiratesexpress.R;
import com.emiratesexpress.common.CommonConstants;
import com.emiratesexpress.common.Utilities;


public class RestartingApplicationTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private String languageCode;
	private RelativeLayout mainActivityParentView;
	
	public RestartingApplicationTask(Context ctx, String languageCode, RelativeLayout mainActivityParentView){
		context = ctx;
		this.languageCode = languageCode;
		this.mainActivityParentView = mainActivityParentView;
		
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Utilities.showprogressDialog(context, context.getString(R.string.applying_settings));
	}

	@Override
	protected Void doInBackground(Void... params) {
		Utilities.updateLocale(languageCode);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		Utilities.restartMainActivity(CommonConstants.EMIRATES_EXPRESS_CONTEXT, mainActivityParentView);
		Utilities.cancelprogressDialog();
	}
};