package com.emiratesexpress.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.emiratesexpress.R;
import com.emiratesexpress.common.Configurations;
import com.emiratesexpress.common.IResponseListener;
import com.emiratesexpress.common.NetworkConstants;
import com.emiratesexpress.common.Utilities;
import com.emiratesexpress.pojos.User;
import com.uaemerchant.asynctask.DataDownloadTask;
import com.uaemerchant.network.Parser;

public class RegisterActivity extends Activity implements OnClickListener {

	private Context context;
	private String username;
	private String password;
	private String companyName;
	private String emailAddress;
	private String name;
	private String nameArabic;


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);
		context = this;

		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		button = (Button) findViewById(R.id.registerBtn);
		button.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			finish();
		} else if (id == R.id.registerBtn) {

			EditText editText = (EditText) findViewById(R.id.username);
			username = editText.getText().toString();
			editText = (EditText) findViewById(R.id.password);
			password = editText.getText().toString();
			editText = (EditText) findViewById(R.id.confirmPassword);
			String confirmPassword = editText.getText().toString();
			editText = (EditText) findViewById(R.id.companyName);
			companyName = editText.getText().toString();
			editText = (EditText) findViewById(R.id.emailAddress);
			emailAddress = editText.getText().toString();
			editText = (EditText) findViewById(R.id.name);
			name = editText.getText().toString();
			editText = (EditText) findViewById(R.id.nameArabic);
			nameArabic = editText.getText().toString();

			String[] array = new String[7];
			array[0] = username;
			array[1] = password;
			array[2] = confirmPassword;
			array[3] = companyName;
			array[4] = emailAddress;
			array[5] = name;
			array[6] = nameArabic;

			if (Utilities.isArrayValuesEmptyOrNull(array)) {
				Toast.makeText(context, "All fields are mendatory", Toast.LENGTH_SHORT).show();
				return;
			}

			if (!confirmPassword.equals(password)) {
				Toast.makeText(context, "Password fields do not match", Toast.LENGTH_SHORT).show();
				return;
			}
			if (!Utilities.isValidEmail(emailAddress)) {
				Toast.makeText(context, "Email address not valid", Toast.LENGTH_SHORT).show();
				return;
			}

			String postData = makePostData(username, password, confirmPassword, companyName, emailAddress, name, nameArabic);

			new DataDownloadTask(context, new RegisterResponse(), NetworkConstants.EMIRATES_EXPRESS_URL, postData).execute();

			Toast.makeText(context, "Register Button clicked", Toast.LENGTH_SHORT).show();
		}
	}

	private String makePostData(String username, String password, String confirmPassword, String companyName, String emailAddress, String name,
			String nameArabic) {
		StringBuilder postData = new StringBuilder();

		postData.append(NetworkConstants.VIEW);
		postData.append("=");
		postData.append(NetworkConstants.VIEW_APP_REGISTER);
		postData.append("&");
		postData.append(NetworkConstants.JSON);
		postData.append("=");
		postData.append("1");
		postData.append("&");
		postData.append(NetworkConstants.NAME);
		postData.append("=");
		postData.append(name);
		postData.append("&");
		postData.append(NetworkConstants.NAME_AR);
		postData.append("=");
		postData.append(nameArabic);
		postData.append("&");
		postData.append(NetworkConstants.USERNAME);
		postData.append("=");
		postData.append(username);
		postData.append("&");
		postData.append(NetworkConstants.PASSWORD);
		postData.append("=");
		postData.append(password);
		postData.append("&");
		postData.append(NetworkConstants.C_PASSWORD);
		postData.append("=");
		postData.append(confirmPassword);
		postData.append("&");
		postData.append(NetworkConstants.EMAIL);
		postData.append("=");
		postData.append(emailAddress);

		return postData.toString();

	}

	private class RegisterResponse implements IResponseListener {

		@Override
		public void onSuccess(JSONObject response) {
			Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();
			String userId = Parser.parseRegisterationResponse(response);
			if (!Utilities.isStringEmptyOrNull(userId)) {
				Utilities.setStringValuesToPreferences(context, NetworkConstants.USERID, userId);
				Toast.makeText(context, "Registration Successful " + userId, Toast.LENGTH_SHORT).show();
				User user = new User(userId, name, nameArabic, username, password, emailAddress, "", "", "", "");
				Configurations.user = user;
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
		Utilities.unbindDrawables(findViewById(R.id.register));

		super.onDestroy();
	}
}