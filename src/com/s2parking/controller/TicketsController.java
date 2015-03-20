package com.s2parking.controller;
	
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.widget.Toast;

import com.s2parking.activities.HistoryActivity;
import com.s2parking.activities.R;
import com.s2parking.api.ApiListener;
import com.s2parking.api.ApiManager;
import com.s2parking.api.URL;
import com.s2parking.enums.Constants;
import com.s2parking.enums.Fields;
import com.s2parking.service.TicketsService;
import com.s2parking.type.HttpMethod;
import com.s2parking.utils.ConfigurationManager;
import com.s2parking.utils.Formatter;
import com.s2parking.utils.Utils;

/**
 * Classe responsável por controlar os tickets ativos do usuário e mostrar as notificações 
 * na barra de status
 * @author desenv03
 *
 */
public class TicketsController {
	

	private Activity activity;
	private ApiManager manager;
	private ConfigurationManager configuration = ConfigurationManager.get();
	
	/**
	 * Default construtor
	 * @param activity
	 */
	public TicketsController(Activity activity) {
		// Seta activity ownet
		this.activity = activity;
	}
	
	/**
	 * Busca os tickets ativos
	 */
	public void getTickets() {
		// Cria instância do objeto que executa o request para API
		manager = new ApiManager(URL.CONTROLLER.TICKETS_MOBILE, Constants.ACTION_ACTIVE_TICKETS , HttpMethod.POST, new ApiListener() {
			
			@Override
			public void onSucceed(JSONObject response) {
				// Valida se a resposta da API é válida, se possui mais de um registro e 
				// se possui o campo dos tickets ativos  
				if (response != null && response.length() > 0 && response.has(Fields.TICKETS)) {
					// Cria array para guardar a lista de id's dos tickets ativos do usuário
					List<Integer> ticketsIdApiConsult = new ArrayList<Integer>();
					try{
						// Extrai da resposta da API, um objeto JSONArray dos tickets ativos
						JSONArray tickets = response.getJSONArray(Fields.TICKETS);
						// Extrai a quantidade de tickets ativos
						int ticketsSize = tickets.length();
						// Variáveis que recebem a mensagem e o títuloque deverá ser 
						// mostrada na notificação na barra de status
						String messageNotification, titleNotification;
						// Varre a lista de tickets ativos para criar a notificação na barra de status
						for (int x = 0; x < ticketsSize; x++) {
							// Extrai o objeto JSON do ticket
							JSONObject ticket = (JSONObject) tickets.getJSONObject(x).get(Fields.JSON_TICKET);
							// Extrai o id do ticket 
							int ticketId = ticket.getInt(Fields.ID);
							// Adiciona na lista de tickets da API o id do ticket
							ticketsIdApiConsult.add(ticketId);
							//Verifica se contém o id do ticket da lista ticketsIdApp.
							if(!TicketsService.getListTicketsId().contains(ticketId)){
								// Cria o titulo da notificação
								titleNotification = "S2Parking - " + ticket.getString(Fields.PLACA);
								// Cria mensagem que será exibido na notificação
								messageNotification = 
										Formatter.get().time(ticket.getString(Fields.DATA_INICIO))
										+ " às "
										+ Formatter.get().time(ticket.getString(Fields.DATA_FIM));
								// Exibe a notificação na barra de status
								Utils.sendNotification(
										activity.getApplicationContext(), 
										HistoryActivity.class, 
										titleNotification, 
										messageNotification, 
									R.drawable.logo_32,
									ticketId
									);
								// Adiciona na lista de tickets 
								TicketsService.getListTicketsId().add(ticketId);
							}
						}
					}catch(Exception e){
						Toast.makeText(activity, 
								activity.getString(R.string.error_get_active_tickets).replace("XXX", e.getMessage()),
								Toast.LENGTH_LONG).show();
					}
						
					// Verifica se existe algum ticket que está na lista do aplicativo com notificação
					// e que não foi retornado pela API. Exemplo: terminou o tempo de um ticket.
					for (Integer id : TicketsService.getListTicketsId()) {
						if (!ticketsIdApiConsult.contains(id)) {
							//Remove do ticketsIdApp.
							TicketsService.getListTicketsId().remove(id);
							// Remove do notification
							Utils.cancelNotification(id);
						}
					}
				}
			}
			
			@Override
			public void onStarted() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onFinished() {
				// TODO Auto-generated method stub
				
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
		manager.addParam(Constants.PREFERENCES_CLIENT_ID, configuration.getString(Constants.PREFERENCES_CLIENT_ID));
		manager.execute();		
		
	}// End Method 'getTickets'
	
}// End Class