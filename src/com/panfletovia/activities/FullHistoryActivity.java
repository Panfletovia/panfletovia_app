package com.panfletovia.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.LightingColorFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.panfletovia.annotation.Field;
import com.panfletovia.bean.HistoryItem;
import com.panfletovia.controller.FullHistoryController;
import com.panfletovia.enums.Constants;
import com.panfletovia.type.ParkHistorico;
import com.panfletovia.type.ParkTicket;
import com.panfletovia.utils.ConfigurationManager;
import com.panfletovia.utils.Dialogs;
import com.panfletovia.utils.Utils;
import com.panfletovia.widget.FullHistoryAdapter;
import com.s2parking.activities.R;

/**
 * Activity para controlar a lista de tickets do usuários (Períodos e irregularidades)
 * @author desenv03
 *
 */
public class FullHistoryActivity extends Activity {
	
	@Field(id = R.id.full_history_list)
	private ListView listView;
	@Field(id = R.id.full_history_btn_stop_period)
	private Button btnStopPeriod;
	private HistoryItem historyItem;
	private FullHistoryAdapter fullHistoryAdapter;
	private List<HistoryItem> listItem;
	private FullHistoryController fullHistoryController;
	private Dialogs dialogs;
	private ConfigurationManager configuration = ConfigurationManager.get();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_full_history);
		init();
		changeCity();
	}
	
	private void init() {
		try {
			// Validação dos componentes
			Utils.initializeFields(this);
			Utils.setTitle(this, R.string.menu_title_history);
			dialogs =  new Dialogs(this);
			fullHistoryController = new FullHistoryController(this, dialogs);
			// Busca a lista de historicos
			historyItem = (HistoryItem) getIntent().getParcelableExtra(ParkHistorico.PARCELABLE_HISTORY);
			listItem = new ArrayList<HistoryItem>();
			listItem.add(historyItem);
			fullHistoryAdapter = new FullHistoryAdapter(this, R.layout.full_history_list_item, listItem);
			listView.setAdapter(fullHistoryAdapter);
			// Se o status do veículo está 'ativo' ou 'a ativar' e ticket for de compra de período, 
			// então mostra a opção para interromper o período.
			String idString = "";
			idString = idString.valueOf(historyItem.getTicketId());
			if (
					(historyItem.getStatus().equals(ParkHistorico.Status.ATIVO.getDescription()) 
					|| historyItem.getStatus().equals(ParkHistorico.Status.A_ATIVAR.getDescription()))
					&& historyItem.getType().equals(ParkTicket.Type.UTILIZACAO.getDescription())
					&& (configuration.getBoolean(idString))
				) {
				btnStopPeriod.getBackground().setColorFilter(new LightingColorFilter(0x580000, 0xA00000));
				btnStopPeriod.setVisibility(View.VISIBLE);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}// End Method 'init'
	
	/**
	 * Chama o método responsável por parar a compra de período.
	 * @param view
	 */
	public void stopPeriod(View view) {
		dialogs.confirm(getString(R.string.question),
			getString(R.string.question_interrupt_ticket),
			new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					fullHistoryController.stopPeriod(historyItem.getTicketId());
				}
			});
		
	}// End Method 'stopPeriod'
	
	/*
	 * Método Responsavél por trocar o imagem para o logo da operação escolhida
	 * e também alterar o nome da cidade
	 */
	public void changeCity() {
		ImageView img = (ImageView) findViewById(R.id.menu_header_logo);
		int logo = Integer.valueOf(configuration.getInt(Constants.PREFERENCES_LOCAL_LOGO));
		img.setImageResource(logo);
		
		TextView city = (TextView) findViewById(R.id.title_city);
		String city_preferences = String.valueOf(configuration.getString(Constants.PREFERENCES_LOCAL_TITLE)); 
		city.setText(city_preferences);
	}// End Method 'changeCity'
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		startActivity(new Intent(this, HistoryActivity.class));
		finish();
	}
}// End Class
