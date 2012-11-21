package com.emiratesexpress.dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.emiratesexpress.R;
import com.emiratesexpress.common.Utilities;

public class AboutUsDialog extends Dialog implements View.OnClickListener, OnCancelListener {
	private View aboutUsView;
	private Context context = null;
	private Activity activity;

	public AboutUsDialog(Context context) {
		super(context, R.style.preview);
		this.context = context;
		activity = (Activity) this.context;
		LayoutInflater layoutInflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		aboutUsView = layoutInflater.inflate(R.layout.about_us_dialog, (ViewGroup) activity.findViewById(R.layout.emirates_express_activity));
		setContentView(aboutUsView);
		
	}

	@Override
	public void show() {
		super.show();
		initializeViews();
	}

	private void initializeViews() {
		ImageView imageView = (ImageView) aboutUsView.findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));

		Button button = (Button) aboutUsView.findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		
		this.setOnKeyListener(aboutUsKeyListener);
		

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

	private OnKeyListener aboutUsKeyListener = new OnKeyListener() {
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
		Utilities.unbindDrawables(findViewById(R.id.about_us_dialog));
	}

}