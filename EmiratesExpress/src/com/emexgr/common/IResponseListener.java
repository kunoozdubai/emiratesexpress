package com.emexgr.common;

import org.json.JSONObject;

public interface IResponseListener {

	public void onSuccess(JSONObject response);
	public void onError( JSONObject response );
}
