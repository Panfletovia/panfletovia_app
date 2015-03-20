package com.s2parking.controller;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.s2parking.activities.MainActivity;
import com.s2parking.api.ApiListener;
import com.s2parking.api.ApiManager;
import com.s2parking.api.URL;
import com.s2parking.enums.Constants;
import com.s2parking.enums.Fields;
import com.s2parking.type.HttpMethod;
import com.s2parking.utils.ConfigurationManager;
import com.s2parking.utils.Dialogs;
import com.s2parking.utils.Utils;

/**
 * Controller responsável por validar o login do usuário e salvar nas preferêrencias algumas informações
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
	 * Efetua a autenticação do usuário.
	 * @param user
	 * @param password
	 */
	public void signin(String user, String password) {
		ApiManager apiManager = new ApiManager(URL.CONTROLLER.LOGIN_MOBILE, HttpMethod.POST, this);
		apiManager.addParam(Fields.LOGIN, user);
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
