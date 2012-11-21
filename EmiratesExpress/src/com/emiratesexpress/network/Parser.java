package com.emiratesexpress.network;

import org.json.JSONException;
import org.json.JSONObject;

import com.emiratesexpress.common.NetworkConstants;
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
			user = new User(userId, name, nameArabic, username, password, email, status, userType, created, mobile);

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return user;
	}

}