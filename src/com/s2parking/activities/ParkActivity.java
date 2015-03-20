package com.s2parking.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;

import com.s2parking.annotation.Field;
import com.s2parking.bean.SpinnerItem;
import com.s2parking.controller.ParkController;
import com.s2parking.enums.Constants;
import com.s2parking.enums.Fields;
import com.s2parking.utils.ConfigurationManager;
import com.s2parking.utils.Dialogs;
import com.s2parking.utils.Formatter;
import com.s2parking.utils.Utils;
import com.s2parking.widget.SpinnerAdapter;

/**
 * Activity responsável por controlar a compra de períodos
 * @author desenv03
 *
 */
public class ParkActivity extends Activity implements OnItemSelectedListener {

	@Field(id = R.id.park_vehicles)
	private Spinner vehicles;
	@Field(id = R.id.park_area)
	private Spinner area;
	@Field(id = R.id.park_time)
	private Spinner time;
	private ConfigurationManager configuration = ConfigurationManager.get();
	private JSONArray arrayAreas;
	private Dialogs dialogs;
	private ParkController parkController;
	private SendItem sendItem;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_park);
		init();
		changeCity();
	}

	/**
	 * Método para inicializar os componentes
	 */
	private void init() {
		try {
			dialogs = new Dialogs(this);
			parkController = new ParkController(this, dialogs);
			sendItem = new SendItem();
			Utils.initializeFields(this);
			Utils.setTitle(this, R.string.menu_title_park);
			populatePlates();
			populateAreas();
			vehicles.setOnItemSelectedListener(this);
			area.setOnItemSelectedListener(this);
			time.setOnItemSelectedListener(this);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// End Method 'init'

	/**
	 * Popula as placas
	 * 
	 * @throws JSONException
	 */
	private void populatePlates() throws JSONException {
		final List<SpinnerItem> list = new ArrayList<SpinnerItem>();
		JSONArray root = configuration.getJSONArray(Fields.PLATES);
		for (int i = 0; i < root.length(); i++) {
			JSONObject objPlates = root.getJSONObject(i);
			JSONObject objPlate = objPlates.getJSONObject(Fields.JSON_PLACA);
			list.add(new SpinnerItem((i + 1), objPlate.getString(Fields.PLACA)));
		}
		SpinnerAdapter adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, list);
		vehicles.setAdapter(adapter);
	}// End Method 'populatePlates'

	/**
	 * Popula as áreas
	 * 
	 * @throws JSONException
	 */
	private void populateAreas() throws JSONException {
		final List<SpinnerItem> list = new ArrayList<SpinnerItem>();
		arrayAreas = configuration.getJSONArray(Fields.AREAS);
		for (int i = 0; i < arrayAreas.length(); i++) {
			JSONObject objAreas = arrayAreas.getJSONObject(i);
			JSONObject objArea = objAreas.getJSONObject(Fields.JSON_AREA);
			list.add(new SpinnerItem(objArea.getInt(Fields.ID), objArea.getString(Fields.NOME)));

		}
		SpinnerAdapter adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, list);
		area.setAdapter(adapter);
	}// End Method 'populateAreas'

	/**
	 * Popula as tarifas, de acordo com a área selecionada.
	 */
	private void populateFare(int areaId, String areaNome) throws JSONException {
		final List<SpinnerItem> list = new ArrayList<SpinnerItem>();
		out: for (int i = 0; i < arrayAreas.length(); i++) {
			JSONObject objAreas = arrayAreas.getJSONObject(i);
			JSONObject objArea = objAreas.getJSONObject(Fields.JSON_AREA);
			if (objArea.getInt(Fields.ID) == areaId && objArea.getString(Fields.NOME).equals(areaNome)) {
				// Extrai as tarifas e preços.
				String arrayPrecos = objArea.getString(Fields.PRECOS);
				// Se preço for vazio.
				if (arrayPrecos.toString().equals("[]")) {
					list.add(new SpinnerItem(0, "(R$ 0,00)"));
				} else {
					JSONObject objPrecos = objArea.getJSONObject(Fields.PRECOS);
					// Se tem PrecoCarro
					if (objPrecos.has(Fields.JSON_PRECO_CARRO)) {
						JSONObject precoCarro = objPrecos.getJSONObject(Fields.JSON_PRECO_CARRO);
						JSONArray arrayTarifas = precoCarro.getJSONArray(Fields.TARIFAS);
						// Varre as tarifas
						for (int tar = 0; tar < arrayTarifas.length(); tar++) {
							JSONObject objTarifa = arrayTarifas.getJSONObject(tar);
							BigDecimal bigDecimal = new BigDecimal(objTarifa.getString(Fields.VALOR));
							String desc = objTarifa.getString(Fields.MINUTOS)
									+ " min ("
									+ Formatter.get().currency(bigDecimal.longValue()) + ")";
							list.add(new SpinnerItem(objTarifa.getInt(Fields.CODIGO), desc));
						}
					}
				}
				break out;
			}
		}
		SpinnerAdapter adapter = new SpinnerAdapter(this, android.R.layout.simple_spinner_item, list);
		time.setAdapter(adapter);
	}// End Method 'populateFare'

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}

	@Override
	public void onItemSelected(AdapterView<?> adapter, View view, int position, long id) {
		try {
			SpinnerItem spinnerItem = (SpinnerItem) adapter.getItemAtPosition(position);
			if (adapter.getId() == R.id.park_vehicles) {
				sendItem.setPlate(spinnerItem.getDescription());
			} else if (adapter.getId() == R.id.park_area) {
				int areaId = spinnerItem.getId();
				sendItem.setAreaId(areaId);
				sendItem.setAreaName(spinnerItem.getDescription());
				populateFare(areaId, spinnerItem.getDescription());
			} else if (adapter.getId() == R.id.park_time) {
				sendItem.setRange(spinnerItem.getId());
			}
		} catch (JSONException ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
	}

	/**
	 * Cancela a compra de período
	 * 
	 * @param view
	 */
	public void cancel(View view) {
		dialogs.confirm("",
				getString(R.string.question_cancel_ticket),
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						startActivity(new Intent(ParkActivity.this, MainActivity.class));
						finish();
					}
				});
	}
	
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

	/**
	 * Confirma a compra de período.
	 * 
	 * @param view
	 */
	public void buy(View view) {
		dialogs.confirm(getString(R.string.question),
				getString(R.string.question_buy_ticket)
					.replace("XXX", "'"+sendItem.getPlate()+"'")
					.replace("YYY", "'"+sendItem.getAreaName()+"'"),
			new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					parkController.buy(sendItem.getPlate(), sendItem.getAreaId(), sendItem.getRange());
				}
			});
	}// End Method 'buy'
	
	/**
	 * Items enviados na compra de período.
	 * @author diego
	 *
	 */
	private class SendItem {
		
		private String plate;
		private int areaId;
		private String areaName;
		private int range;
		
		public void setPlate(String plate) {
			this.plate = plate;
		}
		
		public String getPlate() {
			return plate;
		}
		
		public void setAreaId(int areaId) {
			this.areaId = areaId;
		}
		
		public int getAreaId() {
			return areaId;
		}
		
		public void setAreaName(String areaName){
			this.areaName = areaName;
		}
		
		public String getAreaName(){
			return areaName;
		}
		
		public void setRange(int range) {
			this.range = range;
		}
		
		public int getRange() {
			return range;
		}
	}// End Class 'SendItem'
}// End Class