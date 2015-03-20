package com.s2parking.controller;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.s2parking.activities.MainActivity;
import com.s2parking.activities.R;
import com.s2parking.api.ApiListener;
import com.s2parking.api.ApiManager;
import com.s2parking.api.URL;
import com.s2parking.enums.Fields;
import com.s2parking.type.HttpMethod;
import com.s2parking.utils.Dialogs;
import com.s2parking.utils.Formatter;
import com.s2parking.utils.Utils;

/**
 * Controller responsável por interromper o ticket ativo.
 * @author diego
 *
 */
public class FullHistoryController implements ApiListener {
	
	private Activity activity;
	private Dialogs dialogs;
	
	public FullHistoryController(Activity activity, Dialogs dialogs) {
		this.activity = activity;
		this.dialogs = dialogs;
	}
	
	/**
	 * Faz a requisição para api.
	 * @param ticketId
	 */
	public void stopPeriod(int ticketId) {
		ApiManager apiManager = new ApiManager(URL.CONTROLLER.PERIOD_PURCHASE_MOBILE, URL.ACTION.DELETE + "/" + ticketId, HttpMethod.POST, this);
		apiManager.execute();
	}
	
	@Override
	public void onStarted() {
		dialogs.openProgressDialog();
	}

	@Override
	public void onSucceed(JSONObject response) {
		try {
			// Mostra mensagem para o usuário com a quantidade a ser devolvido 
			BigDecimal cents = new BigDecimal(response.getString(Fields.VALOR));
			dialogs.alert(activity.getString(R.string.warning),
					activity.getString(R.string.warning_interrupt_ticket).replace("XXX", Formatter.get().currency(cents.longValue()))
					, new OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					activity.startActivity(new Intent(activity, MainActivity.class));
					activity.finish();
				}
			});
		} catch(JSONException ex) {
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
	}

}
