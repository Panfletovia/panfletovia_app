package com.panfletovia.service;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import android.app.Activity;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import com.panfletovia.controller.TicketsController;

/**
 * Classe responsável por efetuar o controle dos tickets ativos do usuário
 * quando o usuário do s2Parking permanece no menu principal do aplicativo
 * 
 * @author desenv03
 *
 */
public class TicketsService extends Service {

	private Timer timer = new Timer();
	// Activity owner
	private static Activity activity;
	// Controller que efetua o acesso a API
	private TicketsController ticketsController;
	// Lista de controle dos tickets ativos para notificação no status bar
	private static final List<Integer> listTicketsId = new CopyOnWriteArrayList<Integer>();

	/**
	 * Método set para activity owner
	 * 
	 * @param act
	 */
	public static void setActivity(Activity act) {
		activity = act;
	}

	/**
	 * Método para efetuar a chamada dos tickets ativos
	 */
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startService();
		return START_STICKY;
	}// End Method 'onStartCommand'

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}// End Method 'onBind'

	/**
	 * Lista 'synchronized' da lista de tickets ativos para controle interno das
	 * notificações que estão sendo exibidas
	 * 
	 * @return
	 */
	public static synchronized List<Integer> getListTicketsId() {
		return listTicketsId;
	}// End Method 'getListTicketsId'
	
	private void startService() {
		timer.scheduleAtFixedRate(new mainTask(), 0, 10000);
	}

	private class mainTask extends TimerTask {
		public void run() {
			toastHandler.sendEmptyMessage(0);
		}
	}

	public void onDestroy() {
		super.onDestroy();
		listTicketsId.clear();
		timer.cancel();
	}

	private final Handler toastHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			if (ticketsController == null) {
				ticketsController = new TicketsController(activity);				
			}
			ticketsController.getTickets();
		}
	};

}// End Class