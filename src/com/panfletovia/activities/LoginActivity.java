package com.panfletovia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.panfletovia.R;
import com.panfletovia.annotation.Field;
import com.panfletovia.base.ConfigurationManager;
import com.panfletovia.base.Dialogs;
import com.panfletovia.controllers.LoginController;
import com.panfletovia.type.Constants;
import com.panfletovia.utils.Utils;

public class LoginActivity extends BaseActivity {

	@Field(id = R.id.login_edt_user)
	private EditText edtUser;
	@Field(id = R.id.login_edt_password)
	private EditText edtPassword;
	@Field(id = R.id.keep_logged_in)
	private CheckBox checkBoxKeepLogged;
	
	private LoginController loginController;
	private Dialogs dialogs;
	private ConfigurationManager configuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		
		// Inicializa a classe de configuração das preferências do usuário
		ConfigurationManager.initialize(this);
		// Pega instância das preferências do usuário
		configuration = ConfigurationManager.get();
		// Valida se a configuração do id do cliente está populado
		if (configuration.isSet(Constants.PREFERENCES_KEEP_LOGGED_IN)) {
			// Extrai informação da preferências
			boolean keepLogged = configuration.getBoolean(Constants.PREFERENCES_KEEP_LOGGED_IN);
			// Testa informação para inicializar diretamente a activity principal
			if (keepLogged) {
				// Caso o usuário tenha escolhido por manter-se conectado, inicializa a MainActivity 
				startActivity(new Intent(this, MainActivity.class));
				finish();
			}
		}
	}// End Method 'onCreate'
	
	@Override
	protected void onResume() {
		super.onResume();
	};
	
	/**
	 * M�todo para inicializar a activity
	 */
	private void init() {
		try {
			Utils.initializeFields(this);
			dialogs = new Dialogs(this);
			loginController = new LoginController(this, dialogs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Authentication User. Called by XML file
	 * 
	 * @param view
	 */
	public void signin(View view) {
		// Salva nas preferências a opção de manter logado
		configuration.set(Constants.PREFERENCES_KEEP_LOGGED_IN, checkBoxKeepLogged.isChecked());
		
		// Envia requisição
		loginController.signin(edtUser.getText().toString(), edtPassword.getText().toString());
	}
	
	public void createAccount (View view) {
		startActivity(new Intent(this, RegisterActivity.class));
		finish();
	}
}// End Class