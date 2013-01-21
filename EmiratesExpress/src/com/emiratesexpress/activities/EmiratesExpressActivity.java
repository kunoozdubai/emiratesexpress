package com.emiratesexpress.activities;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.emiratesexpress.R;
import com.emiratesexpress.asynctask.RestartingApplicationTask;
import com.emiratesexpress.asynctask.StartLocationActivityTask;
import com.emiratesexpress.common.CommonConstants;
import com.emiratesexpress.common.Configurations;
import com.emiratesexpress.common.Utilities;
import com.emiratesexpress.dialogs.AboutUsDialog;
import com.emiratesexpress.dialogs.BusinessGuideDialog;
import com.emiratesexpress.dialogs.EconomicNewsDialog;

public class EmiratesExpressActivity extends Activity implements OnClickListener {

	private Context context;
	private RelativeLayout mainActivityParentView;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emirates_express_activity);
        mainActivityParentView = (RelativeLayout) findViewById(R.id.emirates_express_activity);
       
	        context = this;
	        CommonConstants.EMIRATES_EXPRESS_CONTEXT = context;
	//        Configurations.user = new User();
	        if(Utilities.imageMap.get("background") == null){
		        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background, Utilities.getBitmapFactoryoptions(1));
				BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
				Utilities.imageMap.put("background", bitmapDrawable);
				
	        }
	        ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
	        imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));
	        
	        Button button = (Button) findViewById(R.id.loginRegisterBtn);
	        button.setOnClickListener(this);
	        button = (Button) findViewById(R.id.aboutBtn);
	        button.setOnClickListener(this);
	        button = (Button) findViewById(R.id.servicesBtn);
	        button.setOnClickListener(this);
	        button = (Button) findViewById(R.id.contactUsBtn);
	        button.setOnClickListener(this);
	        button = (Button) findViewById(R.id.settingsBtn);
	        button.setOnClickListener(this);
	        button = (Button) findViewById(R.id.expressGroupBtn);
	        button.setOnClickListener(this);
	        button = (Button) findViewById(R.id.businessSetupGuideBtn);
	        button.setOnClickListener(this);
	        button = (Button) findViewById(R.id.careerBtn);
	        button.setOnClickListener(this);
	        button = (Button) findViewById(R.id.economicNewsBtn);
	        button.setOnClickListener(this);
	        
	        button = (Button) findViewById(R.id.clientsBtn);
	        button.setOnClickListener(this);
	        
	        button = (Button) findViewById(R.id.mapBtn);
	        button.setOnClickListener(this);
	        
	        button = (Button) findViewById(R.id.consultationBtn);
	        button.setOnClickListener(this);
        
        
    }

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.aboutBtn){
			new AboutUsDialog(context).show();
		}else if(id == R.id.servicesBtn){
			Intent intent = new Intent(context, ServiceActivity.class);
			startActivity(intent);
		}else if(id == R.id.loginRegisterBtn){
			Intent intent = new Intent(context, AccountActivity.class);
			startActivity(intent);
		}else if(id == R.id.contactUsBtn){
			Intent intent = new Intent(context, ContactUsActivity.class);
			intent.putExtra("title", getString(R.string.contact_us_screen_title));
			startActivity(intent);
		}else if(id == R.id.careerBtn){
			Intent intent = new Intent(context, CareersActivity.class);
			startActivity(intent);
		}else if(id == R.id.businessSetupGuideBtn){
			new BusinessGuideDialog(context).show();
		}else if(id == R.id.expressGroupBtn){
			Intent intent = new Intent(context, GroupCompaniesActivity.class);
			startActivity(intent);
		}else if(id == R.id.economicNewsBtn){
			new EconomicNewsDialog(context).show();
		}else if(id == R.id.clientsBtn){
			Intent intent = new Intent(context, ClientsActivity.class);
			startActivity(intent);
		}else if(id == R.id.mapBtn){
			new StartLocationActivityTask(context).execute();
		}else if(id == R.id.consultationBtn){
			Intent intent = new Intent(context, ContactUsActivity.class);
			intent.putExtra("title", getString(R.string.consultation_form));
			startActivity(intent);
		}else if(id == R.id.settingsBtn){
			createAlertDialogForLanguage();
		}
		
	}
	
	
	private void createAlertDialogForLanguage() {
		Builder alertBuilder = new Builder(context);

		alertBuilder.setTitle("");
		alertBuilder.setMessage("Choose Language");
		
		alertBuilder.setPositiveButton("English", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(Utilities.getLocale().equals("ar")){
					new RestartingApplicationTask(context, "en", mainActivityParentView).execute();
					Configurations.currentLanguage = 1;
					//Utilities.updateLocale("en");
				}
			}
		});
		alertBuilder.setNegativeButton(getString(R.string.arabic), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if(Utilities.getLocale().equals("en")){
					new RestartingApplicationTask(context, "ar", mainActivityParentView).execute();
					Configurations.currentLanguage = 2;
					//Utilities.updateLocale("ar");
				}
				
			}
		});
		alertBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		alertBuilder.create().show();
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
		Utilities.updateLocale("en");
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		context = null;
		Utilities.unbindDrawables(findViewById(R.id.emirates_express_activity));
		System.gc();
		//Killing application process on exit.
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	protected static void releaseActivity() {
		
	}
}