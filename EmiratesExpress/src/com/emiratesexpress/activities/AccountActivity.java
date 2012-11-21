package com.emiratesexpress.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emiratesexpress.R;
import com.emiratesexpress.common.Configurations;
import com.emiratesexpress.common.IResponseListener;
import com.emiratesexpress.common.NetworkConstants;
import com.emiratesexpress.common.Utilities;
import com.emiratesexpress.pojos.User;
import com.uaemerchant.asynctask.DataDownloadTask;

public class AccountActivity extends Activity implements View.OnClickListener {

	private Context context;
	private RelativeLayout accoutDetailParent;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);
		context = this;

		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));
		
		accoutDetailParent =  (RelativeLayout) findViewById(R.id.accountDetailParent);

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		button = (Button) findViewById(R.id.signOutBtn);
		button.setOnClickListener(this);
		button = (Button) findViewById(R.id.trackApplicationBtn);
		button.setOnClickListener(this);

	}

	
	@Override
	protected void onResume() {
		
		if(!Utilities.isStringEmptyOrNull(Configurations.user.getUserId())){
			showUserAccountDetail();
		}
	
		super.onResume();
	}
	private void showUserAccountDetail() {
		User user = Configurations.user;
		accoutDetailParent.setVisibility(View.VISIBLE);
		TextView textView = (TextView) findViewById(R.id.nameText);
		textView.setText(user.getName());
		textView = (TextView) findViewById(R.id.usernameText);
		textView.setText(user.getUsername());
		textView = (TextView) findViewById(R.id.emailText);
		textView.setText(user.getEmail());
		
	}


	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			finish();
		} else if(id == R.id.signOutBtn){
			Configurations.user = null;
			
			finish();
		} else if (id == R.id.trackApplicationBtn) {
		}
	}

	private String makePostData(String userId) {
		StringBuilder postData = new StringBuilder();

		postData.append(NetworkConstants.VIEW);
		postData.append("=");
		postData.append(NetworkConstants.VIEW_APP_REGISTER);
		postData.append("&");
		postData.append(NetworkConstants.JSON);
		postData.append("=");
		postData.append("1");
		postData.append("&");
		postData.append(NetworkConstants.USERID);
		postData.append("=");
		postData.append(userId);
		postData.append("&");
		
		return postData.toString();

	}

	private class TrackApplicationResponse implements IResponseListener {

		@Override
		public void onSuccess(JSONObject response) {
			Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();
			finish();
		}

		@Override
		public void onError(JSONObject response) {
			Toast.makeText(context, "onError", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	protected void onDestroy() {

		context = null;
		Utilities.unbindDrawables(findViewById(R.id.contact_us));

		super.onDestroy();
	}
}