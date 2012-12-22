package com.emiratesexpress.dialogs;



import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.emiratesexpress.R;
import com.emiratesexpress.activities.CareersActivity;

public class PictureOptionsDialog {
	
	private Context context;
	private Options[] options;

	public PictureOptionsDialog(Context ctx) {
		context = ctx;
	}

	public void show( ) {
		loadOptions();
		// define the list adapter with the choices
		ListAdapter adapter = new OptionsAdapter( context, options );

		final AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
		// define the alert dialog with the choices and the action to take
		// when one of the choices is selected
		alertDialog.setCancelable(false);
		alertDialog.setTitle("Choose Picture");
		alertDialog.setSingleChoiceItems( adapter, -1, new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// a choice has been made!
				((CareersActivity)context).handleDialogSelection(options[which].getId());
				Log.d("", "chosen " + options[which].getName() );
				dialog.dismiss();
				
			}
		});
		alertDialog.setNeutralButton("Cancel", new OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.show();
		
	}
	/**
	 * Create all the choices for the list
	 */
	private void loadOptions() {
		options = new Options[2];
		// define the display string, the image, and the value to use
		// when the choice is selected
		
		options[0]  = new Options("Take Picture" , 0);
		options[1]  = new Options("Choose From Gallery", 1);
		
	}
	/**
	 * Definition of the list adapter...uses the View Holder pattern to
	 * optimize performance.
	 */
	static class OptionsAdapter extends ArrayAdapter<Options> {

		private static final int RESOURCE = R.layout.source_options;
		private LayoutInflater inflater;

		static class ViewHolder {
			TextView nameTxVw;
		}

		public OptionsAdapter(Context context, Options[] objects)
		{
			super(context, RESOURCE, objects);
			inflater = LayoutInflater.from(context);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			ViewHolder holder;

			if ( convertView == null ) {
				// inflate a new view and setup the view holder for future use
				convertView = inflater.inflate( RESOURCE, null );

				holder = new ViewHolder();
				holder.nameTxVw =
					(TextView) convertView.findViewById(R.id.name);
				convertView.setTag( holder );
			}  else {
				// view already defined, retrieve view holder
				holder = (ViewHolder) convertView.getTag();
			}

			Options options = (Options) getItem( position );
			if ( options == null ) {
				Log.e( "dialog", "Invalid category for position: " + position );
			}
            if (options != null) {
                holder.nameTxVw.setText( options.getName() );
            }
            //			holder.nameTxVw.setCompoundDrawables(null, null, options.getImg(), null);
			return convertView;
		}
	}

	/**
	 * POJO for holding each list choice
	 *
	 */
	class Options {
		private String name;
		private int id;

		public Options( String name, int id) {
			this.name = name;
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}
	}
}
