package com.emexgr.activities;

import java.io.File;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.emexgr.R;
import com.emexgr.common.CommonConstants;
import com.emexgr.common.NetworkConstants;
import com.emexgr.common.Utilities;
import com.emexgr.dialogs.PictureOptionsDialog;

public class CareersActivity extends Activity implements View.OnClickListener {

	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);

		setContentView(R.layout.careers);
		context = this;

		TextView title = (TextView) findViewById(R.id.titleTxt);
		title.setText(context.getString(R.string.careers_screen_title));
		
		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));

		imageView = (ImageView) findViewById(R.id.photo);
		imageView.setOnClickListener(this);

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		button = (Button) findViewById(R.id.sendBtn);
		button.setOnClickListener(this);
		
		CommonConstants.CAREER_IMAGE_PATH = "";

	}

	@Override
	protected void onPause() {

		overridePendingTransition(R.anim.hold, R.anim.push_out_from_top);
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.backBtn) {
			finish();
		} else if (id == R.id.photo) {
			new PictureOptionsDialog(context).show();

		} else if (id == R.id.sendBtn) {

			EditText editText = (EditText) findViewById(R.id.name);
			String name = editText.getText().toString();
			editText = (EditText) findViewById(R.id.country);
			String country = editText.getText().toString();
			editText = (EditText) findViewById(R.id.email);
			String emailAddress = editText.getText().toString();
			editText = (EditText) findViewById(R.id.mobile);
			String mobile = editText.getText().toString();
			RadioGroup radioGroup = (RadioGroup) findViewById(R.id.gender);
			RadioButton gender = (RadioButton) findViewById(R.id.male);
			String genderValue = "male";
			if (!gender.isChecked()) {
				genderValue = "female";
			}

			String[] array = new String[5];
			array[0] = country;
			array[1] = emailAddress;
			array[2] = name;
			array[3] = mobile;
			array[4] = genderValue;

			if (Utilities.isArrayValuesEmptyOrNull(array)) {
				Toast.makeText(context, getString(R.string.all_fields_required), Toast.LENGTH_SHORT).show();
				return;
			}
			if (!Utilities.isValidEmail(emailAddress)) {
				Toast.makeText(context, getString(R.string.invalid_email), Toast.LENGTH_SHORT).show();
				return;
			}
			StringBuilder emailBody = new StringBuilder();
			String subject = getString(R.string.new_application_details);
			if(Utilities.getLocale().equals("ar")){
				
				emailBody.append(name);
				emailBody.append(" : ");
				emailBody.append(getString(R.string.name));
				emailBody.append("\n");

				emailBody.append(genderValue);
				emailBody.append(" : ");
				emailBody.append(getString(R.string.gender));
				emailBody.append("\n");
				
				emailBody.append(country);
				emailBody.append(" : ");
				emailBody.append(getString(R.string.nationality));
				emailBody.append("\n");
				
				emailBody.append(mobile);
				emailBody.append(" : ");
				emailBody.append(getString(R.string.mobile));
				emailBody.append("\n");
				
				emailBody.append(emailAddress);
				emailBody.append(" : ");
				emailBody.append(getString(R.string.email_address));
				emailBody.append("\n");
				
			}else{
				
				emailBody.append(getString(R.string.name));
				emailBody.append(": ");
				emailBody.append(name);
				emailBody.append("\n");
				
				emailBody.append(getString(R.string.gender));
				emailBody.append(": ");
				emailBody.append(genderValue);
				emailBody.append("\n");
				
				emailBody.append(getString(R.string.nationality));
				emailBody.append(": ");
				emailBody.append(country);
				emailBody.append("\n");
				
				emailBody.append(getString(R.string.mobile));
				emailBody.append(": ");
				emailBody.append(mobile);
				emailBody.append("\n");
				
				emailBody.append(getString(R.string.email_address));
				emailBody.append(": ");
				emailBody.append(emailAddress);
				emailBody.append("\n");

			}
			Utilities.email(context, NetworkConstants.CAREER_EMAIL, subject, emailBody.toString());
		}
	}


	public void handleDialogSelection(int id) {
		Uri fileUri;
		switch (id) {

		case 0:
			// take Picture
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileUri = getOutputMediaFileUri();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
			startActivityForResult(intent, 100);
			break;
		case 1:
			// chose from library
			if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
				Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
				photoPickerIntent.setType("image/jpeg");
				photoPickerIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///sdcard/Pictures"));
				startActivityForResult(photoPickerIntent, 200);
			}
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == 100) {
			if (resultCode == RESULT_OK) {

			}
		} else if (requestCode == 200) {
			if (resultCode == RESULT_OK) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaStore.Images.Media.DATA };
				Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
				String imagePath = cursor.getString(columnIndex);
				cursor.close();
				CommonConstants.CAREER_IMAGE_PATH = imagePath;
				
			}
		} else if(requestCode == 300){
			Toast.makeText(context, getString(R.string.thankyou), Toast.LENGTH_SHORT).show();
			CommonConstants.CAREER_IMAGE_PATH = "";
			finish();
		}
	}

	/** Create a file Uri for saving an image */
	private Uri getOutputMediaFileUri() {
		return Uri.fromFile(getOutputMediaFile());
	}

	/** Create a File for saving an image */
	private File getOutputMediaFile() {
		File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
				CommonConstants.EMIRATES_EXPRESS_APP_NAME);
		if (!mediaStorageDir.exists()) {
			if (!mediaStorageDir.mkdirs()) {
				return null;
			}
		}
		String imagePath = mediaStorageDir.getPath() + File.separator + "career_emex.jpg";
		CommonConstants.CAREER_IMAGE_PATH = imagePath;

		return new File(imagePath);
	}

	@Override
	protected void onDestroy() {
		context = null;
		Utilities.unbindDrawables(findViewById(R.id.careers));
		super.onDestroy();
	}
}