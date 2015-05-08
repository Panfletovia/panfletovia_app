	package com.panfletovia.activities;

import android.app.Activity;
import android.os.Bundle;

import com.panfletovia.base.ConfigurationManager;
import com.panfletovia.base.Dialogs;
import com.panfletovia.base.Toaster;

/**
 * Classe base das activities. NAO ACRESCENTE METODOS NESTA CLASSE! Todos os
 * metodos dessa classe podem ser chamados de outra forma. Ou seja, esta classe
 * eh apenas uma conveniencia. Os metodos desta classe sao normalmente
 * sobrescritos pelos testes das activities (Mocks).
 * 
 * @author Davi Gabriel da Silva
 * 
 */
public class BaseActivity extends Activity {

	protected ConfigurationManager configuration;
//	protected SessionManager sessionManager;
//	protected ParkingSession sessionReader;
//	protected APIMessageBuilder messageBuilder;
//	protected APIManager apiManager;
	protected Toaster toaster;
	protected Dialogs dialogs;
//	protected ReceiptManager receiptManager;
	
	public ConfigurationManager getConfigurationManager() {
		if (configuration == null) {
			configuration = ConfigurationManager.get();
		}
		return configuration;
		
	}

//	public APIManager getAPIManager() {
//		if (apiManager == null) {
//			apiManager = new APIManager();
//		}
//		return apiManager;
//		
//	}
//
//	public SessionManager getSessionManager() {
//		if (sessionManager == null) {
//			sessionManager = SessionManagerImpl.get();
//		}
//		return sessionManager;
//	}
//
//	public APIMessageBuilder getMessageBuilder() {
//		if (messageBuilder == null) {
//			messageBuilder = APIMessageBuilder.get(getConfigurationManager());
//		}
//		return messageBuilder;
//	}

	public Toaster getToaster() {
		if (toaster == null) {
			toaster = new Toaster(this);
		}
		return toaster;
	}

	public Dialogs getDialogs() {
		if (dialogs == null) {
			dialogs = new Dialogs(this);
		}
		return dialogs;
	}

//	public ReceiptManager getReceiptManager() {
//		if (receiptManager == null) {
//			receiptManager = new ReceiptManager(this, ReceiptManager.PRINTER_DATECS);
//		}
//		return receiptManager;
//	}
	
//	/**
//	 * Retorna a string a partir do id do R.string
//	 * @param id
//	 * @return
//	 */
//	public String getStringById(int id) {
//		return getResources().getString(id);
//	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.configuration = getConfigurationManager();
//		this.sessionManager = getSessionManager();
//		this.messageBuilder = getMessageBuilder();
//		this.apiManager = getAPIManager();
//		this.sessionReader = new ParkingSession(sessionManager);
		this.toaster = getToaster();
		this.dialogs = getDialogs();
//		this.receiptManager = getReceiptManager();
	}
	
}
