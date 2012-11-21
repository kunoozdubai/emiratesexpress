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
import android.widget.Toast;
import android.widget.ToggleButton;

import com.emiratesexpress.R;
import com.emiratesexpress.common.CommonConstants;
import com.emiratesexpress.common.Configurations;
import com.emiratesexpress.common.IResponseListener;
import com.emiratesexpress.common.NetworkConstants;
import com.emiratesexpress.common.Utilities;
import com.emiratesexpress.pojos.User;
import com.uaemerchant.asynctask.DataDownloadTask;
import com.uaemerchant.network.Parser;

public class LoginActivity extends Activity implements View.OnClickListener {

	private Context context;
	private String username;
	private String password;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        context = this;
        
        ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));
        
        Button button = (Button) findViewById(R.id.backBtn);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.loginBtn);
        button.setOnClickListener(this);
//        button = (Button) findViewById(R.id.registerBtn);
//        button.setOnClickListener(this);
        
        
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.backBtn){
			finish();
		} else if(id == R.id.loginBtn){
			EditText editText = (EditText) findViewById(R.id.username);
			username = editText.getText().toString();
			editText = (EditText) findViewById(R.id.password);
			password = editText.getText().toString();
			
			String postData = makePostData(username, password);

			new DataDownloadTask(context, new RegisterResponse(), NetworkConstants.EMIRATES_EXPRESS_URL, postData).execute();

		} else if(id == R.id.registerBtn){
			
			Intent intent = new Intent(context, RegisterActivity.class);
			startActivity(intent);
			finish();
		}
	}
	
	private String makePostData(String username, String password) {
		StringBuilder postData = new StringBuilder();

		postData.append(NetworkConstants.VIEW);
		postData.append("=");
		postData.append(NetworkConstants.VIEW_APP_LOGIN);
		postData.append("&");
		postData.append(NetworkConstants.JSON);
		postData.append("=");
		postData.append("1");
		postData.append("&");
		postData.append(NetworkConstants.USERNAME);
		postData.append("=");
		postData.append(username);
		postData.append("&");
		postData.append(NetworkConstants.PASSWORD);
		postData.append("=");
		postData.append(password);
		postData.append("&");
		
		return postData.toString();

	}

	private class RegisterResponse implements IResponseListener {

		@Override
		public void onSuccess(JSONObject response) {
			Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();
			User user = Parser.parseLoginResponse(response);
			Configurations.user = user;
			
			String userId = user.getUserId();

			if (!Utilities.isStringEmptyOrNull(userId)) {
				Utilities.setStringValuesToPreferences(context, NetworkConstants.USERID, userId);
				Toast.makeText(context, "Registration Successful " + user.getUserId(), Toast.LENGTH_SHORT).show();
				ToggleButton rememberMe = (ToggleButton) findViewById(R.id.rememberMeBtn);
				boolean isRemember = rememberMe.isEnabled(); 
				Utilities.setBooleanValuesToPreferences(context, CommonConstants.REMEMBER_ME, isRemember);
				finish();
			} else {
				Toast.makeText(context, "Registration Failed", Toast.LENGTH_SHORT).show();
			}

		}

		@Override
		public void onError(JSONObject response) {
			Toast.makeText(context, "Error in Registration. Please try again!", Toast.LENGTH_SHORT).show();
		}

	}

	
	@Override
	protected void onDestroy() {

		context = null;
		Utilities.unbindDrawables(findViewById(R.id.login));

		super.onDestroy();
	}
}