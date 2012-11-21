package com.emiratesexpress.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.emiratesexpress.R;
import com.emiratesexpress.common.Utilities;

public class EmiratesExpressSplashActivity extends Activity {
	private SplashCounter counter;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splash_activity);
	}

	@Override
	protected void onResume() {
		super.onResume();
		counter = new SplashCounter(3000, 1000);
		counter.start();
	}

	private class SplashCounter extends CountDownTimer {
		private Intent intent;

		public SplashCounter(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);
		}

		@Override
		public void onFinish() {
			finish();
			intent = new Intent(EmiratesExpressSplashActivity.this, EmiratesExpressActivity.class);
			startActivity(intent);
		}

		@Override
		public void onTick(long millisUntilFinished) {
		}
	}

	@Override
	protected void onDestroy() {
		Utilities.unbindDrawables(findViewById(R.id.splash_activity));
		System.gc();
		super.onDestroy();
	}

}