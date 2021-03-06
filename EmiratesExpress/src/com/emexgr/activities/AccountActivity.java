package com.emexgr.activities;

import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.emexgr.R;
import com.emexgr.common.Configurations;
import com.emexgr.common.IResponseListener;
import com.emexgr.common.NetworkConstants;
import com.emexgr.common.Utilities;
import com.emexgr.pojos.User;

public class AccountActivity extends Activity implements View.OnClickListener {

	private Context context;
	private RelativeLayout accoutDetailParent;
	private Button signOutBtn;

	private Builder alertBuilder;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);
		setContentView(R.layout.account);
		context = this;

		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));

		accoutDetailParent = (RelativeLayout) findViewById(R.id.accountDetailParent);

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		button = (Button) findViewById(R.id.trackApplicationBtn);
		button.setOnClickListener(this);

		signOutBtn = (Button) findViewById(R.id.signOutBtn);
		signOutBtn.setOnClickListener(this);
		signOutBtn.setVisibility(View.GONE);

		// if(Configurations.user == null ||
		// Utilities.isStringEmptyOrNull(Configurations.user.getUserId())){
		// showLoginRegisterDialog();
		// }

	}

	@Override
	protected void onResume() {
		if (Configurations.user != null) {
			if (!Utilities.isStringEmptyOrNull(Configurations.user.getUserId())) {
				showUserAccountDetail();
			} else {
				showLoginRegisterDialog();
			}
		} else {
			showLoginRegisterDialog();
		}

		super.onResume();
	}

	@Override
	protected void onPause() {

		overridePendingTransition(R.anim.hold, R.anim.push_out_from_top);
		super.onPause();
	}

	private void showUserAccountDetail() {
		User user = Configurations.user;
		accoutDetailParent.setVisibility(View.VISIBLE);
		TextView textView = (TextView) findViewById(R.id.company);
		textView.setText(user.getCompany());
		if (Configurations.currentLanguage == 1) {
			textView = (TextView) findViewById(R.id.name);
			textView.setText(user.getName());
		} else {
			textView = (TextView) findViewById(R.id.name);
			textView.setText(user.getNameArabic());
		}

		textView = (TextView) findViewById(R.id.email);
		textView.setText(user.getEmail());
		signOutBtn.setVisibility(View.VISIBLE);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			finish();
		} else if (id == R.id.signOutBtn) {
			Configurations.user.setUserId("");
			Configurations.user = null;
			Utilities.setStringValuesToPreferences(context, NetworkConstants.USERID, "");
			finish();
		} else if (id == R.id.trackApplicationBtn) {
			Intent intent = new Intent(context, ApplicationsListActivity.class);
			startActivity(intent);
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
			// Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();
			finish();
		}

		@Override
		public void onError(JSONObject response) {
			// Toast.makeText(context, "onError", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	protected void onDestroy() {

		context = null;
		Utilities.unbindDrawables(findViewById(R.id.account));

		super.onDestroy();
	}

	private void showLoginRegisterDialog() {
		alertBuilder = new Builder(context);

		alertBuilder.setTitle("");
		// alertBuilder.setMessage("Account");
		alertBuilder.setMessage(getString(R.string.account_screen_title));

		alertBuilder.setPositiveButton(getString(R.string.login), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(context, LoginActivity.class);
				startActivity(intent);

			}
		});
		alertBuilder.setNegativeButton(getString(R.string.register), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(context, RegisterActivity.class);
				startActivity(intent);
			}
		});
		alertBuilder.setOnKeyListener(backKeylistener);
		alertBuilder.create().show();
	}

	private OnKeyListener backKeylistener = new OnKeyListener() {

		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_MENU) {
				return true;
			}
			if (keyCode == KeyEvent.KEYCODE_BACK && (event.getAction() == KeyEvent.ACTION_DOWN)) {
				alertBuilder.create().cancel();
				finish();
				return true;
			}

			return false;
		}
	};

}