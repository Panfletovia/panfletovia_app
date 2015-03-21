package com.panfletovia.activities;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.panfletovia.bean.MainItem;
import com.panfletovia.enums.AppMenu;
import com.panfletovia.enums.Constants;
import com.panfletovia.service.TicketsService;
import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Formatter;
import com.panfletovia.widget.MainAdapter;
import com.s2parking.activities.R;

/**
 * Activity que controla o menu principal da aplicação
 * @author desenv03
 *
 */
public class MainActivity extends Activity implements OnItemClickListener {

	
	private ListView listMenu;
	private ConfigurationManager configuration = ConfigurationManager.get();
	private Intent intentService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
		addListeners();
		changeCity();
	}
	
	/**
	 * Monta o menu principal do aplicativo 
	 */
	private void init() {
		// Cria listView dos menus 
		listMenu = (ListView) findViewById(R.id.main_list_menu);
		// Lista que recebe os objetos do menuItem
		List<MainItem> list = new ArrayList<MainItem>();
		// Adiciona os menus a lista de menus
		// list.add(addMainItem(R.drawable.recarga_42, R.string.menu_title_recharge, R.string.menu_subtitle_recharge));
		list.add(addMainItem(R.drawable.estacionar_42, R.string.menu_title_park, R.string.menu_subtitle_park));
		list.add(addMainItem(R.drawable.historico_42, R.string.menu_title_history, R.string.menu_subtitle_history));
		list.add(addMainItem(R.drawable.localizar_42, R.string.menu_title_find_spot, R.string.menu_subtitle_find_spot));
		list.add(addMainItem(R.drawable.adicionar_42, R.string.menu_title_plates, R.string.menu_subtitle_plates));
		list.add(addMainItem(R.drawable.logout_42, R.string.menu_title_logout, R.string.menu_subtitle_logout));
		// Adiciona o adapter 
		MainAdapter mainAdapter = new MainAdapter(this, R.id.main_list_menu,list);
		listMenu.setAdapter(mainAdapter);
		// Adiciona informação do saldo do usuário
		TextView textSaldo = (TextView) findViewById(R.id.main_text_saldo);
		BigDecimal saldoPre = new BigDecimal(configuration.getString(Constants.PREFERENCES_SALDO_PRE));
		textSaldo.setText(Formatter.get().currency(saldoPre.longValue()));
	}// End Method 'init'
	
	/**
	 * Adiciona listener para classe afim de validar a ação do click para entrada de menu
	 */
	private void addListeners() {
		listMenu.setOnItemClickListener(this);
	}// End Method 'addListeners'
	
	/**
	 * Método facilitador da criação do objeto MainItem
	 * @param icon
	 * @param title
	 * @param subTitle
	 * @return
	 */
	private MainItem addMainItem(int icon, int title, int subTitle){
		return new MainItem(icon, getString(title), getString(subTitle));
	}// End Method 'addMainItem'
	
	@Override
	protected void onResume() {
		super.onResume();
		// Inicia serviço da busca dos tickets ativos para notificação na barra de status
		startTicketService();
	}
	
	/**
	 * Método para iniciar a busca dos tickets ativos do usuário
	 */
	private void startTicketService() {
		if(intentService == null) {
			TicketsService.setActivity(this);
			intentService = new Intent(this, TicketsService.class);
			startService(intentService);
		}
	}// End Method 'startTicketService'
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,	long id) {
		if (id == AppMenu.MENU_RECHARGE.getId()) {
		} else if (id == AppMenu.MENU_PARK.getId()) {
			startActivity(new Intent(this, ParkActivity.class));
		} else if (id == AppMenu.MENU_HISTORY.getId()) {
			startActivity(new Intent(this, HistoryActivity.class));
		} else if (id == AppMenu.MENU_FIND_SPOT.getId()) {
			startActivity(new Intent(this, FindSpotActivity.class));
		} else if (id == AppMenu.MENU_PLATES.getId()) {
			startActivity(new Intent(this, PlatesActivity.class));
		} else if (id == AppMenu.MENU_LOGOUT.getId()) {
			Dialogs dialogs = new Dialogs(this);

			dialogs.confirm(getString(R.string.question),
					getString(R.string.question_logoff),
					new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// LOUGOUT
							stopService(intentService);
							configuration.set(Constants.PREFERENCES_CLIENT_ID, String.valueOf(0));
							startActivity(new Intent(MainActivity.this,LoginActivity.class));
							finish();
						}
					});
		}
	}// End Method 'onItemClick'
	
	
	/*
	 * Método Responsavél por trocar o imagem para o logo da operação escolhida
	 * e o nome da cidade
	 */
	public void changeCity() {
		ImageView img = (ImageView) findViewById(R.id.main_logo);
		int logo = Integer.valueOf(configuration.getInt(Constants.PREFERENCES_LOCAL_LOGO));
		img.setImageResource(logo);
		
		TextView city = (TextView) findViewById(R.id.main_city);
		String city_preferences = String.valueOf(configuration.getString(Constants.PREFERENCES_LOCAL_TITLE)); 
		city.setText(city_preferences);
		
	}// End Method 'changeCity'
	
} // End Class