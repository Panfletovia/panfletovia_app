package com.panfletovia.controller;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.panfletovia.api.ApiListener;
import com.panfletovia.api.ApiManager;
import com.panfletovia.api.URL;
import com.panfletovia.bean.AreaItem;
import com.panfletovia.enums.Fields;
import com.panfletovia.type.HttpMethod;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Utils;
import com.panfletovia.widget.AreaAdapter;
import com.s2parking.activities.R;

/**
 * Controller responsável por buscar as vagas disponíveis na área selecionada
 * @author desenv03
 *
 */
public class SpotsController implements ApiListener {

	private Activity activity;
	private Dialogs dialogs;
	private GoogleMap map;
	
	public SpotsController(Activity activity, Dialogs dialogs, GoogleMap map) {
		this.activity = activity;
		this.dialogs = dialogs;
		this.map = map;
	}
	
	/**
	 * Abre um alert com as áreas para seleção.
	 * @param activity
	 * @param dialogs
	 * @return
	 */
	public void openListAreas(List<AreaItem> items) {
		final View viewDialog = dialogs.createDialog(activity, R.layout.dialog_area, activity.getString(R.string.select_areas));
		final ListView listArea = (ListView) viewDialog.findViewById(R.id.dialog_area_list);
		final AreaAdapter adapter = new AreaAdapter(activity, R.layout.item_dialog_area, items);
		listArea.setAdapter(adapter);
		
		listArea.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
				final AreaItem areaItem = (AreaItem) adapter.getItemAtPosition(position);
				ApiManager api = new ApiManager(URL.CONTROLLER.SPOTS_MOBILE, HttpMethod.GET, SpotsController.this);
				api.addParam(Fields.PARK_AREA_ID, String.valueOf(areaItem.getId()));
				api.execute();
			}
		});
	}	
	
	@Override
	public void onStarted() {
		dialogs.openProgressDialog();
	}

	@Override
	public void onSucceed(JSONObject response) {
		try {
			LatLngBounds.Builder builder = new LatLngBounds.Builder();
			JSONArray arrayAreaPonto = new JSONArray(response.getString(Fields.VAGAS));
			for (int i = 0; i < arrayAreaPonto.length(); i++) {
				JSONObject objRoot = arrayAreaPonto.getJSONObject(i);
				JSONObject objAreaPonto = objRoot.getJSONObject(Fields.JSON_AREA_PONTO);
				//Cria um marker
				MarkerOptions markerOptions = new MarkerOptions();
				//Seta a posição do marker
				markerOptions.position(new LatLng(objAreaPonto.getDouble(Fields.LATITUDE), objAreaPonto.getDouble(Fields.LONGITUDE)));
				//Seta a cor verde	
				markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
				//Adiciona o marker no mapa
				map.addMarker(markerOptions);
				//Adiciona no builder, a posição do marker.
				builder.include(markerOptions.getPosition());
			}
			//Centra os markers na câmera.
			LatLngBounds bounds = builder.build();
			CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 0);
			map.moveCamera(cameraUpdate);			
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onFailed(JSONObject response) {
		try {
			Utils.validationErros(activity, response);			
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onFinished() {
		dialogs.closeProgressDialog();
		dialogs.getAlertDialog().dismiss();
	}

}
