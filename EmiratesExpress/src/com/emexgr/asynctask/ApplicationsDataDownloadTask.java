package com.emexgr.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.emexgr.R;
import com.emexgr.common.IResponseListener;
import com.emexgr.common.Utilities;
import com.emexgr.network.JSONfunctions;


public class ApplicationsDataDownloadTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private IResponseListener responseListener;
	private JSONObject jsonObject = null;
	String requestURL;
	String postData;
	
	
	public ApplicationsDataDownloadTask(Context ctx, IResponseListener iResponseListener, String requestURL, String postData){
		context = ctx;
		responseListener = iResponseListener;
		this.requestURL = requestURL;
		this.postData = postData;
		
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		Utilities.showprogressDialog(context, context.getString(R.string.loading_please_wait));
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		jsonObject = JSONfunctions.httpPostRequest(requestURL, postData);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		super.onPostExecute(result);
		if(jsonObject != null && jsonObject.length() > 0 ){
			int results = 0; 
				if(!jsonObject.isNull("results")){
					try {
						results = Integer.parseInt(jsonObject.getString("results"));
					} catch (NumberFormatException e) {
						e.printStackTrace();
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				if(results > 0){
					responseListener.onSuccess(jsonObject);
				}else{
					responseListener.onError(jsonObject);
				}
		}else{
			responseListener.onError(jsonObject);
		}
		Utilities.cancelprogressDialog();
	}
};