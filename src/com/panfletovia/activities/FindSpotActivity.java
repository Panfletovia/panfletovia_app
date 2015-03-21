package com.panfletovia.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.panfletovia.controller.SpotsController;
import com.panfletovia.enums.Constants;
import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Utils;
import com.s2parking.activities.R;


/**
 * Activity para a localização de vagas
 * @author desenv03
 *
 */
public class FindSpotActivity extends FragmentActivity {
	
	private GoogleMap map;
	private SpotsController spotsController;
	private Dialogs dialogs;
	private ConfigurationManager configuration = ConfigurationManager.get();
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_find_spot);
		init();
		changeCity();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		init();
	}
	
	/**
	 * Método para montar a tela da localização das vagas disponíveis
	 */
	private void init() {
		Utils.setTitle(this, R.string.menu_title_find_spot);
		// Valida instância do objeto de mapas do google maps;
		if (map != null) {
			return;
		}
		// Initialize map options. For example:
		map = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
		dialogs = new Dialogs(this);
		spotsController = new SpotsController(this, dialogs, map);
		spotsController.openListAreas(Utils.getAreas());
	}// End Method 'init'
	
	/*
	 * Método Responsavél por trocar o imagem para o logo da operação escolhida
	 * e também alterar o nome da cidade
	 */
	public void changeCity() {
		ImageView img = (ImageView) findViewById(R.id.menu_header_logo);
		int logo = Integer.valueOf(configuration.getInt(Constants.PREFERENCES_LOCAL_LOGO));
		img.setImageResource(logo);
		
		TextView city = (TextView) findViewById(R.id.title_city);
		String city_preferences = String.valueOf(configuration.getString(Constants.PREFERENCES_LOCAL_TITLE)); 
		city.setText(city_preferences);
	}// End Method 'changeCity'
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, MainActivity.class));
	}
}// End Class