package com.panfletovia.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.panfletovia.R;
import com.panfletovia.annotation.Field;
import com.panfletovia.base.ConfigurationManager;
import com.panfletovia.base.Dialogs;
import com.panfletovia.controllers.RegisterController;
import com.panfletovia.type.PublicEnums;
import com.panfletovia.utils.Utils;

public class RegisterActivity extends BaseActivity {

	@Field(id = R.id.register_email, required = true, message = R.string.field_is_required)
	private EditText txtEmail;
	@Field(id = R.id.register_password, required = true, message = R.string.field_is_required)
	private EditText txtPassword;
	@Field(id = R.id.register_confirm_password, required = true,  message = R.string.field_is_required)
	private EditText txtZConfirmPassword;
	
	private RegisterController registerController;
	private Dialogs dialogs;
	private ConfigurationManager configuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		init();
		
		// Busca inst�ncia das prefer�ncias do usu�rio
//		configuration = ConfigurationManager.get();
//		// Valida se o campo com o id do usu�rio existe
//		if (configuration.isSet(Constants.PREFERENCES_CLIENT_ID)) {
//			// Extrai o id do usu�rio
//			int isLogged = configuration.getInt(Constants.PREFERENCES_CLIENT_ID);
//			// Valida se � um valor v�lido
//			if (isLogged > 0) {
				// Caso for, inicializa a activity dos menus
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
			dialogs = new Dialogs(this);
			registerController = new RegisterController(this, dialogs);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Authentication User. Called by XML file
	 * 
	 * @param view
	 */
	public void register(View view) {
		
		try {
			if (!Utils.validateRequiredFields(this)){
				return;
			}
		} catch (Exception e) {
			Utils.validationErrors(this, "Error: " + e.getMessage());
			return;
		}
		
		String email = txtEmail.getText().toString();
		String password = txtPassword.getText().toString();
		String confirmPassword = txtZConfirmPassword.getText().toString();
		
		if (password.length() < 6) {
			Utils.validationErrors(this, "A senha deve conter no minimo 6 caracteres!");
			return;
		}
		
		if (!password.equals(confirmPassword)) {
			Utils.validationErrors(this, "Senhas diferentes. Por favor, digite novamente.");
			return;
		}
		
		String version = android.os.Build.VERSION.RELEASE;
		
		// Salva nas preferências a url referente a cidade selecionada.
		registerController.register(email, password, PublicEnums.ANDROID.getDescription(), version);
	}// End Method 'register'
}// End Class