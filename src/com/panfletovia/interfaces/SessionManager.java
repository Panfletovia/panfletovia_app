package com.panfletovia.interfaces;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public interface SessionManager {
	
	public boolean isLoggedIn();

	public void login();

	public void logout();

	public void save(Map<String, String> params);

	public void save(String key, String value);

	public void save(String key, JSONObject value);

	public void save(String key, JSONArray value);

	public String getString(String key);

	public int getInt(String key);

	public long getLong(String key);

	public JSONArray getJSONArray(String key) throws JSONException;

	public JSONObject getJSONObject(String key) throws JSONException;
}
