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

import com.emiratesexpress.R;
import com.emiratesexpress.asynctask.DataDownloadTask;
import com.emiratesexpress.asynctask.StartLocationActivityTask;
import com.emiratesexpress.common.IResponseListener;
import com.emiratesexpress.common.NetworkConstants;
import com.emiratesexpress.common.Utilities;

public class ContactUsActivity extends Activity implements View.OnClickListener {

	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_us);
		context = this;

		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));

		imageView = (ImageView) findViewById(R.id.locationBtn);
		imageView.setOnClickListener(this);

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		button = (Button) findViewById(R.id.sendBtn);
		button.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			finish();
		} else if(id == R.id.locationBtn){
			new StartLocationActivityTask(context).execute();

		} else if (id == R.id.sendBtn) {

			EditText editText = (EditText) findViewById(R.id.name);
			String name = editText.getText().toString();
			editText = (EditText) findViewById(R.id.company);
			String companyName = editText.getText().toString();
			editText = (EditText) findViewById(R.id.email);
			String emailAddress = editText.getText().toString();
			editText = (EditText) findViewById(R.id.description);
			String message = editText.getText().toString();

			String[] array = new String[4];
			array[0] = companyName;
			array[1] = emailAddress;
			array[2] = name;
			array[3] = message;
			
			

			if (Utilities.isArrayValuesEmptyOrNull(array)) {
				Toast.makeText(context, getString(R.string.all_fields_required), Toast.LENGTH_SHORT).show();
				return;
			}
			if (!Utilities.isValidEmail(emailAddress)) {
				Toast.makeText(context, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
				return;
			}

			String postData = makePostData(name, companyName, emailAddress, message);

			new DataDownloadTask(context, new ContactUsResponse(), NetworkConstants.EMIRATES_EXPRESS_CONTACT_US_URL, postData).execute();

//			Toast.makeText(context, "Send Button clicked", Toast.LENGTH_SHORT).show();
		}
	}

	private String makePostData(String name, String companyName, String emailAddress, String message) {
		StringBuilder postData = new StringBuilder();

//		postData.append(NetworkConstants.VIEW);
//		postData.append("=");
//		postData.append(NetworkConstants.VIEW_APP_REGISTER);
//		postData.append("&");
//		postData.append(NetworkConstants.JSON);
//		postData.append("=");
//		postData.append("1");
//		postData.append("&");
		postData.append(NetworkConstants.NAME);
		postData.append("=");
		postData.append(name);
		postData.append("&");
		postData.append(NetworkConstants.COMPANY);
		postData.append("=");
		postData.append(companyName);
		postData.append("&");
		postData.append(NetworkConstants.MESSAGE);
		postData.append("=");
		postData.append(message);
		postData.append("&");
		postData.append(NetworkConstants.EMAIL);
		postData.append("=");
		postData.append(emailAddress);

		return postData.toString();

	}

	private class ContactUsResponse implements IResponseListener {

		@Override
		public void onSuccess(JSONObject response) {
			Toast.makeText(context, getString(R.string.contact_us_send), Toast.LENGTH_SHORT).show();
			finish();
		}

		@Override
		public void onError(JSONObject response) {
			Toast.makeText(context, getString(R.string.contact_us_send), Toast.LENGTH_SHORT).show();
			finish();
		}

	}

	@Override
	protected void onDestroy() {

		context = null;
		Utilities.unbindDrawables(findViewById(R.id.contact_us));

		super.onDestroy();
	}
}