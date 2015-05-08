package com.panfletovia.controllers;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;

import com.panfletovia.R;
import com.panfletovia.activities.MainActivity;
import com.panfletovia.api.ApiListener;
import com.panfletovia.api.ApiManager;
import com.panfletovia.api.URL;
import com.panfletovia.base.ConfigurationManager;
import com.panfletovia.base.Dialogs;
import com.panfletovia.base.Toaster;
import com.panfletovia.type.Constants;
import com.panfletovia.type.Fields;
import com.panfletovia.type.HttpMethod;
import com.panfletovia.utils.Utils;

public class RegisterController implements ApiListener {
	
	private Activity registerActivity;
	private Toaster toaster;
	private Dialogs dialogs;
	private ConfigurationManager configuration;
	
	public RegisterController(Activity activity, Dialogs dialogs) {
		this.registerActivity = activity;
		this.dialogs = dialogs;
		configuration = ConfigurationManager.get();
	}
	
	/**
	 * Efetua a autentica��o do usu�rio.
	 * @param user
	 * @param password
	 */
	public void register(String user, String password, String plataform, String version) {
		ApiManager apiManager = new ApiManager(URL.CONTROLLER.CLIENT, HttpMethod.POST, this);
		apiManager.addParam(Fields.LOGIN, user);
		apiManager.addParam(Fields.PASSWORD, password);
		apiManager.addParam(Fields.PLATAFORM, plataform);
		apiManager.addParam(Fields.VERSION, version);
		apiManager.execute();
	}	
	
	@Override
	public void onStarted() {
		dialogs.openProgressDialog();
		toaster = new Toaster(registerActivity);
	}
	
	@Override
	public void onSucceed(JSONObject response) {
		// Grava nas preferências do usuário as configurações necessárias
		if (response != null) {
			try {
				// Valida se o campo 'client_id' está presente na resposta.
				if (response.has(Fields.CLIENT_ID)) {
					// Caso estiver, grava o seu id nas preferências do usuário
					configuration.set(Constants.PREFERENCES_CLIENT_ID, response.get(Fields.CLIENT_ID).toString());
				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// Se o cadastro foi efetuado com sucesso, salva nas preferências automaticamente a configuração de
		// manter o usuário logado
		configuration.set(Constants.PREFERENCES_KEEP_LOGGED_IN, true);
		// Exibe mensagem de cadastro efetuado com sucesso
		toaster.slow(R.string.register_successfully);
		// Inicia aplicação
		registerActivity.startActivity(new Intent(registerActivity, MainActivity.class));
		registerActivity.finish();
	}
	@Override
	public void onFailed(JSONObject response) {
		try {
			Utils.validationErros(registerActivity, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@Override
	public void onFinished() {
		dialogs.closeProgressDialog();
	}
}