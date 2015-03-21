package com.panfletovia.controller;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;

import com.panfletovia.activities.MainActivity;
import com.panfletovia.api.ApiListener;
import com.panfletovia.api.ApiManager;
import com.panfletovia.api.URL;
import com.panfletovia.enums.Fields;
import com.panfletovia.type.HttpMethod;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Formatter;
import com.panfletovia.utils.Utils;
import com.s2parking.activities.R;

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
