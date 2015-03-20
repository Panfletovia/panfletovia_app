package com.s2parking.api;

import org.json.JSONObject;

public interface ApiListener {
	
	public void onStarted();
	public void onSucceed(JSONObject response);
	public void onFailed(JSONObject response);
	public void onFinished();
}
