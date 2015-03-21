package com.panfletovia.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.panfletovia.annotation.Field;
import com.panfletovia.controller.LoginController;
import com.panfletovia.enums.Constants;
import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Utils;
import com.s2parking.activities.R;

/**
 * Activity do login do usuário
 * @author desenv03
 *
 */
public class LoginActivity extends Activity {

	@Field(id = R.id.login_edt_user)
	private EditText edtUser;
	@Field(id = R.id.login_edt_password)
	private EditText edtPassword;
	private LoginController loginController;
	private Dialogs dialogs;
	private ConfigurationManager configuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		
		// Busca instância das preferências do usuário
		configuration = ConfigurationManager.get();
		// Valida se o campo com o id do usuário existe
		if (configuration.isSet(Constants.PREFERENCES_CLIENT_ID)) {
			// Extrai o id do usuário
			int isLogged = configuration.getInt(Constants.PREFERENCES_CLIENT_ID);
			// Valida se é um valor válido
			if (isLogged > 0) {
				// Caso for, inicializa a activity dos menus
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
	 * Método para inicializar a activity
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
	 * Authentication User
	 * 
	 * @param view
	 */
	public void signin(View view) {
		// Salva nas preferências a url referente a cidade selecionada.
		loginController.signin(edtUser.getText().toString(), edtPassword.getText().toString());
	}
}// End Class