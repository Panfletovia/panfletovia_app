package com.s2parking.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.ListView;

import com.s2parking.activities.R;
import com.s2parking.api.ApiListener;
import com.s2parking.api.ApiManager;
import com.s2parking.api.URL;
import com.s2parking.bean.HistoryItem;
import com.s2parking.enums.Constants;
import com.s2parking.enums.Fields;
import com.s2parking.type.HttpMethod;
import com.s2parking.type.ParkHistorico;
import com.s2parking.type.ParkTicket;
import com.s2parking.utils.ConfigurationManager;
import com.s2parking.utils.Dialogs;
import com.s2parking.utils.Formatter;
import com.s2parking.utils.Utils;
import com.s2parking.widget.HistoryAdapter;

/**
 * Controller responsável por mostrar os dados dos históricos (Períodos e Irregularidades) na listagem
 * @author desenv03
 *
 */
public class HistoryController implements ApiListener {
	
	private Activity activity;
	private Dialogs dialogs;
	private ListView listView;
	private ConfigurationManager configuration;
	
	public HistoryController(Activity activity, ListView listView, Dialogs dialogs) {
		this.activity = activity;
		this.listView = listView;
		this.dialogs = dialogs;
		this.configuration = ConfigurationManager.get();
	}
	
	public void getHistory(final ListView list) {
		ApiManager apiManager =  new ApiManager(URL.CONTROLLER.HISTORY_MOBILE, HttpMethod.GET, this);
		apiManager.addParam(Constants.PREFERENCES_CLIENT_ID, String.valueOf(ConfigurationManager.get().getInt(Constants.PREFERENCES_CLIENT_ID)));
		apiManager.execute();
	}

	@Override
	public void onStarted() {
		dialogs.openProgressDialog();
	}

	@Override
	public void onSucceed(JSONObject response) {
		try {
			// Zera listagem do id dos últimos tickets
			eraseListLastTicketsIds();
			
			//Monta a lista com o histórico do cliente.
			final List<HistoryItem> items = new ArrayList<HistoryItem>();
			JSONArray arrayTickets = new JSONArray(response.getString(Fields.TICKETS));
			
			for (int i = 0; i < arrayTickets.length(); i++) {
				JSONObject objRoot = arrayTickets.getJSONObject(i);
				JSONObject objTicket = objRoot.getJSONObject(Fields.JSON_TICKET);
				JSONObject objArea = objRoot.getJSONObject(Fields.JSON_AREA);
				
				final HistoryItem history = new HistoryItem();
				//Tipo Ticket
				history.setType(ParkTicket.Type.getDescriptionByName(objTicket.getString(Fields.TIPO)));
				//Tipo Irregularidade
				history.setReasonIrregularity(ParkTicket.IrregularityType.getDescriptionByName(objTicket.getString(Fields.MOTIVO_IRREGULARIDADE)));
				//Placa
				history.setPlate(objTicket.getString(Fields.PLACA));
				//Período Início
				String sDataInicio = objTicket.getString(Fields.DATA_INICIO);
				history.setInitialDate(Formatter.get().dateFromSQLDate(sDataInicio) + " " + Formatter.get().time(sDataInicio));
				//Período Fim
				String sDataFim = objTicket.getString(Fields.DATA_FIM);
				history.setFinalDate(Formatter.get().dateFromSQLDate(sDataFim) + " " + Formatter.get().time(sDataFim));
				//Situação
				history.setSituation(objTicket.getString(Fields.SITUACAO));
				
				//Status
				Date dateStart =  Formatter.get().parseDate(sDataInicio);
				Date dateEnd =  Formatter.get().parseDate(sDataFim);
				Date now = Formatter.get().parseDate(response.getString(Fields.NOW));
				//Se o now() está entre o data_inicio e o data_fim, então está ativo.
				//Se o now() está depois do data_fim, então está consumido.
				//Se não está a ativar.
				if (now.getTime() >= dateStart.getTime() && now.getTime() <= dateEnd.getTime()) {
					history.setStatus(ParkHistorico.Status.ATIVO.getDescription());	
				} else if (now.after(dateEnd)) {
					history.setStatus(ParkHistorico.Status.CONSUMIDO.getDescription());
				} else {
					history.setStatus(ParkHistorico.Status.A_ATIVAR.getDescription());
				}
				//Valor
				BigDecimal valor = new BigDecimal(objTicket.getString(Fields.VALOR));
				history.setValue(Formatter.get().currency(valor.longValue()));
				//Forma de pagamento
				history.setPaymentMethod(ParkTicket.PaymentType.getDescriptionByName(objTicket.getString(Fields.FORMA_PAGAMENTO)));
				//Área
				history.setArea(objArea.getString(Fields.NOME));
				//Interrompido
				history.setInterrupted(objTicket.getString(Fields.INTERROMPIDO));
				//Ticket Id
				history.setTicketId(objTicket.getInt(Fields.ID));
				
				String idString = "";
				idString = idString.valueOf(objTicket.getInt(Fields.ID));
				//Verifica se o ticket pode ser interrompido
				if (objTicket.getBoolean("can_cancel")) {			
					configuration.set(idString, true);
				}else {
					configuration.set(idString, false);
				}		
				
				items.add(history);
			}
			// Adiciona no adapter
			HistoryAdapter adapter = new HistoryAdapter(activity, R.layout.history_list_item, items);
			listView.setAdapter(adapter);
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
	}
	
	/**
	 * Método para zerar o array dos últimos id's de cada placa do histórico para validar interrupção de tickets
	 */
	private void eraseListLastTicketsIds(){
		try {
			JSONArray list = configuration.getJSONArray(Constants.PREFERENCES_LAST_TICKETS_ID);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}// End Method 'eraseListLastTicketsIds'
}