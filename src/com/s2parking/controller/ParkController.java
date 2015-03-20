package com.s2parking.controller;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import com.s2parking.activities.HistoryActivity;
import com.s2parking.activities.R;
import com.s2parking.api.ApiListener;
import com.s2parking.api.ApiManager;
import com.s2parking.api.URL;
import com.s2parking.enums.Constants;
import com.s2parking.enums.Fields;
import com.s2parking.type.HttpMethod;
import com.s2parking.utils.ConfigurationManager;
import com.s2parking.utils.Dialogs;
import com.s2parking.utils.Utils;

/**
 * Controller responsável por validar a compra de período
 * @author desenv03
 *
 */
public class ParkController implements ApiListener {
	
	private Activity parkActivity;
	private Dialogs dialogs;
	private ConfigurationManager configuration;
	
	public ParkController(Activity activity, Dialogs dialogs) {
		this.parkActivity = activity;
		this.dialogs = dialogs;
		configuration = ConfigurationManager.get();
	}
	
	/**
	 * Efetua a requisição da compra de período.
	 * @param plate
	 * @param areaId
	 * @param range
	 */
	public void buy(String plate, int areaId, int range) {
		ApiManager apiManager = new ApiManager(URL.CONTROLLER.PERIOD_PURCHASE_MOBILE, HttpMethod.POST, this);
		apiManager.addParam(Fields.PLACA, plate);
		apiManager.addParam(Fields.AREA_ID, String.valueOf(areaId));
		apiManager.addParam(Fields.PERIODOS, String.valueOf(range));
		apiManager.addParam(Constants.PREFERENCES_CLIENT_ID, configuration.getString(Fields.CLIENT_ID));
		apiManager.execute();
	}

	@Override
	public void onStarted() {
		dialogs.openProgressDialog();
	}

	@Override
	public void onSucceed(JSONObject response) {
		//Extrai o saldo_pre, e salva nas preferências
		try {
			Toast.makeText(parkActivity, parkActivity.getString(R.string.confirmation_buy_ticket), Toast.LENGTH_LONG).show();
			configuration.set(Constants.PREFERENCES_SALDO_PRE, response.getString(Fields.SALDO_PRE));			
		} catch (JSONException ex) {
			Toast.makeText(parkActivity, ex.getMessage(), Toast.LENGTH_LONG).show();
		} finally {
			parkActivity.startActivity(new Intent(parkActivity, HistoryActivity.class));
			parkActivity.finish();	
		}
	}

	@Override
	public void onFailed(JSONObject response) {
		try {
			Utils.validationErros(parkActivity, response);			
		} catch (JSONException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void onFinished() {
		dialogs.closeProgressDialog();
	}
}
