package com.emiratesexpress.network;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;

import com.emiratesexpress.R;
import com.emiratesexpress.common.CommonConstants;
import com.emiratesexpress.common.NetworkConstants;
import com.emiratesexpress.common.Utilities;
import com.emiratesexpress.pojos.Applications;
import com.emiratesexpress.pojos.User;

public class Parser {

	public static String parseRegisterationResponse(JSONObject response) {
		String userId = "";
		try {
			if (!response.isNull(NetworkConstants.USERID)) {
				userId = response.getString(NetworkConstants.USERID);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return userId;
	}

	public static User parseLoginResponse(JSONObject response) {

		User user = null;

		String userId = "";
		String name = "";
		String nameArabic = "";
		String username = "";
		String password = "";
		String email = "";
		String status = "";
		String userType = "";
		String created = "";
		String mobile = "";
		String company = "";

		try {
			if (!response.isNull(NetworkConstants.USERID)) {
				userId = response.getString(NetworkConstants.USERID);
			}
			if (!response.isNull(NetworkConstants.NAME)) {
				name = response.getString(NetworkConstants.NAME);
			}
			if (!response.isNull(NetworkConstants.NAME_AR)) {
				nameArabic = response.getString(NetworkConstants.NAME_AR);
			}
			if (!response.isNull(NetworkConstants.USERNAME)) {
				username = response.getString(NetworkConstants.USERNAME);
			}
			if (!response.isNull(NetworkConstants.PASSWORD)) {
				password = response.getString(NetworkConstants.PASSWORD);
			}
			if (!response.isNull(NetworkConstants.EMAIL)) {
				email = response.getString(NetworkConstants.EMAIL);
			}
			if (!response.isNull(NetworkConstants.STATUS)) {
				status = response.getString(NetworkConstants.STATUS);
			}
			if (!response.isNull(NetworkConstants.USER_TYPE)) {
				userType = response.getString(NetworkConstants.USER_TYPE);
			}
			if (!response.isNull(NetworkConstants.CREATED)) {
				created = response.getString(NetworkConstants.CREATED);
			}
			if (!response.isNull(NetworkConstants.MOBILE)) {
				mobile = response.getString(NetworkConstants.MOBILE);
			}
			if (!response.isNull(NetworkConstants.COMPANY)) {
				company = response.getString(NetworkConstants.COMPANY);
			}
			user = new User(userId, name, nameArabic, username, password, email, status, userType, created, mobile, company);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

	public static ArrayList<Applications> parseApplicationResponse(JSONObject response, Context context) {

		int size = 0;
		ArrayList<Applications> applicationList = null;

		String transId = "";
		String transTypeId = "";
		String userId = "";
		String description = "";
		String name = "";
		String serviceFee = "";
		String govtFee = "";
		String debit = "";
		String oldBalance = "";
		String posted = "";
		String comments = "";
		String status = "";
		String authority = "";
		String authorityAr = "";
		String transaction = "";
		String transactionAr = "";
		String username = "";
		String nameAr = "";
		String datePosted = "";

		JSONArray applicationJSONArray;
		try {

			if (!response.isNull("results")) {
				size = Integer.parseInt(response.getString("results"));
			}
			applicationList = new ArrayList<Applications>(size);
			if (size == 0) {
				return applicationList;
			}

			if (!response.isNull("data")) {
				applicationJSONArray = response.getJSONArray("data");
			} else {
				return new ArrayList<Applications>();
			}
			for (int i = 0; i < applicationJSONArray.length(); i++) {

				response = applicationJSONArray.getJSONObject(i);

				if (!response.isNull(NetworkConstants.TRANS_ID)) {
					transId = response.getString(NetworkConstants.TRANS_ID);
				}
				if (!response.isNull(NetworkConstants.TRANS_TYPE_ID)) {
					transTypeId = response.getString(NetworkConstants.TRANS_TYPE_ID);
				}
				if (!response.isNull(NetworkConstants.USERID)) {
					userId = response.getString(NetworkConstants.USERID);
				}
				if (!response.isNull(NetworkConstants.DESCRIPTION)) {
					description = response.getString(NetworkConstants.DESCRIPTION);
				}
				if (!response.isNull(NetworkConstants.NAME)) {
					name = response.getString(NetworkConstants.NAME);
				}
				if (!response.isNull(NetworkConstants.SERVICE_FEE)) {
					serviceFee = response.getString(NetworkConstants.SERVICE_FEE);
				}
				if (!response.isNull(NetworkConstants.GOVT_FEE)) {
					govtFee = response.getString(NetworkConstants.GOVT_FEE);
				}
				if (!response.isNull(NetworkConstants.DEBIT)) {
					debit = response.getString(NetworkConstants.DEBIT);
				}
				if (!response.isNull(NetworkConstants.OLD_BALANCE)) {
					oldBalance = response.getString(NetworkConstants.OLD_BALANCE);
				}
				if (!response.isNull(NetworkConstants.POSTED)) {
					posted = response.getString(NetworkConstants.POSTED);
				}
				if (!response.isNull(NetworkConstants.COMMENTS)) {
					comments = response.getString(NetworkConstants.COMMENTS);
				}
				if (!response.isNull(NetworkConstants.STATUS)) {
					int value = Integer.parseInt(response.getString(NetworkConstants.STATUS));
					if (value == CommonConstants.APPROVED) {
						status = context.getString(R.string.approved);
					} else if (value == CommonConstants.IN_PROCESS) {
						status = context.getString(R.string.in_process);
					} else if (value == CommonConstants.REJECTED) {
						status = context.getString(R.string.rejected);
					}
				}
				if (!response.isNull(NetworkConstants.AUTHORITY)) {
					authority = response.getString(NetworkConstants.AUTHORITY);
				}
				if (!response.isNull(NetworkConstants.AUTHORITY_AR)) {
					authorityAr = response.getString(NetworkConstants.AUTHORITY_AR);
				}
				if (!response.isNull(NetworkConstants.TRANSACTION)) {
					transaction = response.getString(NetworkConstants.TRANSACTION);
				}
				if (!response.isNull(NetworkConstants.TRANSACTION_AR)) {
					transactionAr = response.getString(NetworkConstants.TRANSACTION_AR);
				}
				if (!response.isNull(NetworkConstants.USERNAME)) {
					username = response.getString(NetworkConstants.USERNAME);
				}
				if (!response.isNull(NetworkConstants.NAME_AR)) {
					nameAr = response.getString(NetworkConstants.NAME_AR);
				}
				if (!response.isNull(NetworkConstants.DATE_POSTED)) {
					datePosted = response.getString(NetworkConstants.DATE_POSTED);
				}
				Applications applications = new Applications(transId, transTypeId, userId, description, name, serviceFee, govtFee, debit, oldBalance, posted,
						comments, status, authority, authorityAr, transaction, transactionAr, username, nameAr, datePosted);
				applicationList.add(applications);
				transId = "";
				transTypeId = "";
				userId = "";
				description = "";
				name = "";
				serviceFee = "";
				govtFee = "";
				debit = "";
				oldBalance = "";
				posted = "";
				comments = "";
				status = "";
				authority = "";
				authorityAr = "";
				transaction = "";
				transactionAr = "";
				username = "";
				nameAr = "";
				datePosted = "";

			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return applicationList;
	}

	public static String parseAppClientsResponse(JSONObject response, Context context) {
		JSONArray appClientsJSONArray = null;
		StringBuilder nameBuilder = new StringBuilder();
		int size = 0;
		boolean isLocaleEnglish = true;
		if(Utilities.getLocale().equals("ar")){
			isLocaleEnglish = false;
		}
		try {
			if (!response.isNull("results")) {
				size = Integer.parseInt(response.getString("results"));
			}
			if (!response.isNull("data")) {
				appClientsJSONArray = response.getJSONArray("data");
			}
			
			for (int i = 0; i < size; i++) {

				response = appClientsJSONArray.getJSONObject(i);
				String name = "";
				String nameAr = "";
				if (!response.isNull(NetworkConstants.NAME)) {
					name = response.getString(NetworkConstants.NAME);
				}
				if (!response.isNull(NetworkConstants.NAME_AR)) {
					nameAr = response.getString(NetworkConstants.NAME_AR);
				}
				if(isLocaleEnglish){
					if(!Utilities.isStringEmptyOrNull(name)){
						nameBuilder.append(name);
						nameBuilder.append("\n");
					}
//					else{
//						nameBuilder.append(nameAr);
//						nameBuilder.append("\n");
//					}
				}else if(!isLocaleEnglish){
					if(!Utilities.isStringEmptyOrNull(nameAr)){
						nameBuilder.append(nameAr);
						nameBuilder.append("\n");
					}
//					else{
//						nameBuilder.append(name);
//						nameBuilder.append("\n");
//					}
				}
				
			}
			
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return nameBuilder.toString();
	}

}