package com.panfletovia.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.panfletovia.annotation.Field;
import com.panfletovia.controller.PlatesController;
import com.panfletovia.enums.Constants;
import com.panfletovia.enums.VehicleType;
import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Mask;
import com.panfletovia.utils.Utils;
import com.s2parking.activities.R;

/**
 * Activity responsável por controlar a adição e remoção das placas
 * @author desenv03
 *
 */
public class PlatesActivity extends Activity {
		
	private PlatesController platesController;
	private Dialogs dialogs;
	private TextWatcher twPlatePTBR;
	private TextWatcher twPlateForeign;
	private CheckBox checkForeign;
	private EditText edtTextPlate;
	private RadioGroup radioGroup;
	private ConfigurationManager configuration = ConfigurationManager.get();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_plates);
		init();
		changeCity();
	}
	
	/**
	 * Inicializa controller e componentes.
	 */
	private void init() {
		try {
			Utils.initializeFields(this);
			Utils.setTitle(this, R.string.menu_title_plates);
			dialogs = new Dialogs(this);
			platesController = new PlatesController(this, dialogs);
			platesController.mountPlates();			
		} catch (Exception ex) {
			ex.printStackTrace();
		}		
	}// End Method 'init'
	
	/**
	 * Adiciona uma nova placa a entidade logada.
	 * @param view
	 */
	public void addPlate(View view) {
		dialogs.createDialog(this, R.layout.dialog_input, getString(R.string.title_add_plate), new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String type = "";
				//Se o radio car estiver marcado, type recebe "CARRO".
				//Se não recebe "MOTO".
				type = radioGroup.getCheckedRadioButtonId() == R.id.dialog_input_radiocar ? VehicleType.CARRO.name() : VehicleType.MOTO.name();
				platesController.addPlate(edtTextPlate.getText().toString(), type, checkForeign.isChecked());
				dialog.dismiss();
			}
		}, null);
		
		//Instancia o ediText
		edtTextPlate = (EditText) dialogs.getView().findViewById(R.id.dialog_input_edttext);
		//Instancia o checkbox
		checkForeign = (CheckBox) dialogs.getView().findViewById(R.id.dialog_input_foreign);
		//Instancia o RadioGroup
		radioGroup = (RadioGroup) dialogs.getView().findViewById(R.id.dialog_input_radiogroup);
		
		twPlatePTBR = Mask.insertPlate(edtTextPlate);
		twPlateForeign = Mask.insertForeignPlate(edtTextPlate);
		
		//Adiciona a máscara no formato brasileiro como padrão
		edtTextPlate.addTextChangedListener(twPlatePTBR);
	}
	
	/**
	 * Altera a máscara da placa, conforme o checkbox(foreign) marcado.
	 * @param view
	 */
	public void foreign(View view) {
		//Limpa o editText
		edtTextPlate.setText(null);
		//Se o check está marcado.
		if (checkForeign.isChecked()) {
			edtTextPlate.removeTextChangedListener(twPlatePTBR);			
			edtTextPlate.addTextChangedListener(twPlateForeign);
		} else {
			edtTextPlate.removeTextChangedListener(twPlateForeign);			
			edtTextPlate.addTextChangedListener(twPlatePTBR);
		}		
	}
	
	/**
	 * Percorre todos os checkbox marcados, para concatenar os ids das placas. Antes de fazer a requisição para API.
	 * @param view
	 */
	public void removePlate(View view) {
		
		dialogs.confirm(getString(R.string.question),
				getString(R.string.question_remove_plate), 
				new OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						String strIds = "";
						for (CheckBox check : platesController.getListPlates().keySet()) {
							if (check.isChecked()) {
								int id = platesController.getListPlates().get(check);
								strIds = strIds + id + ";";
							}
						}
						platesController.removePlates(strIds);
					}
		});
	}// End Method 'removePlate'
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, MainActivity.class));
		finish();
	}// End Method 'onBackPressed
	
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
	
}// End Class