package com.emiratesexpress.network;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import com.emiratesexpress.common.Utilities;

public class JSONfunctions {
	
	/**
	 * This method sends a request to passed URL using POST method.
	 * 
	 * @param requestURL
	 *            URL on which request is to be sent
	 * @param postData
	 *            Json data to be posted
	 * @return JSONObject
	 */
	public static JSONObject httpPostRequest(String requestURL, String postData) {
		// StringBuilder response = null;
		requestURL = requestURL + "?";
		HttpURLConnection connection = null;
		URL url = null;
		try {
			url = new URL(requestURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setConnectTimeout(15000);
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			connection.getOutputStream().write(postData.getBytes());

		} catch (MalformedURLException malFormedExp) {
			malFormedExp.printStackTrace();

		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		JSONObject jsonObject = null;
		String result = "";
		int responseCode;
		try {

			responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				result = Utilities.readServerResponse(connection.getInputStream());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			jsonObject = new JSONObject(result);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jsonObject;

	}
	
}
