package com.emexgr.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.emexgr.R;
import com.emexgr.asynctask.DataDownloadTask;
import com.emexgr.common.IResponseListener;
import com.emexgr.common.NetworkConstants;
import com.emexgr.common.Utilities;
import com.emexgr.network.Parser;

public class ClientsActivity extends Activity implements OnClickListener {

	private Context context;
	private TextView names;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);
		setContentView(R.layout.clients);
		context = this;

		names = (TextView) findViewById(R.id.names);

		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		
		
		StringBuilder postData = new StringBuilder();
		postData.append(NetworkConstants.VIEW);
		postData.append("=");
		postData.append(NetworkConstants.VIEW_APP_CLIENTS);
		postData.append("&");
		postData.append(NetworkConstants.JSON);
		postData.append("=");
		postData.append("1");
		new DataDownloadTask(context, new ClientsResponse(), NetworkConstants.EMIRATES_EXPRESS_URL, postData.toString()).execute();
		

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			finish();
		}
	}

	
	private class ClientsResponse implements IResponseListener {

		@Override
		public void onSuccess(JSONObject response) {
			String namesList = Parser.parseAppClientsResponse(response, context);
			names.setText(namesList);
			
		}

		@Override
		public void onError(JSONObject response) {
			
			
		}
		

	}
	
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();

	}

	@Override
	protected void onPause() {
		overridePendingTransition(R.anim.hold, R.anim.push_out_from_top);
		super.onPause();
	}

	@Override
	protected void onDestroy() {

		context = null;
		Utilities.unbindDrawables(findViewById(R.id.clients));
		super.onDestroy();
	}
}