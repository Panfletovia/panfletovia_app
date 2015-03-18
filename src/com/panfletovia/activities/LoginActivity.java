package com.panfletovia.activities;

import android.app.Activity;
import android.os.Bundle;

public class LoginActivity extends Activity {

//	@Field(id = R.id.login_edt_user)
//	private EditText edtUser;
//	@Field(id = R.id.login_edt_password)
//	private EditText edtPassword;
//	@Field(id = R.id.login_spi_cities)
//	private Spinner spiCities;
//	private LoginController loginController;
//	private Dialogs dialogs;
//	private Map<String, String> mapCities;
//	private ConfigurationManager configuration;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
//		init();
		
//		// Busca instância das preferências do usuário
//		configuration = ConfigurationManager.get();
//		// Valida se o campo com o id do usuário existe
//		if (configuration.isSet(Constants.PREFERENCES_CLIENT_ID)) {
//			// Extrai o id do usuário
//			int isLogged = configuration.getInt(Constants.PREFERENCES_CLIENT_ID);
//			// Valida se é um valor válido
//			if (isLogged > 0) {
//				// Caso for, inicializa a activity dos menus
//				startActivity(new Intent(this, MainActivity.class));
//				finish();
//			}
//		}
//		// Verifica qual é o padrão de cidade.
//		if (configuration.isSet(Constants.PREFERENCES_LOCAL_BASE)) {
//			// Extrai o id do usuário
//			String keyCity = configuration.getString(Constants.PREFERENCES_LOCAL_BASE);
//			spiCities.setSelection(Integer.valueOf(keyCity));
//		}
		
		
	}// End Method 'onCreate'
	
	@Override
	protected void onResume() {
		super.onResume();
	};
	
//	/**
//	 * Método para inicializar a activity
//	 */
//	private void init() {
//		try {
//			Utils.initializeFields(this);
//			dialogs = new Dialogs(this);
//			loginController = new LoginController(this, dialogs);
//			// Monta o combo com as cidades.
//			mapCities = Utils.getHashMapResource(this, R.xml.map_cities);
//			//Se o tamanho do mapCities for 1, então esconde o spiCities.
//			if (mapCities.size() == 1) {
//				spiCities.setVisibility(View.INVISIBLE);
//			}
//			List<String> items = new ArrayList<String>();
//			for (String key : mapCities.keySet()) {
//				items.add(key);
//			}
//			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, items);
//			spiCities.setAdapter(adapter);
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//	}
//
//	/**
//	 * Authentication User
//	 * 
//	 * @param view
//	 */
//	public void signin(View view) {
//		// Salva nas preferências a url referente a cidade selecionada.
//		String key = spiCities.getSelectedItem().toString();
//		ConfigurationManager.get().set(Constants.PREFERENCES_URL_BASE, mapCities.get(key));
//		ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_BASE, String.valueOf(spiCities.getSelectedItemPosition()));
//		loginController.signin(edtUser.getText().toString(), edtPassword.getText().toString());
//		ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_TITLE, String.valueOf(key));
//		
//		//Com a opção da cidade, salva nas preferências o logo da operação que será mostrado na barra superior das telas
//		if (key.equalsIgnoreCase("Búzios")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_summer_parking));
//		}else if (key.equalsIgnoreCase("Florianópolis")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_minha_vaga));
//		}else if (key.equalsIgnoreCase("Iguatu")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_sinart));
//		}else if (key.equalsIgnoreCase("Itabuna")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_minha_vaga));
//		}else if (key.equalsIgnoreCase("Lajeado")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_stacione));
//		}else if (key.equalsIgnoreCase("Muriaé")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_muriae));
//		}else if (key.equalsIgnoreCase("Panambi")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_stacione));
//		}else if (key.equalsIgnoreCase("Rondonópolis")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_rondonopolis));
//		}else if (key.equalsIgnoreCase("Santa Cruz")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_santa_cruz));
//		}else if (key.equalsIgnoreCase("São Borja")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_sao_borja));
//		}else if (key.equalsIgnoreCase("Taquari")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_stacione));
//		}else if (key.equalsIgnoreCase("Treinamento")) {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_rondonopolis));
//		} else {
//			ConfigurationManager.get().set(Constants.PREFERENCES_LOCAL_LOGO, String.valueOf(R.drawable.logo_sao_borja));
//		}
//	}
}// End Class