package com.emexgr.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.emexgr.R;
import com.emexgr.common.Utilities;

public class BusinessGuideDialog extends Dialog implements View.OnClickListener, OnCancelListener {
	private View businessGuideView;
	private Context context = null;
	private Activity activity;

	public BusinessGuideDialog(Context context) {
		super(context, R.style.preview);
		this.context = context;
		activity = (Activity) this.context;
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		businessGuideView = layoutInflater.inflate(R.layout.business_guide_dialog, (ViewGroup) activity.findViewById(R.layout.emirates_express_activity));
		setContentView(businessGuideView);
		
	}

	@Override
	public void show() {
		super.show();
		initializeViews();
	}

	private void initializeViews() {
		/*ImageView imageView = (ImageView) businessGuideView.findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));*/

		TextView title = (TextView) businessGuideView.findViewById(R.id.titleTxt);
		title.setText(context.getString(R.string.business_setup_screen_title));
		
		Button button = (Button) businessGuideView.findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		
		this.setOnKeyListener(businessGuideKeyListener);
		
		String businessGuideUrl = "file:///android_asset/en/business_setup.html";
		
		if(Utilities.getLocale().equals("ar")){
			businessGuideUrl = "file:///android_asset/ar/business_setup-ar.html";
		}
		WebView webView	= (WebView) findViewById(R.id.webView);
		
		WebSettings settings= webView.getSettings();
		settings.setBuiltInZoomControls(true);
		settings.setSupportZoom(true);
		settings.setJavaScriptEnabled(true);
		settings.setLoadWithOverviewMode(true);
		webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
		webView.setScrollbarFadingEnabled(true);
		webView.loadUrl(businessGuideUrl);

	}

	@Override
	public void hide() {
		cancel();

	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			hide();
		}
	}

	private OnKeyListener businessGuideKeyListener = new OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_MENU) {
				return true;
			}
			if (keyCode == KeyEvent.KEYCODE_BACK && (event.getAction() == KeyEvent.ACTION_DOWN)) {
				hide();

				return true;
			}

			return false;
		}
	};

	@Override
	public void onCancel(DialogInterface dialog) {
		context = null;
		activity = null;
		Utilities.unbindDrawables(findViewById(R.id.business_guide_dialog));
	}

}