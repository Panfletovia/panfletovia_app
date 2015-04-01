package com.panfletovia.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.panfletovia.R;
import com.panfletovia.annotation.Field;
import com.panfletovia.controllers.LoginController;
import com.panfletovia.utils.Utils;

public class LoginActivity extends Activity {

	@Field(id = R.id.login_edt_user)
	private EditText edtUser;
	@Field(id = R.id.login_edt_password)
	private EditText edtPassword;
	private LoginController loginController;
//	private Dialogs dialogs;
//	private ConfigurationManager configuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
		
		// Busca inst�ncia das prefer�ncias do usu�rio
//		configuration = ConfigurationManager.get();
//		// Valida se o campo com o id do usu�rio existe
//		if (configuration.isSet(Constants.PREFERENCES_CLIENT_ID)) {
//			// Extrai o id do usu�rio
//			int isLogged = configuration.getInt(Constants.PREFERENCES_CLIENT_ID);
//			// Valida se � um valor v�lido
//			if (isLogged > 0) {
//				// Caso for, inicializa a activity dos menus
//				startActivity(new Intent(this, MainActivity.class));
//				finish();
//			}
//		}
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
//			dialogs = new Dialogs(this);
//			loginController = new LoginController(this, dialogs);
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
//		// Salva nas prefer�ncias a url referente a cidade selecionada.
//		loginController.signin(edtUser.getText().toString(), edtPassword.getText().toString());
	}
}// End Class