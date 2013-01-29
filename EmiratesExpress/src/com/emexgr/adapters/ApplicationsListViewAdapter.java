package com.emexgr.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.emexgr.R;
import com.emexgr.common.Configurations;
import com.emexgr.pojos.Applications;

public class ApplicationsListViewAdapter extends BaseAdapter {
	private Context context;
	private LayoutInflater mInflater;
	private ArrayList<Applications> applicationsList;

	private OnClickListener listener;

	public ApplicationsListViewAdapter(Context context, ArrayList<Applications> list) {
		this.context = context;
		this.applicationsList = list;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		if (applicationsList != null) {
			return applicationsList.size();
		}

		return 0;
	}

	@Override
	public Object getItem(int position) {
		if (applicationsList != null && position >= 0 && position < getCount()) {
			return applicationsList.get(position);
		}

		return null;
	}

	@Override
	public long getItemId(int position) {
		if (applicationsList != null && position >= 0 && position < getCount()) {
			return 0;
		}

		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		View view = convertView;
		ViewHolder viewHolder;

		if (view == null) {
			viewHolder = new ViewHolder();
			view = mInflater.inflate(R.layout.applications_list_item, parent, false);
			viewHolder.transactionText = (TextView) view.findViewById(R.id.titleTxt);
			viewHolder.authorityText = (TextView) view.findViewById(R.id.descriptionTxt);
			if(Configurations.currentLanguage == 1){
				viewHolder.transactionText.setText(applicationsList.get(position).getTransaction());
				viewHolder.authorityText.setText(applicationsList.get(position).getAuthority());
			}else{
				viewHolder.transactionText.setText(applicationsList.get(position).getTransactionAr());
				viewHolder.authorityText.setText(applicationsList.get(position).getAuthorityAr());
			}
			
			view.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) view.getTag();
			if(Configurations.currentLanguage == 1){
				viewHolder.transactionText.setText(applicationsList.get(position).getTransaction());
				viewHolder.authorityText.setText(applicationsList.get(position).getAuthority());
			}else{
				viewHolder.transactionText.setText(applicationsList.get(position).getTransactionAr());
				viewHolder.authorityText.setText(applicationsList.get(position).getAuthorityAr());
			}
		}

		view.setTag(viewHolder);
		return view;
	}

	public void setListener(OnClickListener listener) {
		this.listener = listener;
	}

	class ViewHolder{
		TextView transactionText;
		TextView authorityText;
	
	}
	
}
