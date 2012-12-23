package com.emiratesexpress.activities;

import java.io.ByteArrayOutputStream;
import java.io.File;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
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
import android.widget.Toast;

import com.emiratesexpress.R;
import com.emiratesexpress.asynctask.DataDownloadTask;
import com.emiratesexpress.common.CommonConstants;
import com.emiratesexpress.common.IResponseListener;
import com.emiratesexpress.common.NetworkConstants;
import com.emiratesexpress.common.Utilities;
import com.emiratesexpress.dialogs.PictureOptionsDialog;

public class CareersActivity extends Activity implements View.OnClickListener {

	private Context context;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.pull_in_from_bottom, R.anim.hold);

		setContentView(R.layout.careers);
		context = this;

		ImageView imageView = (ImageView) findViewById(R.id.backgourndImg);
		imageView.setBackgroundDrawable(Utilities.imageMap.get("background"));

		imageView = (ImageView) findViewById(R.id.photo);
		imageView.setOnClickListener(this);

		Button button = (Button) findViewById(R.id.backBtn);
		button.setOnClickListener(this);
		button = (Button) findViewById(R.id.sendBtn);
		button.setOnClickListener(this);

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

			String postData = makePostData(name, country, emailAddress, mobile, genderValue);

			new DataDownloadTask(context, new ContactUsResponse(), NetworkConstants.EMIRATES_EXPRESS_CAREERS_URL, postData).execute();

			// Toast.makeText(context, "Send Button clicked",
			// Toast.LENGTH_SHORT).show();
		}
	}

	private String makePostData(String name, String country, String emailAddress, String mobile, String gender) {
		StringBuilder postData = new StringBuilder();

		// postData.append(NetworkConstants.VIEW);
		// postData.append("=");
		// postData.append(NetworkConstants.VIEW_APP_REGISTER);
		// postData.append("&");
		// postData.append(NetworkConstants.JSON);
		// postData.append("=");
		// postData.append("1");
		// postData.append("&");
		postData.append(NetworkConstants.NAME.getBytes());
		postData.append("=".getBytes());
		postData.append(name.getBytes());
		postData.append("&".getBytes());
		postData.append(NetworkConstants.COUNTRY.getBytes());
		postData.append("=".getBytes());
		postData.append(country.getBytes());
		postData.append("&".getBytes());
		postData.append(NetworkConstants.MOBILE.getBytes());
		postData.append("=".getBytes());
		postData.append(mobile.getBytes());
		postData.append("&".getBytes());
		postData.append(NetworkConstants.EMAIL.getBytes());
		postData.append("=".getBytes());
		postData.append(emailAddress.getBytes());
		postData.append("&".getBytes());
		postData.append(NetworkConstants.GENDER.getBytes());
		postData.append("=".getBytes());
		postData.append(gender.getBytes());
		postData.append("&".getBytes());
		postData.append(NetworkConstants.FILE_ATT.getBytes());
		postData.append("=".getBytes());
		Bitmap bm = null;
		Bitmap bmpCompressed = null;

		bm = BitmapFactory.decodeFile(CommonConstants.CAREER_IMAGE_PATH, Utilities.getBitmapFactoryoptions(2));
		if (bm != null) {
			bmpCompressed = Bitmap.createScaledBitmap(bm, 400, 400, true);
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			bmpCompressed.compress(CompressFormat.JPEG, 75, bos);
			byte[] data = bos.toByteArray();
			postData.append(data);
			if (bm != null) {
				bm.recycle();
				bm = null;
			}
			if (bmpCompressed != null) {
				bmpCompressed.recycle();
				bmpCompressed = null;
			}

		}
		return postData.toString();

	}

	private class ContactUsResponse implements IResponseListener {

		@Override
		public void onSuccess(JSONObject response) {
			Toast.makeText(context, getString(R.string.thankyou), Toast.LENGTH_SHORT).show();
			File file = new File(CommonConstants.CAREER_IMAGE_PATH);
			file.delete();
			finish();
		}

		@Override
		public void onError(JSONObject response) {
			Toast.makeText(context, getString(R.string.thankyou), Toast.LENGTH_SHORT).show();
			File file = new File(CommonConstants.CAREER_IMAGE_PATH);
			file.delete();
			finish();
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
				Bitmap careerPic = null;
				careerPic = BitmapFactory.decodeFile(imagePath);
				File folderPath = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/"
						+ CommonConstants.EMIRATES_EXPRESS_APP_NAME);
				// updateProfilePic(profilePic);
				if (folderPath.exists()) {
					Utilities.writeOnSdCard(folderPath, careerPic);
				} else {
					folderPath.mkdirs();
					Utilities.writeOnSdCard(folderPath, careerPic);
				}
			}
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
		String imagePath = mediaStorageDir.getPath() + File.separator + "career.jpg";

		return new File(imagePath);
	}

	@Override
	protected void onDestroy() {
		context = null;
		Utilities.unbindDrawables(findViewById(R.id.careers));
		File file = new File(CommonConstants.CAREER_IMAGE_PATH);
		file.delete();
		super.onDestroy();
	}
}