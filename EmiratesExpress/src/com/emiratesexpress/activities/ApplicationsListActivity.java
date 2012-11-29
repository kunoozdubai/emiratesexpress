package com.emiratesexpress.activities;

import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.emiratesexpress.R;
import com.emiratesexpress.adapters.ApplicationsListViewAdapter;
import com.emiratesexpress.asynctask.ApplicationsDataDownloadTask;
import com.emiratesexpress.common.IResponseListener;
import com.emiratesexpress.common.NetworkConstants;
import com.emiratesexpress.common.Utilities;
import com.emiratesexpress.network.Parser;
import com.emiratesexpress.pojos.Applications;

// society
public class ApplicationsListActivity extends Activity implements OnClickListener, OnItemClickListener {

	public static Context context;
	private static ListView myList;
	private ApplicationsListViewAdapter adapter = null;

	public static ArrayList<Applications> applicationsArrayList = null;

	private RelativeLayout mainParent;
	private RelativeLayout staticContentParent;
	private TextView titleTxt;
	private boolean staticContentShowing = false;
	

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.applications_list_activity);
		context = this;
		applicationsArrayList = new ArrayList<Applications>();


		mainParent = (RelativeLayout) findViewById(R.id.mainParent);
		staticContentParent = (RelativeLayout) findViewById(R.id.staticContentParent);
		titleTxt = (TextView) findViewById(R.id.titleTxt);

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);

		ImageView imageView = (ImageView) findViewById(R.id.mainBackgroundImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));
		
		String userId = Utilities.getStringValuesFromPreference(context, NetworkConstants.USERID, "");
		String postData = makePostData(userId);
		
		new ApplicationsDataDownloadTask(context, new ApplicationsResponse(), NetworkConstants.EMIRATES_EXPRESS_URL, postData).execute();
	}

	private String makePostData(String userId) {
		StringBuilder postData = new StringBuilder();

		postData.append(NetworkConstants.VIEW);
		postData.append("=");
		postData.append(NetworkConstants.VIEW_APP_MEMBER_TRANSACTIONS);
		postData.append("&");
		postData.append(NetworkConstants.JSON);
		postData.append("=");
		postData.append("1");
		postData.append("&");
		postData.append(NetworkConstants.USERID);
		postData.append("=");
		postData.append(userId);

		return postData.toString();
	}

	@Override
	protected void onDestroy() {
		Utilities.unbindDrawables(findViewById(R.id.applicationsListActivity));
		System.gc();
		super.onDestroy();
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
		Applications applicationObject = (Applications) adapter.getItem(position);

		mainParent.setVisibility(View.INVISIBLE);
		staticContentParent.setVisibility(View.VISIBLE);
		staticContentShowing = true;
		
//		titleTxt.setText(getString(R.string.applications_detail_screen_title));
		
		TextView textView = (TextView) findViewById(R.id.transaction);
		textView.setText(applicationObject.getTransaction());
		textView = (TextView) findViewById(R.id.authority);
		textView.setText(applicationObject.getAuthority());
		textView = (TextView) findViewById(R.id.status);
		textView.setText(applicationObject.getStatus());
		textView = (TextView) findViewById(R.id.serviceFee);
		textView.setText(applicationObject.getServiceFee());
		textView = (TextView) findViewById(R.id.govtFee);
		textView.setText(applicationObject.getGovtFee());
		textView = (TextView) findViewById(R.id.debit);
		textView.setText(applicationObject.getDebit());
		textView = (TextView) findViewById(R.id.oldBalance);
		textView.setText(applicationObject.getOldBalance());
//		textView = (TextView) findViewById(R.id.comments);
//		textView.setText(applicationObject.getComments());
//		textView = (TextView) findViewById(R.id.description);
//		textView.setText(applicationObject.getDescription());
		
		
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			if (staticContentShowing) {
				mainParent.setVisibility(View.VISIBLE);
				staticContentParent.setVisibility(View.GONE);
//				titleTxt.setText(getString(R.string.applications_screen_title));
				staticContentShowing = false;
			} else {
				finish();
			}
		}
	}

	private class ApplicationsResponse implements IResponseListener {

		@Override
		public void onSuccess(JSONObject response) {
			Toast.makeText(context, "onSuccess", Toast.LENGTH_SHORT).show();
			applicationsArrayList = Parser.parseApplicationResponse(response);
			adapter = new ApplicationsListViewAdapter(context, applicationsArrayList);
			myList = (ListView) findViewById(R.id.list);
			myList.setAdapter(adapter);
			myList.setOnItemClickListener(ApplicationsListActivity.this);
			adapter.setListener(ApplicationsListActivity.this);

		}

		@Override
		public void onError(JSONObject response) {
			Toast.makeText(context, "onError", Toast.LENGTH_SHORT).show();

		}
	}

	@Override
	public void onBackPressed() {
		if (staticContentShowing) {
			mainParent.setVisibility(View.VISIBLE);
			staticContentParent.setVisibility(View.GONE);
//			titleTxt.setText(getString(R.string.applications_screen_title));
			staticContentShowing = false;
		} else {
			super.onBackPressed();
		}
	}
}