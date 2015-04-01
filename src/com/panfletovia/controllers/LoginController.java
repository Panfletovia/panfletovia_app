package com.panfletovia.controller;

import java.net.URL;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.provider.SyncStateContract.Constants;

import com.panfletovia.activities.MainActivity;
import com.panfletovia.api.ApiListener;
import com.panfletovia.api.ApiManager;
import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Utils;

/**
 * Controller respons�vel por validar o login do usu�rio e salvar nas prefer�rencias algumas informa��es
 * @author desenv03
 *
 */
public class LoginController implements ApiListener {
	
	private Activity loginActivity;
	private Dialogs dialogs;
	private ConfigurationManager configuration;
	
	public LoginController(Activity activity, Dialogs dialogs) {
		this.loginActivity = activity;
		this.dialogs = dialogs;
		configuration = ConfigurationManager.get();
	}
	
	/**
	 * Efetua a autentica��o do usu�rio.
	 * @param user
	 * @param password
	 */
	public void signin(String user, String password) {
		ApiManager apiManager = new ApiManager(URL.CONTROLLER.AUTHORIZATION, HttpMethod.POST, this);
		apiManager.addParam(Fields.USERNAME, user);
		apiManager.addParam(Fields.PASSWORD, password);
		apiManager.execute();
	}	
	
	@Override
	public void onStarted() {
		dialogs.openProgressDialog();
	}
	@Override
	public void onSucceed(JSONObject response) {
		try {
			configuration.set(Constants.PREFERENCES_CLIENT_ID, response.get(Fields.CLIENT_ID).toString());
			configuration.set(Constants.PREFERENCES_PLATES, response.getJSONArray(Constants.PREFERENCES_PLATES));
			configuration.set(Constants.PREFERENCES_AREAS, response.getJSONArray(Fields.AREAS));
			configuration.set(Constants.PREFERENCES_SALDO_PRE, response.get(Fields.SALDO_PRE).toString());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		loginActivity.startActivity(new Intent(loginActivity, MainActivity.class));
		loginActivity.finish();
	}
	@Override
	public void onFailed(JSONObject response) {
		try {
			Utils.validationErros(loginActivity, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void onFinished() {
		dialogs.closeProgressDialog();
	}
}
