package com.emexgr.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.emexgr.R;
import com.emexgr.asynctask.DataDownloadTask;
import com.emexgr.asynctask.StartLocationActivityTask;
import com.emexgr.common.IResponseListener;
import com.emexgr.common.NetworkConstants;
import com.emexgr.common.Utilities;

public class ContactUsActivity extends Activity implements View.OnClickListener {

	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);
		setContentView(R.layout.contact_us);
		context = this;

		String titleString = "";
		Bundle extras = getIntent().getExtras();
		titleString = extras.getString("title");

		if (!Utilities.isStringEmptyOrNull(titleString)) {
			TextView title = (TextView) findViewById(R.id.titleTxt);
			title.setText(titleString);
		}

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
		} else if (id == R.id.locationBtn) {
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
			editText = (EditText) findViewById(R.id.mobile);
			String mobile = editText.getText().toString();

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
			if (!Utilities.isStringEmptyOrNull(mobile)) {
				if (!Utilities.isValidNumber(mobile)) {
					Toast.makeText(context, getString(R.string.invalid_mobile), Toast.LENGTH_SHORT).show();
					return;
				}
			}

			String postData = makePostData(name, companyName, emailAddress, message, mobile);

			new DataDownloadTask(context, new ContactUsResponse(), NetworkConstants.EMIRATES_EXPRESS_CONTACT_US_URL, postData).execute();

			// Toast.makeText(context, "Send Button clicked",
			// Toast.LENGTH_SHORT).show();
		}
	}

	private String makePostData(String name, String companyName, String emailAddress, String message, String mobile) {
		StringBuilder postData = new StringBuilder();

		// postData.append(NetworkConstants.VIEW);
		// postData.append("=");
		// postData.append(NetworkConstants.VIEW_APP_REGISTER);
		// postData.append("&");
		// postData.append(NetworkConstants.JSON);
		// postData.append("=");
		// postData.append("1");
		// postData.append("&");
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
		postData.append("&");
		postData.append(NetworkConstants.MOBILE);
		postData.append("=");
		postData.append(mobile);
		
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
	protected void onPause() {

		overridePendingTransition(R.anim.hold, R.anim.push_out_from_top);
		super.onPause();
	}

	@Override
	protected void onDestroy() {

		context = null;
		Utilities.unbindDrawables(findViewById(R.id.contact_us));

		super.onDestroy();
	}
}