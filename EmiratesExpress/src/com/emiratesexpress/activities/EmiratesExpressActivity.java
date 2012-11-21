package com.emiratesexpress.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

import com.emiratesexpress.R;
import com.emiratesexpress.common.CommonConstants;
import com.emiratesexpress.common.Configurations;
import com.emiratesexpress.common.Utilities;
import com.emiratesexpress.dialogs.AboutUsDialog;
import com.emiratesexpress.pojos.User;

public class EmiratesExpressActivity extends Activity implements OnClickListener {

	private Context context;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.emirates_express_activity);
        context = this;
        CommonConstants.EMIRATES_EXPRESS_CONTEXT = context;
        
        Configurations.user = new User();
        
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.background, Utilities.getBitmapFactoryoptions(0));
		BitmapDrawable bitmapDrawable = new BitmapDrawable(bitmap);
		Utilities.imageMap.put("background", bitmapDrawable);
		
		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));
        
        Button button = (Button) findViewById(R.id.loginRegisterBtn);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.aboutBtn);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.servicesBtn);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.howToBtn);
        button.setOnClickListener(this);
        button = (Button) findViewById(R.id.contactUsBtn);
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
		}else if(id == R.id.howToBtn){
			
		}else if(id == R.id.loginRegisterBtn){
			Intent intent = new Intent(context, LoginActivity.class);
			startActivity(intent);
			
		}else if(id == R.id.contactUsBtn){
			Intent intent = new Intent(context, ContactUsActivity.class);
			startActivity(intent);
		}
	}
	
	@Override
	protected void onDestroy() {

		context = null;
		Utilities.unbindDrawables(findViewById(R.id.emirates_express_activity));
		super.onDestroy();
	}
}