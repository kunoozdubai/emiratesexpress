package com.uaemerchant.asynctask;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;

import com.emiratesexpress.common.IResponseListener;
import com.emiratesexpress.common.Utilities;
import com.uaemerchant.network.JSONfunctions;


public class DataDownloadTask extends AsyncTask<Void, Void, Void> {

	private Context context;
	private IResponseListener responseListener;
	private JSONObject jsonObject = null;
	String requestURL;
	String postData;
	
	
	public DataDownloadTask(Context ctx, IResponseListener iResponseListener, String requestURL, String postData){
		context = ctx;
		responseListener = iResponseListener;
		this.requestURL = requestURL;
		this.postData = postData;
		
	}
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
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
			if(!jsonObject.has("ERROR")){
				responseListener.onSuccess(jsonObject);
			}else{
				responseListener.onError(jsonObject);
			}
//				String error = jsonObject.getString("ERROR");
//				if(Utilities.isStringEmptyOrNull(error)){
//					responseListener.onSuccess(jsonObject);
//				}else{
//					responseListener.onError(jsonObject);
//				}
			
		}else{
			responseListener.onError(null);
		}
		Utilities.cancelprogressDialog();
	}
};