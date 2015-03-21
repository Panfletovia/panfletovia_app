package com.panfletovia.controller;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.Toast;

import com.panfletovia.api.ApiListener;
import com.panfletovia.api.ApiManager;
import com.panfletovia.api.URL;
import com.panfletovia.enums.Constants;
import com.panfletovia.enums.Fields;
import com.panfletovia.enums.VehicleType;
import com.panfletovia.type.HttpMethod;
import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Utils;
import com.s2parking.activities.R;

/**
 * Controller responsável por montar e validar ações na listagem de placas do usuário
 * @author desenv03
 *
 */
public class PlatesController {
	
	
	private Activity activity;
	private Dialogs dialogs;
	private Map<CheckBox, Integer> map;
	private ApiManager apiManager;
	private ConfigurationManager configuration = ConfigurationManager.get();
	
	public PlatesController(Activity activity, Dialogs dialogs) {
		this.activity = activity;
		this.dialogs = dialogs;		
	}	
	
	/**
	 * Monta os checkbox com as placas.
	 */
	public void mountPlates() {
		map = new HashMap<CheckBox, Integer>();
		final LinearLayout linear = (LinearLayout) activity.findViewById(R.id.plates_linearlayout);
		//Limpa todos os view do linear, antes de carregar os dados.
		linear.removeAllViews();
		try {
			String strJSONPlates = ConfigurationManager.get().getString(Constants.PREFERENCES_PLATES);
			JSONArray arrayPlates = new JSONArray(strJSONPlates);
			for (int i = 0; i < arrayPlates.length(); i++) {
				JSONObject objRoot = arrayPlates.getJSONObject(i);
				JSONObject objChild = objRoot.getJSONObject(Fields.JSON_PLACA);
				
				//Instância um novo LinearLayout
				final LinearLayout linearData = new LinearLayout(activity);
				//Seta width e height
				linearData.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
				//Seta a orientação do LinearLayout.
				linearData.setOrientation(LinearLayout.HORIZONTAL);
				//Seta o padding
				linearData.setPadding(5,5,5,0);
				
				//Cria um checkbox e add no LinearLayout
				int id = objChild.getInt(Fields.ID);
				String plate = objChild.getString(Fields.PLACA);
				//Instância um checkbox.
				final CheckBox checkBox = new CheckBox(activity);
				//Seta o weight
				checkBox.setLayoutParams(new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));
				//Seta a descrição do check com a placa.
				checkBox.setText(plate);
				//Adiciona no linearlayout.
				linearData.addView(checkBox);

				//Instancia um ImageView
				ImageView imageView = new ImageView(activity);
				//Se carro, então seta a imagem car.png
				//Se não set a imagem motorcycle.png
				if (objChild.getString(Fields.TIPO).equals(VehicleType.CARRO)) {
					imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.car));	
				} else {
					imageView.setImageDrawable(activity.getResources().getDrawable(R.drawable.motorcycle));
				}
				
				//Adiciona no linearlayout
				linearData.addView(imageView);
				
				//Cria um separador e add no LinearLayout				
				View view = new View(activity);
				view.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 1));
				view.setBackgroundColor(activity.getResources().getColor(R.color.gray));
				
				linear.addView(view);
				linear.addView(linearData);
				map.put(checkBox, id);
			}			
						
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	/**
	 * Faz um POST para API, com a placa que deverá ser adicionada.
	 * @param plate
	 */
	public void addPlate(final String plate, String type, boolean isForeign) {
		
		// Valida tamanho da placa brasileira
		if(!isForeign){
			if(plate.length() != 8){
				Toast.makeText(activity, activity.getString(R.string.error_invalid_plate), Toast.LENGTH_SHORT).show();
				return;
			}
		}
		
		apiManager = new ApiManager(URL.CONTROLLER.PLATES_MOBILE, HttpMethod.POST, new ApiListener() {
			
			@Override
			public void onSucceed(JSONObject response) {
				try {
					configuration.set(Constants.PREFERENCES_PLATES, response.getJSONArray(Fields.PLATES));
					mountPlates();
					dialogs.alert(activity.getString(R.string.confirmation), activity.getString(R.string.confirmation_add_plate).replace("XXX", plate), new android.content.DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});					
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			
			@Override
			public void onStarted() {
				dialogs.openProgressDialog();				
			}
			
			@Override
			public void onFinished() {
				dialogs.closeProgressDialog();
			}
			
			@Override
			public void onFailed(JSONObject response) {
				try {
					Utils.validationErros(activity, response);
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		apiManager.addParam(Fields.CLIENT_ID, String.valueOf(configuration.getInt(Fields.CLIENT_ID)));
		apiManager.addParam(Fields.PLATE_TYPE, type);
		apiManager.addParam(Fields.PLATE, plate);
		apiManager.addParam(Fields.CHECK_FOREIGN_PLATE, String.valueOf(isForeign));
		apiManager.execute();
	}
	
	/**
	 * Faz um POST para API, com os ids das placas que devem ser removidas.
	 * Os ids são concatenados por ";".
	 * @param ids
	 */
	public void removePlates(String ids) {
		apiManager = new ApiManager(URL.CONTROLLER.PLATES_MOBILE, URL.ACTION.REMOVE, HttpMethod.POST, new ApiListener() {
			
			@Override
			public void onSucceed(JSONObject response) {
				try {
					configuration.set(Constants.PREFERENCES_PLATES, response.getJSONArray(Fields.PLATES));
					mountPlates();
					dialogs.alert(activity.getString(R.string.warning), activity.getString(R.string.confirmation_remove_plate), new android.content.DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});					
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
			
			@Override
			public void onStarted() {
				dialogs.openProgressDialog();
			}
			
			@Override
			public void onFinished() {
				dialogs.closeProgressDialog();
			}
			
			@Override
			public void onFailed(JSONObject response) {
				try {
					Utils.validationErros(activity, response);					
				} catch (JSONException ex) {
					ex.printStackTrace();
				}
			}
		});
		
		apiManager.addParam(Fields.CLIENT_ID, String.valueOf(configuration.getInt(Fields.CLIENT_ID)));
		apiManager.addParam(Fields.PLATES, ids);
		apiManager.execute();
	}
	
	/**
	 * Retorna o map com os checkbox e os ids das placas.
	 * @return
	 */
	public Map<CheckBox, Integer> getListPlates() {
		return map;
	}
		
}
