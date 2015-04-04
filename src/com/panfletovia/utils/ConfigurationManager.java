package com.panfletovia.utils;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class ConfigurationManager {
	
	protected static ConfigurationManager instance = null;
	protected SharedPreferences preferences;

	
	protected ConfigurationManager() {
		
	}
	
	protected void setup(Context applicationContext) {
//		preferences = applicationContext.getSharedPreferences(Constants.PREFERENCES_NAME, Context.MODE_PRIVATE);
//		PreferenceManager.setDefaultValues(applicationContext, Constants.PREFERENCES_NAME, Context.MODE_PRIVATE, R.xml.preferences, false);
	}
	
	// Construtor singleton
	protected ConfigurationManager(Context applicationContext) {
		setup(applicationContext);
	}

	/**
	 * Método que inicializa a instância singleton
	 * 
	 * @param applicationContext
	 */
	public static void initialize(Context applicationContext) {
		instance = new ConfigurationManager(applicationContext);
	}

	/**
	 * Seta uma configuração
	 * 
	 * @param name
	 *            Nome da configurações
	 * @param value
	 *            Valor da configuração
	 * @return
	 */
	public ConfigurationManager set(String name, String value) {
		Editor editor = preferences.edit();
		editor.putString(name, value);
		editor.commit();
		return this;
	}
	
	public ConfigurationManager remove(String name) {
		Editor editor = preferences.edit();
		editor.remove(name);
		editor.commit();
		return this;
	}
	
	/**
	 * Seta uma configuração
	 * 
	 * @param name
	 *            Nome da configurações
	 * @param value
	 *            Valor da configuração
	 * @return
	 */
	
	public ConfigurationManager set(String name, boolean value) {
		Editor editor = preferences.edit();
		editor.putBoolean(name, value);
		editor.commit();
		return this;
	}
	
	/**
	 * Seta uma configuração int
	 * 
	 * @param name
	 *            Nome da configurações
	 * @param value
	 *            Valor da configuração
	 * @return
	 */
	/*public ConfigurationManager set(String name, int value) {
		Editor editor = preferences.edit();
		editor.putInt(name, value);
		editor.commit();
		return this;
	}*/

	/**
	 * Seta uma configuração JSONObject
	 * 
	 * @param name
	 *            Nome da configuração
	 * @param json
	 *            Valor da configuração
	 * @return
	 */
	public ConfigurationManager set(String name, JSONObject json) {
		return set(name, json.toString());
	}

	/**
	 * Seta uma configuração JSONArray
	 * 
	 * @param name
	 *            Nome da configuração
	 * @param json
	 *            Valor da configuração
	 * @return
	 */
	public ConfigurationManager set(String name, JSONArray json) {
		return set(name, json.toString());
	}

	/**
	 * Seta diversas configurações em uma única transação
	 * 
	 * @param configurations
	 *            Map com as configurações e seus valores
	 * @return
	 */
	public ConfigurationManager setAll(Map<String, Object> configurations) {
		Editor editor = preferences.edit();
		for (String configuration : configurations.keySet()) {
			editor.putString(configuration, String.valueOf(configurations.get(configuration)));
		}
		editor.commit();
		return this;
	}

	/**
	 * Retorna uma configuração String
	 * 
	 * @param name
	 *            Nome da configuração
	 * @return
	 */
	public String getString(String name) {
		return preferences.getString(name, null);
	}

	/**
	 * Retorna uma configuração String e utiliza Integer.parseInt para conversão
	 * 
	 * @param name
	 *            Nome da configuração
	 * @return
	 */
	public int getInt(String name) {
		return Integer.parseInt(getString(name));
	}
	
	/**
	 * Retorna uma configuração Boolean
	 * 
	 * @param name
	 *            Nome da configuração
	 * @return
	 */
	public boolean getBoolean (String name) {
		return preferences.getBoolean(name, false);
	}
		
	/**
	 * Retorna uma configuração do tipo JSONObject
	 * 
	 * @param name
	 *            Nome da configuração
	 * @return
	 * @throws JSONException
	 */
	public JSONObject getJSONObject(String name) throws JSONException {
		return new JSONObject(getString(name));
	}

	/**
	 * Retorna uma configuração do tipo JSONArray
	 * 
	 * @param name
	 *            Nome da configuração
	 * @return
	 * @throws JSONException
	 */
	public JSONArray getJSONArray(String name) throws JSONException {
		String str = getString(name);
		if (str == null || str.isEmpty()) {
			return null;
		} else {
			return new JSONArray(str);
		}
	}

	/**
	 * Testa se existe a chave nas configurações
	 * 
	 * @param key
	 *            Nome da configuração
	 * @return boolean
	 */
	public boolean isSet(String key) {
		return preferences.contains(key);
	}	

	/**
	 * Retorna a instância singleton
	 * 
	 * @return
	 */
	public static ConfigurationManager get() {
		return instance;
	}
	
	/**
	 * Retorna todas as preferências
	 * @return
	 */
	public Map<String, Object> getAll(){
		return (Map<String, Object>) preferences.getAll();
	}// End Method 'getAll'
	
	

}
