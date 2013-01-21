package com.emiratesexpress.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.emiratesexpress.R;
import com.emiratesexpress.common.Utilities;

public class ServiceActivity extends Activity implements OnClickListener {

	private Context context;
	private RelativeLayout mainParent;
	private RelativeLayout staticParent;
	private TextView screenTitle;
	private TextView mainTitle;
	private ScrollView scrollView;
	private Button contactBtn;

	private int SCROLL_INTERVAL = 1000;

	private boolean isStaticContentShowing = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);
		setContentView(R.layout.services);
		context = this;

		mainParent = (RelativeLayout) findViewById(R.id.mainParent);
		staticParent = (RelativeLayout) findViewById(R.id.staticParent);
		scrollView = (ScrollView) findViewById(R.id.scrollView);

		mainTitle = (TextView) findViewById(R.id.titleTxt);
		mainTitle.setText(context.getString(R.string.services_screen_title));
		
		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));

		imageView = (ImageView) findViewById(R.id.upArrowBtn);
		imageView.setOnClickListener(this);

		imageView = (ImageView) findViewById(R.id.downArrowBtn);
		imageView.setOnClickListener(this);

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		contactBtn = (Button) findViewById(R.id.contactBtn);
		contactBtn.setOnClickListener(this);

		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.block1);
		relativeLayout.setOnClickListener(this);
		relativeLayout = (RelativeLayout) findViewById(R.id.block2);
		relativeLayout.setOnClickListener(this);
		relativeLayout = (RelativeLayout) findViewById(R.id.block3);
		relativeLayout.setOnClickListener(this);
		relativeLayout = (RelativeLayout) findViewById(R.id.block4);
		relativeLayout.setOnClickListener(this);
		relativeLayout = (RelativeLayout) findViewById(R.id.block5);
		relativeLayout.setOnClickListener(this);
		relativeLayout = (RelativeLayout) findViewById(R.id.block6);
		relativeLayout.setOnClickListener(this);
		relativeLayout = (RelativeLayout) findViewById(R.id.block7);
		relativeLayout.setOnClickListener(this);

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			if (isStaticContentShowing) {
				// screenTitle.setText(getString(R.string.services_screen_title));
				mainParent.setVisibility(View.VISIBLE);
				staticParent.setVisibility(View.GONE);
				contactBtn.setVisibility(View.INVISIBLE);
				mainTitle.setText(getString(R.string.services_screen_title));
				isStaticContentShowing = false;
			} else {
				finish();
			}
		}
		if (id == R.id.contactBtn) {
			Intent intent = new Intent(context, ContactUsActivity.class);
			startActivity(intent);
		}
		if (id == R.id.block1) {
			isStaticContentShowing = true;
			mainParent.setVisibility(View.GONE);
			staticParent.setVisibility(View.VISIBLE);
			contactBtn.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView) findViewById(R.id.serviceImg);
			imageView.setBackgroundResource(R.drawable.service_document_clearing_en);
			screenTitle = (TextView) findViewById(R.id.serviceTitle);
			screenTitle.setText(getString(R.string.services_documents_clearing_title_en));
			mainTitle.setText(getString(R.string.services_documents_clearing_title_en));
			TextView textView = (TextView) findViewById(R.id.description);
			textView.setText(getString(R.string.services_documents_clearing_desc_en));

		}
		if (id == R.id.block2) {

			isStaticContentShowing = true;
			mainParent.setVisibility(View.GONE);
			staticParent.setVisibility(View.VISIBLE);
			contactBtn.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView) findViewById(R.id.serviceImg);
			imageView.setBackgroundResource(R.drawable.service_businessmen_en);
			screenTitle = (TextView) findViewById(R.id.serviceTitle);
			screenTitle.setText(getString(R.string.services_businessmen_title_en));
			mainTitle.setText(getString(R.string.services_businessmen_title_en));
			TextView textView = (TextView) findViewById(R.id.description);
			textView.setText(getString(R.string.services_businessmen_desc_en));
		}
		if (id == R.id.block3) {
			isStaticContentShowing = true;
			mainParent.setVisibility(View.GONE);
			staticParent.setVisibility(View.VISIBLE);
			contactBtn.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView) findViewById(R.id.serviceImg);
			imageView.setBackgroundResource(R.drawable.service_typing_en);
			screenTitle = (TextView) findViewById(R.id.serviceTitle);
			screenTitle.setText(getString(R.string.services_typing_title_en));
			mainTitle.setText(getString(R.string.services_typing_title_en));
			TextView textView = (TextView) findViewById(R.id.description);
			textView.setText(getString(R.string.services_typing_desc_en));
		}
		if (id == R.id.block4) {
			isStaticContentShowing = true;
			mainParent.setVisibility(View.GONE);
			staticParent.setVisibility(View.VISIBLE);
			contactBtn.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView) findViewById(R.id.serviceImg);
			imageView.setBackgroundResource(R.drawable.service_photocopying_en);
			screenTitle = (TextView) findViewById(R.id.serviceTitle);
			screenTitle.setText(getString(R.string.services_photocopying_title_en));
			mainTitle.setText(getString(R.string.services_photocopying_title_en));
			TextView textView = (TextView) findViewById(R.id.description);
			textView.setText(getString(R.string.services_photocopying_desc_en));
		}
		if (id == R.id.block5) {
			isStaticContentShowing = true;
			mainParent.setVisibility(View.GONE);
			staticParent.setVisibility(View.VISIBLE);
			contactBtn.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView) findViewById(R.id.serviceImg);
			imageView.setBackgroundResource(R.drawable.service_translation_en);
			screenTitle = (TextView) findViewById(R.id.serviceTitle);
			screenTitle.setText(getString(R.string.services_translation_title_en));
			mainTitle.setText(getString(R.string.services_translation_title_en));
			TextView textView = (TextView) findViewById(R.id.description);
			textView.setText(getString(R.string.services_translation_desc_en));
		}
		if (id == R.id.block6) {
			isStaticContentShowing = true;
			mainParent.setVisibility(View.GONE);
			staticParent.setVisibility(View.VISIBLE);
			contactBtn.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView) findViewById(R.id.serviceImg);
			imageView.setBackgroundResource(R.drawable.service_vehicle_registration_en);
			screenTitle = (TextView) findViewById(R.id.serviceTitle);
			screenTitle.setText(getString(R.string.services_vehicles_regisration_title_en));
			mainTitle.setText(getString(R.string.services_vehicles_regisration_title_en));
			TextView textView = (TextView) findViewById(R.id.description);
			textView.setText(getString(R.string.services_vehicles_regisration_desc_en));
		}
		if (id == R.id.block7) {
			isStaticContentShowing = true;
			mainParent.setVisibility(View.GONE);
			staticParent.setVisibility(View.VISIBLE);
			contactBtn.setVisibility(View.VISIBLE);
			ImageView imageView = (ImageView) findViewById(R.id.serviceImg);
			imageView.setBackgroundResource(R.drawable.service_car_embarkation_en);
			screenTitle = (TextView) findViewById(R.id.serviceTitle);
			screenTitle.setText(getString(R.string.services_car_embarkation_title_en));
			mainTitle.setText(getString(R.string.services_car_embarkation_title_en));
			TextView textView = (TextView) findViewById(R.id.description);
			textView.setText(getString(R.string.services_car_embarkation_desc_en));
		}
		if (id == R.id.upArrowBtn) {
			scrollView.smoothScrollTo(0, scrollView.getScrollY() - SCROLL_INTERVAL);
		}
		if (id == R.id.downArrowBtn) {
			scrollView.smoothScrollTo(0, scrollView.getScrollY() + SCROLL_INTERVAL);
		}
	}

	@Override
	public void onBackPressed() {
		if (isStaticContentShowing) {
			screenTitle.setText(getString(R.string.services_screen_title));
			mainParent.setVisibility(View.VISIBLE);
			staticParent.setVisibility(View.GONE);
			contactBtn.setVisibility(View.INVISIBLE);
			mainTitle.setText(getString(R.string.services_screen_title));
			isStaticContentShowing = false;
		} else {
			super.onBackPressed();
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
		Utilities.unbindDrawables(findViewById(R.id.services));
		super.onDestroy();
	}
}